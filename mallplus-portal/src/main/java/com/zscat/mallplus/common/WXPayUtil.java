package com.zscat.mallplus.common;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.*;


public class WXPayUtil {



    private static final Logger logger = LoggerFactory.getLogger(WXPayUtil.class);


    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = null;

        String proxy = request.getHeader("Client-IP");
        if (proxy == null || proxy.length() == 0 || "unknown".equalsIgnoreCase(proxy))
            proxy = request.getHeader("x-forwarded-for");

        if (proxy == null || proxy.length() == 0 || "unknown".equalsIgnoreCase(proxy))
            proxy = request.getHeader("Proxy-Client-IP");

        if (proxy == null || proxy.length() == 0 || "unknown".equalsIgnoreCase(proxy))
            proxy = request.getHeader("WL-Proxy-Client-IP");

        if (proxy != null && proxy.length() > 0 && !"unknown".equalsIgnoreCase(proxy))
            ipAddress = proxy.split(",")[0];

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }

        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        logger.info("============================================");
        logger.info("========client IP:" + ipAddress + "=============");
        logger.info("============================================");
        return ipAddress;
    }

    



    /**
     * 输入流转字符串
     * @param in
     * @return
     */
    public static String InputStream2String(InputStream in) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        InputStream stream = null;
        stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(stream);
        Element root = document.getRootElement();
        if (!root.getName().equals("xml")) {
            throw new Exception("XML root \""+ root.getName() +"\" is invalid");
        }
        List<Element> list = root.getChildren();
        HashMap<String, String> data = new HashMap<String, String>();
        for (Element node : list) {
            data.put(node.getName(), node.getTextTrim());
        }
        return data;
    }


    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return String
     */
    public static String mapToXml(Map<String, String> data) {
        Element root=new Element("xml");
        Document doc=new Document();
        for(String k:data.keySet()) {
            Element child=new Element(k);
            child.addContent(data.get(k));
            root.addContent(child);
        }
        doc.setRootElement(root);
        XMLOutputter xmOut = new XMLOutputter(Format.getPrettyFormat());
        return xmOut.outputString(doc);
    }

    // public static String mapToXml(Map<String, Object> data) {
    //     Element root=new Element("xml");
    //     Document doc=new Document();
    //     for(String k:data.keySet()) {
    //         Element child=new Element(k);
    //         child.addContent(data.get(k).toString());
    //         root.addContent(child);
    //     }
    //     doc.setRootElement(root);
    //     XMLOutputter xmOut = new XMLOutputter(Format.getPrettyFormat());
    //     return xmOut.outputString(doc);
    // }

    /**
     * 生成带有sign的XML格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) {
        String sign = generateSignature(data, key);
        data.put(WXPayConstants.SIGN, sign);
        return mapToXml(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WXPayConstants.SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        if (!data.containsKey(WXPayConstants.SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.SIGN)) {
                continue;
            }
            if (data.get(k).length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k)).append("&");
        }
        sb.append("key=").append(key);
        return MD5(sb.toString()).toUpperCase();
    }


    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 生成 MD5
     *
     * @param s 待处理数据
     * @return MD5结果
     */
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
