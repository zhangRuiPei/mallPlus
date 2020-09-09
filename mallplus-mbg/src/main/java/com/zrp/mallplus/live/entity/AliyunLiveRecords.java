package com.zrp.mallplus.live.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliyunLiveRecords {

    /**
     * 主键id
     * */
    private Long id;

    /**
     * 直播间id
     * */
    private Long aliyunLiveId;

    /**
     * 主播id
     * */
    private Long userId;

    /**
     * 房间号
     * */
    private Long roomNumber;

    /**
     * 房间名
     * */
    private String roonName;

    /**
     * 房间标题
     * */
    private String roomTitle;

    /**
     *
     * */
    private String rtmpUrl;

    /**
     *
     * */
    private String flvUrl;

    /**
     *
     * */
    private String m3u8Url;
    /**
     *
     * */
    private String replayUrl;

    /**
     *
     * */
    private String addLive;

    /**
     * 创建时间
     * */
    private String createTime;

    /**
     * 开始时间
     * */
    private String startTime;

    /**
     * 结束时间
     * */
    private String endTime;

    /**
     * 直播间红包记录id
     * */
    private Long redEnvelopeId;

    /**
     * 直播间结束新增粉丝
     * */
    private Long anchorNewFans;

    /**
     * 上下架状态
     * */
    private Long isShow;

    /**
     * 本次直播人气值
     * */
    private Long anchorPopularityValue;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String icon;
}
