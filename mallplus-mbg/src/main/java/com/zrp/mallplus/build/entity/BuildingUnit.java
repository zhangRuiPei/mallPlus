package com.zrp.mallplus.build.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
@TableName("building_unit")
public class BuildingUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单元ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 单元编号
     */
    @Excel(name = " 单元编号", orderNum = "1", width = 30)
    @TableField("unit_num")
    private String unitNum;

    /**
     * 楼ID
     */
    @Excel(name = " 楼编号", orderNum = "2", width = 30)
    @TableField("floor_id")
    private String floorId;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "3", width = 30)
    private String remark;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", orderNum = "4", width = 30)
    @TableField("create_time")
    private Date createTime;


}
