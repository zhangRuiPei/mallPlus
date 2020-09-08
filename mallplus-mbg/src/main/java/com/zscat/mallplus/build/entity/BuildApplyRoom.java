package com.zscat.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Setter
@Getter
@TableName("build_apply_room")
public class BuildApplyRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请人
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 申请房间
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
