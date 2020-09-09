package com.zrp.mallplus.live.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */

@Data
@TableName("anchor_apply")
public class AnchorApply {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //姓名
    private String username;

    //身份证号码
    private String userIdCare;

    //正面
    private String userIdCareFront;

    //反面
    private String userIdCareBack;

    //视频url
    private String videoUrl;

    //用户ID
    private Integer umsMemberId;

    /**
     *  0 申请 1 通过 2 驳回
     */
    private Integer status;

    //创建时间
    private Date createTime;

    private String updateTime;

}
