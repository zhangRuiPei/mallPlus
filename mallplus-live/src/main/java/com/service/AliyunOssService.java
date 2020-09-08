package com.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Service
public class AliyunOssService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AliyunOssService.class);
    @Value("${oss.accesKeyId}")
    private String accesKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;


    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.bucketName}")
    private String bucketName;
    /**
     * 生成访问图片地址有效期
     */
    private static final int VISIT_URL_EXPIRATION = 365 * 10;
//    private static final int VISIT_URL_EXPIRATION = 1;

    private OSS ossClient;

    public void initOssClient() {
        ossClient = new OSSClientBuilder().build(endpoint, accesKeyId, accessKeySecret);
    }

    /**
     * uploadFile:上传文件到Oss
     *
     * @param imageName       图片名字
     * @param fileInputStream 图片流
     * @param fileSize        fileSize 图片大小
     * @throws Exception
     */
    public String uploadFile(String imageName, InputStream fileInputStream, Long fileSize) throws Exception {
        initOssClient();
        try {
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(fileSize);
//            if (!savePath.endsWith("/")) {
//                savePath = savePath + "/";
//            }
            ossClient.putObject(bucketName, imageName, fileInputStream, objectMeta);
        } catch (Exception e) {
            LOGGER.error("上传文件到oss出错", e);
            throw new Exception("上传文件到oss出错");
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                    ossClient.shutdown();
                } catch (IOException e) {
                    LOGGER.error("上传文件到oss出错", e);
                }
            }
        }
        return  imageName;
    }

    /**
     * 生成原图访问地址
     * @param imageName
     * @return
     * @throws Exception
     */
    @Cacheable(value = "fileUrl",key = "#imageName")
    public String getVisitUrl(String imageName) throws Exception {
        initOssClient();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, VISIT_URL_EXPIRATION);
            return ossClient.generatePresignedUrl(bucketName,  imageName, calendar.getTime()).toString();
        } catch (Exception e) {
            throw new Exception("生成访问地址出错");
        } finally {
            ossClient.shutdown();
        }
    }

}
