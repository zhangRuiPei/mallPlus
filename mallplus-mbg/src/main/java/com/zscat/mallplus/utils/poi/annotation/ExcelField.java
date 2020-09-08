package com.zscat.mallplus.utils.poi.annotation;




import com.zscat.mallplus.utils.poi.config.Options;
import com.zscat.mallplus.utils.poi.convert.ReadConverter;
import com.zscat.mallplus.utils.poi.convert.WriteConverter;
import com.zscat.mallplus.utils.poi.validator.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {
    /**
     * 属性名, 仅在复杂数据类型时配置.
     * <pre>
     *   @ExcelField(name="UserGroup.name");
     *   private UserGroupEntity UserGroup;
     * </pre>
     *
     * @return 属性名
     */
    String name() default "";

    /**
     * excel字段标题
     *
     * @return
     */
    String title() default "";

    /**
     * @return 是否必填
     */
    boolean required() default false;

    /**
     * @return 单元格宽度[仅限表头] 默认-1(自动计算列宽)
     */
    short width() default -1;

    /**
     * @return 最大长度, 读取时生效, 默认不限制
     */
    int maxLength() default -1;

    /**
     * 日期格式, 如: yyyy/MM/dd
     *
     * @return 日期格式
     */
    String dateFormat() default "";

    /**
     * @return 下拉框数据源, 生成模板和验证数据时生效
     */
    Class<? extends Options> options() default Void.class;

    /**
     * 写入内容转换表达式 (如: 1=男,2=女), 与 writeConverter 二选一(优先级0)
     *
     * @return 写入内容转换表达式
     * @see ExcelField#writeConverter()
     */
    String writeConverterExp() default "";

    /**
     * 写入内容转换器, 与 writeConverterExp 二选一(优先级1)
     *
     * @return 写入内容转换器
     * @see ExcelField#writeConverterExp()
     */
    Class<? extends WriteConverter> writeConverter() default Void.class;

    /**
     * 读取内容转表达式 (如: 男=1,女=2), 与 readConverter 二选一(优先级0)
     *
     * @return 读取内容转表达式
     * @see ExcelField#readConverter()
     */
    String readConverterExp() default "";

    /**
     * 读取内容转换器, 与 readConverterExp 二选一(优先级1)
     *
     * @return 读取内容转换器
     * @see ExcelField#readConverterExp()
     */
    Class<? extends ReadConverter> readConverter() default Void.class;

    /**
     * 正则表达式, 读取时生效, 与 validator 二选一(优先级0)
     *
     * @return 正则表达式
     * @see ExcelField#validator()
     */
    String regularExp() default "";

    /**
     * 正则表达式验证失败时的错误消息, regularExp 配置后生效
     *
     * @return 正则表达式验证失败时的错误消息
     * @see ExcelField#regularExp()
     */
    String regularExpMessage() default "";

    /**
     * 自定义验证器, 读取时生效, 与 regularExp 二选一(优先级1)
     *
     * @return 自定义验证器
     * @see ExcelField#regularExp()
     */
    Class<? extends Validator> validator() default Void.class;

    class Void implements ReadConverter, WriteConverter, Validator, Options {

        @Override
        public String convert(Object value) {
            return null;
        }

        @Override
        public String valid(Object value) {
            return null;
        }

        @Override
        public String[] get() {
            return new String[0];
        }
    }
}
