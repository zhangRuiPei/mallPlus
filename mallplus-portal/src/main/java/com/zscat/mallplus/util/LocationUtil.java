package com.zscat.mallplus.util;

import com.zscat.mallplus.util.vo.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:    地理信息相关
 */
public class LocationUtil {



    /**
     * 根据经纬度返回对应的地理信息
     * @param lat
     * @param lng
     * @return
     * @throws Exception
     */
    public static LocalResoultVo getAddress(String lat, String lng) throws Exception {
        final String appid="19cd2cab2deb4d7fb21a3870a3d6d982";
        final String host = "https://jisujwddz.market.alicloudapi.com";
        final String path = "/geoconvert/coord2addr";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appid);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("lat", lat);
        querys.put("lng", lng);
        querys.put("type", "baidu");
        HttpResponse post = HttpUtils.doGet(host, path,"GET",headers, querys);
        String beng = EntityUtils.toString(post.getEntity());
        JSONObject jsonObject= JSONObject.fromObject(beng);
        LocalResoultVo localResoultVo = (LocalResoultVo)JSONObject.toBean(jsonObject, LocalResoultVo.class);
        return localResoultVo;
    }

    /**
     * 根据地址返回经纬度
     * @param address
     * @return
     */
    public static LocalResoultVo addr2coord(String address){
        try {
            String host = "https://jisujwddz.market.alicloudapi.com";
            String path = "/geoconvert/addr2coord";
            String method = "ANY";
            String appcode = "19cd2cab2deb4d7fb21a3870a3d6d982";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            //根据API的要求，定义相对应的Content-Type
            headers.put("Content-Type", "application/json; charset=UTF-8");
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("address", address);
            querys.put("type", "baidu");
            HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
            String toString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject= JSONObject.fromObject(toString);
            LocalResoultVo localResoultVo = (LocalResoultVo)JSONObject.toBean(jsonObject, LocalResoultVo.class);
            return localResoultVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取快递信息根据订单号
     * @param expressNumber
     * @param type
     * @return
     * @throws Exception
     */
    public static ExpressVo queryexpress(String expressNumber, String type) throws Exception {
        final String host = "https://qyexpress.market.alicloudapi.com";
        final String path = "/composite/queryexpress";
        final String method = "GET";
        final String appcode = "19cd2cab2deb4d7fb21a3870a3d6d982";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        type = type.equals(null)? "0" : "auto";
        querys.put("number", expressNumber);
        querys.put("type", type);
        HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
        String toString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject= JSONObject.fromObject(toString);
        String list = jsonObject.getString("data");
        JSONObject jsonObject1= JSONObject.fromObject(list);
        List<ExpressListVo> listVos = jsonObject1.getJSONArray("list");
        ExpressVo expressVo = new ExpressVo();
        expressVo.setList(listVos);
        expressVo.setNumber(jsonObject1.getString("number"));
        expressVo.setDeliverystatus(jsonObject1.getString("deliverystatus"));
        expressVo.setIssign(jsonObject1.getString("issign"));
        expressVo.setLogo(jsonObject1.getString("logo"));
        expressVo.setType(jsonObject1.getString("type"));
        expressVo.setTypename(jsonObject1.getString("typename"));
        return expressVo;
    }


    /**
     * 查询主流的快递
     * @return
     */
    public static ExpressInfoList findExpressInfo(){
        try {
            String host = "https://qyexpress.market.alicloudapi.com";
            String path = "/composite/queryexpresscompany";
            String method = "GET";
            String appcode = "19cd2cab2deb4d7fb21a3870a3d6d982";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
            String toString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject= JSONObject.fromObject(toString);
            List<ExpressInfo> expressInfos = jsonObject.getJSONArray("data");
            ExpressInfoList expressInfoList = new ExpressInfoList();
            expressInfoList.setList(expressInfos);
            return expressInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }








}
