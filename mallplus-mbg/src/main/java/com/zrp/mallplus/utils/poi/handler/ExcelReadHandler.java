package com.zrp.mallplus.utils.poi.handler;





import com.zrp.mallplus.utils.poi.pojo.ExcelErrorField;

import java.util.List;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ExcelReadHandler<T> {
    /**
     * 读取成功
     *
     * @param sheetIndex 工作簿编号
     * @param rowIndex   行号
     * @param entity     实体
     */
    void onSuccess(int sheetIndex, int rowIndex, T entity);

    /**
     * 读取失败
     *
     * @param sheetIndex  工作簿编号
     * @param rowIndex    行号
     * @param errorFields 异常字段
     */
    void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields);


//
//    /**
//     * 读取成功
//     *
//     * @param sheetIndex 工作簿编号
//     * @param rowIndex   行号
//     * @param entity     实体
//     */
//    void onSuccess(int sheetIndex, int rowIndex, T entity);
//
//    /**
//     * 读取失败
//     *
//     * @param sheetIndex  工作簿编号
//     * @param rowIndex    行号
//     * @param errorFields 异常字段
//     */
//    void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields);
}
