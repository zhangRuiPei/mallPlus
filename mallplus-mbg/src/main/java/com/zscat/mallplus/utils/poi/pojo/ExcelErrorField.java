package com.zscat.mallplus.utils.poi.pojo;

import lombok.*;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/23 13:51
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorField {
    private Integer cellIndex;

    private String name;

    private String title;

    private String errorMessage;
}
