package com.zscat.mallplus.utils.poi.pojo;

import lombok.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCell implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行号
     */
    private int rowIdx;

    /**
     * 列号
     */
    private int colIdx;

    /**
     * 单元格样式
     */
    private transient CellStyle cellStyle;
}
