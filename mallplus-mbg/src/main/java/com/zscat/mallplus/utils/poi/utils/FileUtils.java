package com.zscat.mallplus.utils.poi.utils;


import com.zscat.mallplus.utils.poi.constants.MessageConstants;
import com.zscat.mallplus.utils.poi.constants.PoiPlusFileExtend;
import com.zscat.mallplus.utils.poi.exception.PoiPlusExcelDownloadException;
import com.zscat.mallplus.utils.poi.exception.PoiPlusExcelSaveException;
import com.zscat.mallplus.utils.poi.pojo.ExcelMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件帮助类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 15:39
 */
public class FileUtils {
    private FileUtils() {
        // Do Nothing
    }

    /**
     * 保存文件
     *
     * @param filePath 目标文件路径
     * @throws IOException
     */
    public static void saveFile(ByteArrayOutputStream outStream, String filePath) {
        // 获取流字节
        byte[] content = outStream.toByteArray();

        try (
                InputStream is = new ByteArrayInputStream(content);
                FileOutputStream outputStream = new FileOutputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream)
        ) {
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new PoiPlusExcelSaveException(e);
        }
    }


    public static void downloadFile(ByteArrayOutputStream outStream, ExcelMapping excelMapping, HttpServletRequest request, HttpServletResponse response, PoiPlusFileExtend poiPlusFileExtend, String fileTitle) {
        if (poiPlusFileExtend.equals(PoiPlusFileExtend.CSV)) {
            throw new PoiPlusExcelDownloadException("下载.cvs格式文件失败，请使用正确的cvs下载方法！");
        }

        // 获取流字节
        byte[] content = outStream.toByteArray();

        // 设置response参数，可以打开下载页面
        // response 为 HttpServletResponse对象
        response.reset();

        try (
                InputStream is = new ByteArrayInputStream(content);
                OutputStream outputStream = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream)
        ) {
            // 后去文件名
            String fileName = FileUtils.getDownloadFileName(request, excelMapping, fileTitle, poiPlusFileExtend);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentLength(content.length);

            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new PoiPlusExcelSaveException(e);
        }
    }

    /**
     * 获取CSV下载文件名
     *
     * @param request        客户端请求
     * @param excelMapping   excel实体mapping
     * @param fileTitle      文件标题
     * @param poiPlusFileExtend 文件扩展名
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getDownloadFileName(HttpServletRequest request, ExcelMapping excelMapping, String fileTitle, PoiPlusFileExtend poiPlusFileExtend) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(fileTitle)) {
            fileTitle = excelMapping.getName();
        }

        String fileName = fileTitle + poiPlusFileExtend.getValue();
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        if (userAgent.contains(MessageConstants.BROWSER_CHROME)) {
            //encode编码UTF-8 解决大多数中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //encode后替换空格  解决空格问题
            fileName = fileName.replace("+", "%20");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        return fileName;
    }
}
