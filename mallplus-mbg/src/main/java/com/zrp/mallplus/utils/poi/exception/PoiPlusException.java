package com.zrp.mallplus.utils.poi.exception;

/**
 * 自定义异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 16:00
 */
public class PoiPlusException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusException(String message) {
        super(message);
    }

    public PoiPlusException(Throwable cause) {
        super(cause);
    }

    public PoiPlusException(String message, Throwable cause) {
        super(message, cause);
    }
}
