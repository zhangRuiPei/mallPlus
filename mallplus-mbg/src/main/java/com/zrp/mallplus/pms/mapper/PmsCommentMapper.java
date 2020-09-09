package com.zrp.mallplus.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.pms.entity.PmsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品评价表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface PmsCommentMapper extends BaseMapper<PmsComment> {

    List<PmsComment> getByProductId(@Param("productId") Long productId);

    PmsComment getCommentInfo(@Param("productId") Long productId,
                              @Param("orderNo") Long orderNo);
}
