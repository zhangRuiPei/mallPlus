package com.zrp.mallplus.utils.poi.exception;

/**
 * Excel文件保存异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 16:00
 */
public class PoiPlusExcelWriteException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusExcelWriteException(String message) {
        super(message);
    }

    public PoiPlusExcelWriteException(Throwable cause) {
        super(cause);
    }
}
