package com.zscat.mallplus.utils.poi.constants;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public enum PoiPlusFileExtend {
    /**
     * xls
     */
    XLS(".xls"),

    /**
     * xlsx
     */
    XLSX(".xlsx"),

    /**
     * csv
     */
    CSV(".csv");

    private String value;

    PoiPlusFileExtend(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
