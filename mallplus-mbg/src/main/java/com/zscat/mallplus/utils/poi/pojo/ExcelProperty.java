package com.zscat.mallplus.utils.poi.pojo;


import com.zscat.mallplus.utils.poi.config.Options;
import com.zscat.mallplus.utils.poi.convert.ReadConverter;
import com.zscat.mallplus.utils.poi.convert.WriteConverter;
import com.zscat.mallplus.utils.poi.validator.Validator;
import lombok.*;


/**
 * @author yangdaxin
 * @version 创建时间 2019/1/19 17:12
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelProperty {
    /**
     * 自定义字段名
     */
    private String name;

    /**
     * Excel字段标题
     */
    private String title;

    /**
     * 是否为必填字段
     */
    private Boolean required;

    /**
     * 列宽
     */
    private Short width;

    /**
     * 最大长度, 读取时生效
     */
    private int maxLength;

    /**
     * 时间格式化表达式
     */
    private String dateFormat;

    /**
     * 自定义配置项
     */
    private Options options;

    /**
     * 写表达式
     */
    private String writeConverterExp;

    /**
     * 写转换器
     */
    private WriteConverter writeConverter;

    /**
     * 读表达式
     */
    private String readConverterExp;

    /**
     * 读转换器
     */
    private ReadConverter readConverter;

    /**
     * 正则表达式
     */
    private String regularExp;

    /**
     * 正则表达式提示信息
     */
    private String regularExpMessage;

    /**
     * 校验器
     */
    private Validator validator;
}
