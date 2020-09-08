package com.zscat.mallplus.vo;

import lombok.Data;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Data
public class PhoneDto {

    private String openId;

    private String keyStr;

    private String ivStr;

    private String encDataStr;
}
