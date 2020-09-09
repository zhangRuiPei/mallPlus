package com.zrp.mallplus.utils.poi.exception;

/**
 * Excel文件读取异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 16:00
 */
public class PoiPlusExcelReadException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusExcelReadException(String message) {
        super(message);
    }

    public PoiPlusExcelReadException(Throwable cause) {
        super(cause);
    }
}
