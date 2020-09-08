package com.zscat.mallplus.utils.poi.constants;

import java.io.Serializable;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public final class MessageConstants implements Serializable {
    private static final long serialVersionUID = 1L;

    private MessageConstants() {
        // Do Nothing
    }

    public static final String MSG_WORKBOOK_CLOSE = "Workbook资源关闭异常！";

    public static final String MSG_WORKBOOK_IS_NULL = "Workbook资源不能为Null！";

    public static final String MSG_SAVE_FILE_PATH_ERROR = "导出文件路径错误！";

    public static final String MSG_DATA_NOT_EMPTY = "导出数据不能为空！";

    public static final String MSG_CUSTOM_DATA_NOT_EMPTY = "自定义导出数据不能为空！";

    public static final String BROWSER_CHROME = "chrome";

    public static final String ONLY_SUPPORTED_XLSX = "Only .xlsx formatted files are supported.";
}
