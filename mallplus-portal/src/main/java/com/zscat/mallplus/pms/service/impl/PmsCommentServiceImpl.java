package com.zscat.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.pms.entity.PmsComment;
import com.zscat.mallplus.pms.mapper.*;
import com.zscat.mallplus.pms.service.IPmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class PmsCommentServiceImpl extends ServiceImpl<PmsCommentMapper, PmsComment> implements IPmsCommentService {

    @Autowired
    private PmsCommentMapper mapper;

    @Override
    public List<PmsComment> getByProductId(Long productId) {
        return mapper.getByProductId(productId);
    }

    @Override
    public PmsComment getCommentInfo(Long productId, Long orderNo){
        return mapper.getCommentInfo(productId,orderNo);
    }
}
