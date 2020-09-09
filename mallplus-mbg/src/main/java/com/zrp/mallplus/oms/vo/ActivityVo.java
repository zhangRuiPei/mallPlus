package com.zrp.mallplus.oms.vo;

import lombok.Data;

/**
 * 生成订单时传入的参数
 */
@Data
public class ActivityVo {

    private String info;
    private String link;
    private String pic;
    private String title;
    private String wap_link;

    public ActivityVo() {
    }

    public ActivityVo(String info, String link, String pic, String title, String wap_link) {
        this.info = info;
        this.link = link;
        this.pic = pic;
        this.title = title;
        this.wap_link = wap_link;
    }
}
