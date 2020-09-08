package com.zscat.mallplus.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Getter
@Setter
public class CarList {
    private Long memberId;
    private List<Long> ids;
    private  Long id;
    private Integer quantity;
}
