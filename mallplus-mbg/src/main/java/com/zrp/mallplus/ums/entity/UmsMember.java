package com.zrp.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrp.mallplus.utils.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:  会员表
 */
@TableName("ums_member")
public class UmsMember extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员等级ID
     */
    @TableField("member_level_id")
    private Long memberLevelId;
    /**
     * 会员等级名称
     */
    @TableField("member_level_name")
    private String memberLevelName;
    /**
     * 地区ID
     */
    @TableField("area_id")
    private Long areaId;
    /**
     * 地区名
     */
    @TableField("area_name")
    private String areaName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    private Integer status;

    /**
     * 注册时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd: HH:mm:ss",timezone = "GTM+8")
    private Date createTime;

    /**
     * 头像
     */
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     */
    private Integer gender;

    /**
     * 生日
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date birthday;

    /**
     * 所做城市
     */
    private String city;

    /**
     * 职业
     */
    private String job;

    /**
     * 个性签名
     */
    @TableField("personalized_signature")
    private String personalizedSignature;

    /**
     * 用户来源 1 小程序 2 公众号 3 页面
     */
    @TableField("source_type")
    private Integer sourceType;

    /**
     * 积分
     */
    private Integer integration;

    /**
     * 直播房间号
     */
    @TableField("room_nums")
    private String roomNums;

    /**
     * 直播房间排序
     */
    @TableField("room_desc")
    private String roomDesc;
    /**
     * 成长值
     */
    private Integer growth;


    /**
     *  1 账号密码 2 验证码登录
     */
    @TableField(exist = false)
    private Integer loginType;


    /**
     * 返回给前端的生日
     */
    @TableField(exist = false)
    private String birthDays;

    /**
     * 剩余抽奖次数
     */
    @TableField("luckey_count")
    private Integer luckeyCount;

    /**
     * 历史积分数量
     */
    @TableField("history_integration")
    private Integer historyIntegration;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 微信OPENID
     */
    @TableField("weixin_openid")
    private String weixinOpenid;

    /**
     * 邀请码
     */
    private String invitecode;

    @TableField("buy_count")
    private Integer buyCount;
    @TableField("buy_money")
    private BigDecimal buyMoney;

    /**
     * 余额
     */
    private BigDecimal blance;

    /**
     *团队销售额
     */
    @TableField("sales_team")
    private BigDecimal salesTeam;

    /**
     * 上级id
     */
    @TableField("higher_level_id")
    private Integer higherLevelId;


    /**
     * 是否是主播
     */
    @TableField("is_anchor")
    private Integer isAnchor;

    /**
     * 点赞量
     */
    @TableField("like_num")
    private Long likeNum;

    /**
     * 转发量
     */
    @TableField("forward_num")
    private Long forwardNum;

    /**
     * 评论数量
     */
    @TableField("comment_num")
    private Long commentNum;

    /**
     * 主播人气值
     */
    @TableField("anchor_popularity_value")
    private Long anchorPopularityValue;

    /**
     * 粉丝数量
     */
    @TableField("anchor_fans_num")
    private Long anchorFansNum;



    //微信登录所需参数
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String encryptedData;
    @TableField(exist = false)
    private String iv;
    @TableField(exist = false)
    private String signature;
    @TableField(exist = false)
    private UserInfo userInfo;
    @TableField(exist = false)
    private String cloudID;



    @TableField(exist = false)
    private String confimpassword;

    @TableField(exist = false)
    private String phonecode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(Long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public String getRoomNums() {
        return roomNums;
    }

    public void setRoomNums(String roomNums) {
        this.roomNums = roomNums;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getBirthDays() {
        return birthDays;
    }

    public void setBirthDays(String birthDays) {
        this.birthDays = birthDays;
    }

    public Integer getLuckeyCount() {
        return luckeyCount;
    }

    public void setLuckeyCount(Integer luckeyCount) {
        this.luckeyCount = luckeyCount;
    }

    public Integer getHistoryIntegration() {
        return historyIntegration;
    }

    public void setHistoryIntegration(Integer historyIntegration) {
        this.historyIntegration = historyIntegration;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal buyMoney) {
        this.buyMoney = buyMoney;
    }

    public BigDecimal getBlance() {
        return blance;
    }

    public void setBlance(BigDecimal blance) {
        this.blance = blance;
    }

    public BigDecimal getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(BigDecimal salesTeam) {
        this.salesTeam = salesTeam;
    }

    public Integer getHigherLevelId() {
        return higherLevelId;
    }

    public void setHigherLevelId(Integer higherLevelId) {
        this.higherLevelId = higherLevelId;
    }

    public Integer getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Integer isAnchor) {
        this.isAnchor = isAnchor;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public Long getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(Long forwardNum) {
        this.forwardNum = forwardNum;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public Long getAnchorPopularityValue() {
        return anchorPopularityValue;
    }

    public void setAnchorPopularityValue(Long anchorPopularityValue) {
        this.anchorPopularityValue = anchorPopularityValue;
    }

    public Long getAnchorFansNum() {
        return anchorFansNum;
    }

    public void setAnchorFansNum(Long anchorFansNum) {
        this.anchorFansNum = anchorFansNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getCloudID() {
        return cloudID;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public String getConfimpassword() {
        return confimpassword;
    }

    public void setConfimpassword(String confimpassword) {
        this.confimpassword = confimpassword;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }
}
