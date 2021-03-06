package com.zrp.mallplus.sys.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zrp.mallplus.annotation.IgnoreAuth;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.component.OssAliyunUtil;
import com.zrp.mallplus.pms.entity.PmsAlbumPic;
import com.zrp.mallplus.pms.mapper.PmsAlbumPicMapper;
import com.zrp.mallplus.sys.service.impl.OssServiceImpl;
import com.zrp.mallplus.sys.vo.OssCallbackResult;
import com.zrp.mallplus.sys.vo.OssPolicyResult;
import com.zrp.mallplus.util.UserUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.vo.BlobUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Oss相关操作接口
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    OssAliyunUtil aliyunOSSUtil;
    @Autowired
    private OssServiceImpl ossService;
    @Resource
    private PmsAlbumPicMapper albumPicMapper;

    private static ByteArrayInputStream getRandomDataStream(int length) {
        return new ByteArrayInputStream(getRandomBuffer(length));
    }

    private static byte[] getRandomBuffer(int length) {
        final Random randGenerator = new Random();
        final byte[] buff = new byte[length];
        randGenerator.nextBytes(buff);
        return buff;
    }

    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public Object policy() {
        OssPolicyResult result = ossService.policy();
        return new CommonResult().success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public Object callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new CommonResult().success(ossCallbackResult);
    }

    @SysLog(MODULE = "图片上传管理", REMARK = "上传")
    @ApiOperation("上传")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Object uploadImage(Long groupId, String id, Long uid, String fileType, int type, @RequestPart("file") MultipartFile multipartFile) {
        List<BlobUpload> list = new ArrayList<>();

        String name = aliyunOSSUtil.upload(multipartFile);
        insertPic(groupId, multipartFile, name, "image");
        BlobUpload blobUploadEntity = new BlobUpload();
        blobUploadEntity.setFileName(multipartFile.getOriginalFilename());
        blobUploadEntity.setFileUrl(name);
        blobUploadEntity.setThumbnailUrl(name);
        list.add(blobUploadEntity);


        return new CommonResult().success(list);
    }

    @SysLog(MODULE = "图片上传管理", REMARK = "上传")
    @ApiOperation("上传")
    @RequestMapping(value = "uploads", method = RequestMethod.POST)
    public Object uploadImages(Long groupId, Long id, Integer type, @RequestPart("file") MultipartFile[] multipartFile) {
        List<BlobUpload> list = new ArrayList<>();
        if (multipartFile != null && multipartFile.length > 0) {
            for (int i = 0; i < multipartFile.length; i++) {
                String name = aliyunOSSUtil.upload(multipartFile[i]);
                insertPic(groupId, multipartFile[i], name, "image");
                BlobUpload blobUploadEntity = new BlobUpload();
                blobUploadEntity.setFileName(multipartFile[i].getOriginalFilename());
                blobUploadEntity.setFileUrl(name);
                blobUploadEntity.setThumbnailUrl(name);
                list.add(blobUploadEntity);
            }
        }
        return new CommonResult().success(list);
    }

    @SysLog(MODULE = "图片上传管理", REMARK = "上传文件")
    @ApiOperation("上传文件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Object uploadFile(Long groupId, String id, Long uid, String fileType, Integer type, @RequestPart("file") MultipartFile multipartFile) {
        List<BlobUpload> list = new ArrayList<>();
        String fileExtension = getFileExtension(multipartFile.getOriginalFilename()).toLowerCase();
        if (multipartFile != null) {
            if (!(fileType.equals("file") || fileType.equals("video"))) {
                return new CommonResult().failed("文件类型错误");
            }
            if (fileType.equals("file")) {
                if (!fileExtension.equals(".csv")) {
                    return new CommonResult().failed("文件格式错误");
                }
            }
            if (fileType.equals("video")) {
                if (!fileExtension.equals(".mp4")) {
                    return new CommonResult().failed("视频格式错误");
                }
            }
            String name = aliyunOSSUtil.upload(multipartFile);

            return new CommonResult().success(insertPic(groupId, multipartFile, name, fileType));

        }
        return new CommonResult().success(list);
    }

    private PmsAlbumPic insertPic(Long groupId, @RequestPart("file") MultipartFile multipartFile, String name, String type) {
        PmsAlbumPic attachment = new PmsAlbumPic();
        if (ValidatorUtils.notEmpty(UserUtils.getCurrentMember())) {
            attachment.setUserId(UserUtils.getCurrentMember().getId());
        }
        attachment.setAlbumId(groupId);
        attachment.setCreateTime(new Date());
        attachment.setName(multipartFile.getOriginalFilename());
        //  attachment.setStoreId(Long.parseLong("1"));
        attachment.setType(type);
        attachment.setPic(name);
        albumPicMapper.insert(attachment);
        return attachment;
    }

    @IgnoreAuth
    @PostMapping("/upload1")
    @ApiOperation("上传文件")
    public Object upload(@RequestParam("file") MultipartFile file) throws Exception {
        return new CommonResult().success(aliyunOSSUtil.upload(file));
    }

    @IgnoreAuth
    @PostMapping("/uploads1")
    @ApiOperation("多文件上传文件")
    public Object uploads(@RequestPart("file") MultipartFile[] file) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                stringBuffer.append(aliyunOSSUtil.upload(file[i]) + ",");
            }
        }
        return new CommonResult().success(stringBuffer);
    }

    private String getFileExtension(String fileName) {
        int position = fileName.indexOf('.');
        if (position > 0) {
            String temp = fileName.substring(position);
            return temp;
        }
        return "";
    }

    /**
     * @Description 获取config.json配置文件
     */
    @RequestMapping("/getConfig")
    @ResponseBody
    public void getConfig(HttpServletResponse response) {
        InputStream is = null;
        response.setHeader("Content-Type", "text/html");
        try {
            is = new FileInputStream("C:\\Users\\EDZ\\Desktop\\mallplus\\mallplus-admin\\src\\main\\resources\\config.json");
            StringBuffer sb = new StringBuffer();
            byte[] b = new byte[1024];
            int length;
            while (-1 != (length = is.read(b))) {
                sb.append(new String(b, 0, length, "utf-8"));
            }
            String result = sb.toString().replaceAll("/\\*(.|[\\r\\n])*?\\*/", "");
            JSONObject json = JSON.parseObject(result);
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }


}
