package com.zscat.mallplus.utils.poi.pojo;

import lombok.*;

import java.util.List;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/19 17:12
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelMapping {
    /**
     * Excel实体名称
     */
    private String name;

    /**
     * Excel实体属性
     */
    private List<ExcelProperty> propertyList;
}