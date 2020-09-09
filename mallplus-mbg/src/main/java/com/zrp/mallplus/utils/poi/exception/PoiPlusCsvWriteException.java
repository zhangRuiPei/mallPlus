package com.zrp.mallplus.utils.poi.exception;

/**
 * Scv文件写异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 15:26
 */
public class PoiPlusCsvWriteException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusCsvWriteException(String message) {
        super(message);
    }

    public PoiPlusCsvWriteException(Throwable cause) {
        super(cause);
    }
}
