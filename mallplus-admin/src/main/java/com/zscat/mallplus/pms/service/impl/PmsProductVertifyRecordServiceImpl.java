package com.zscat.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.pms.entity.PmsProductVertifyRecord;
import com.zscat.mallplus.pms.mapper.PmsProductVertifyRecordMapper;
import com.zscat.mallplus.pms.service.IPmsProductVertifyRecordService;
import com.zscat.mallplus.sys.entity.SysStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 商品审核记录 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class PmsProductVertifyRecordServiceImpl extends ServiceImpl<PmsProductVertifyRecordMapper, PmsProductVertifyRecord> implements IPmsProductVertifyRecordService {
    @Resource
    private PmsProductVertifyRecordMapper productVertifyRecordMapper;
    @Override
    public int addRecord(SysStore entity) {
        PmsProductVertifyRecord record = new PmsProductVertifyRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        record.setCreateTime(df.format(new Date()));
        record.setStatus(entity.getStatus());
        record.setVertifyMan(entity.getVertifyMan());
        record.setDetail(entity.getDetail());
        record.setStoreId(entity.getId());
        return  productVertifyRecordMapper.insert(record);
    }
}
