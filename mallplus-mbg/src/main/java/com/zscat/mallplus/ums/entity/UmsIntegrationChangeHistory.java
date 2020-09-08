package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

 /**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark: 积分变化历史记录表
 */
@TableName("ums_integration_change_history")
public class UmsIntegrationChangeHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 改变类型：1->增加；2->减少 AllEnum.class
     */
    @TableField("change_type")
    private Integer changeType;

    /**
     * 积分改变数量
     */
    @TableField("change_count")
    private Integer changeCount;

    /**
     * 操作人员
     */
    @TableField("operate_man")
    private String operateMan;

    /**
     * 操作备注
     */
    @TableField("operate_note")
    private String operateNote;

    /**
     * 积分来源：1->下单；2->登录； 3->注册 ；4-> 后台修改；5-> 签到；6-> 评论；7-> 分享
     */
    @TableField("source_type")
    private Integer sourceType;


     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public Long getMemberId() {
         return memberId;
     }

     public void setMemberId(Long memberId) {
         this.memberId = memberId;
     }

     public Date getCreateTime() {
         return createTime;
     }

     public void setCreateTime(Date createTime) {
         this.createTime = createTime;
     }

     public Integer getChangeType() {
         return changeType;
     }

     public void setChangeType(Integer changeType) {
         this.changeType = changeType;
     }

     public Integer getChangeCount() {
         return changeCount;
     }

     public void setChangeCount(Integer changeCount) {
         this.changeCount = changeCount;
     }

     public String getOperateMan() {
         return operateMan;
     }

     public void setOperateMan(String operateMan) {
         this.operateMan = operateMan;
     }

     public String getOperateNote() {
         return operateNote;
     }

     public void setOperateNote(String operateNote) {
         this.operateNote = operateNote;
     }

     public Integer getSourceType() {
         return sourceType;
     }

     public void setSourceType(Integer sourceType) {
         this.sourceType = sourceType;
     }

     public UmsIntegrationChangeHistory() {
    }

    public UmsIntegrationChangeHistory(Long memberId, Date createTime, Integer changeType, Integer changeCount, String operateMan, String operateNote, Integer sourceType) {
        this.memberId = memberId;
        this.createTime = createTime;
        this.changeType = changeType;
        this.changeCount = changeCount;
        this.operateMan = operateMan;
        this.operateNote = operateNote;
        this.sourceType = sourceType;
    }
}
