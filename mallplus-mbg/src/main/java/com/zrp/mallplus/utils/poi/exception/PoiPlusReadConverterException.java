package com.zrp.mallplus.utils.poi.exception;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/21 13:58
 */
public class PoiPlusReadConverterException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusReadConverterException(String message) {
        super(message);
    }

    public PoiPlusReadConverterException(Throwable cause) {
        super(cause);
    }
}
