package com.zscat.mallplus.live.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Data
@TableName("aliyun_live")
public class AliyunLive {


    @TableId(type = IdType.AUTO)
    private Integer id;

    //图标
    private String icon;

    //AppName
    private String appName;

    //StreamName
    private String steamName;

    //播流
    private String addLive;

    //rtmpUrl
    private String rtmpUrl;

    //flvUrl
    private String flvUrl;

    //m3u8Url
    private String m3u8Url;

    //创建时间
    private String createTime;


    //主播ID
    private Integer userId;

    //房间号
    private Integer roomNumber;

    //房间名
    private String roonName;

    //房间标题
    private String roomTitle;

    //直播商品
    private List<Long> longs;


    @TableField("roon_status")
    private Integer  roonStatus;

    @TableField("is_show")
    private Integer  isShow;

    private String lastStartTime;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String uicon;

    @TableField("sort")
    private Integer sort;
}
