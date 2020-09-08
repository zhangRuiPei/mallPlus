package com.zscat.mallplus.utils.poi.constants;

import java.io.Serializable;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public final class ExcelConstants implements Serializable {
    private static final long serialVersionUID = 1L;

    private ExcelConstants() {
        // Do Nothing
    }

    /**
     * 数据源字段前缀
     */
    public static final String FIELD_PREFIX = "&=";

    /**
     * 数据源自定义字段前缀
     */
    public static final String CUSTOM_FIELD_PREFIX = "&=$";

    public static final String XLSX_DEFAULT_EMPTY_CELL_VALUE = "$EMPTY_CELL$";
    public static final Integer XLSX_DEFAULT_BEGIN_READ_ROW_INDEX = 1;
    public static final String SAX_PARSER_CLASS = "org.apache.xerces.parsers.SAXParser";
    public static final String SAX_C_ELEMENT = "c";
    public static final String SAX_SST_ELEMENT = "sst";
    public static final String SAX_STYLESHEET_ELEMENT = "styleSheet";
    public static final String SAX_R_ATTR = "r";
    public static final String SAX_T_ELEMENT = "t";
    public static final String SAX_S_ATTR_VALUE = "s";
    public static final String SAX_RID_PREFIX = "rId";
    public static final String SAX_ROW_ELEMENT = "row";

    public static final String SAX_CELLTYPE_B = "b";
    public static final String SAX_CELLTYPE_E = "e";
    public static final String SAX_CELLTYPE_INLINESTR = "inlineStr";
    public static final String SAX_CELLTYPE_S = "s";
    public static final String SAX_CELLTYPE_STR = "str";

    public static final int OPTIONS_MAX_SIZE = 100;
}
