package com.zrp.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sms.entity.SmsFlashPromotionSession;
import com.zrp.mallplus.sms.vo.SmsFlashSessionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 限时购场次表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsFlashPromotionSessionMapper extends BaseMapper<SmsFlashPromotionSession> {
    List<SmsFlashSessionInfo> getCurrentDang(@Param("current_time") String current_time);

}
