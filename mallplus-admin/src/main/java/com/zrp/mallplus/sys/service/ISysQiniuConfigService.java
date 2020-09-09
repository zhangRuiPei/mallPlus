package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysQiniuConfig;
import com.zrp.mallplus.sys.entity.SysQiniuContent;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysQiniuConfigService extends IService<SysQiniuConfig> {

    int update(SysQiniuConfig qiniuConfig);

    /**
     * 上传文件
     *
     * @param file        文件
     * @param qiniuConfig 配置
     * @return QiniuContent
     */
    SysQiniuContent upload(MultipartFile file, SysQiniuConfig qiniuConfig);

    /**
     * 查询文件
     *
     * @param id 文件ID
     * @return QiniuContent
     */
    SysQiniuContent findByContentId(Long id);

    /**
     * 下载文件
     *
     * @param content 文件信息
     * @param config  配置
     * @return String
     */
    String download(SysQiniuContent content, SysQiniuConfig config);

    /**
     * 删除文件
     *
     * @param content 文件
     * @param config  配置
     */
    void delete(SysQiniuContent content, SysQiniuConfig config);

    /**
     * 同步数据
     *
     * @param config 配置
     */
    void synchronize(SysQiniuConfig config);

    /**
     * 删除文件
     *
     * @param ids    文件ID数组
     * @param config 配置
     */
    void deleteAll(Long[] ids, SysQiniuConfig config);


    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     */
    void downloadList(List<SysQiniuContent> queryAll, HttpServletResponse response) throws IOException;
}
