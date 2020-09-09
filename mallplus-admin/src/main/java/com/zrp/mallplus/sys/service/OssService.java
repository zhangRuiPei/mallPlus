package com.zrp.mallplus.sys.service;


import com.zrp.mallplus.sys.vo.OssCallbackResult;
import com.zrp.mallplus.sys.vo.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface OssService {
    OssPolicyResult policy();

    OssCallbackResult callback(HttpServletRequest request);
}
