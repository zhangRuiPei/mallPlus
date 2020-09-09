package com.zrp.mallplus.build;


import com.zrp.mallplus.build.entity.BuildAdv;
import com.zrp.mallplus.build.entity.BuildNotice;
import com.zrp.mallplus.oms.vo.ActivityVo;
import com.zrp.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zrp.mallplus.sys.entity.SysStore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 * https://github.com/shenzhuan/mallplus on 2019/1/28.
 */
@Getter
@Setter
public class BuildHomeResult {
    List<PmsSmallNaviconCategory> navList;
    List<ActivityVo> activityList;
    //轮播广告
    List<BuildAdv> advertiseList;
    //推荐品牌
    private List<SysStore> storeList;
    //推荐专题
    private List<BuildNotice> subjectList;

}
