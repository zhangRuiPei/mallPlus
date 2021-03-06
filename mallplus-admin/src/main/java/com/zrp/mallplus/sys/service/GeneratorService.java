/**
 *
 */
package com.zrp.mallplus.sys.service;

import com.zrp.mallplus.bo.ColumnInfo;
import com.zrp.mallplus.sys.entity.GenConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public interface GeneratorService {
    /**
     * 查询数据库元数据
     *
     * @param name     表名
     * @param startEnd 分页参数
     * @return /
     */
    Object getTables(String name, int[] startEnd);

    /**
     * 得到数据表的元数据
     *
     * @param name 表名
     * @return /
     */
    Object getColumns(String name);

    /**
     * 生成代码
     *
     * @param columnInfos 表字段数据
     * @param genConfig   代码生成配置
     * @param tableName   表名
     */
    void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName);
}
