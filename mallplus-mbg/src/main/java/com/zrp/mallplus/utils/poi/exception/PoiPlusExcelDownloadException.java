package com.zrp.mallplus.utils.poi.exception;

/**
 * Excel下载异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 16:00
 */
public class PoiPlusExcelDownloadException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusExcelDownloadException(String message) {
        super(message);
    }

    public PoiPlusExcelDownloadException(Throwable cause) {
        super(cause);
    }
}
