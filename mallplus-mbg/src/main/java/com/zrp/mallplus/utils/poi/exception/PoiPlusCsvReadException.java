package com.zrp.mallplus.utils.poi.exception;

/**
 * Scv文件读取异常
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 15:26
 */
public class PoiPlusCsvReadException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PoiPlusCsvReadException(String message) {
        super(message);
    }

    public PoiPlusCsvReadException(Throwable cause) {
        super(cause);
    }
}
