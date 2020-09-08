package com.zscat.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.pms.entity.PmsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品评价表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsCommentService extends IService<PmsComment> {

    List<PmsComment> getByProductId(Long productId);

    PmsComment getCommentInfo(Long productId, Long orderNo);
}
