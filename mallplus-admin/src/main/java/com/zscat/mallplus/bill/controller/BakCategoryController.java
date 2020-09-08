package com.zscat.mallplus.bill.controller;

import com.google.common.collect.Maps;
import com.zscat.mallplus.bill.entity.BakGoods;
import com.zscat.mallplus.bill.service.IBakGoodsService;
import com.zscat.mallplus.utils.poi.constants.PoiPlusFileExtend;
import com.zscat.mallplus.utils.poi.designer.SimpleXlsDesigner;
import com.zscat.mallplus.utils.poi.pojo.ExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@RequestMapping("BakCategory")
public class  BakCategoryController{

    private static final String XLSX_EXCEL_TEMP_PATH = "template/templates/BakCategory.xlsx";


    @Autowired
    private IBakGoodsService service;

    /**
     * 下载excel（有模板）
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/excelTemp")
    public void exportExcelTemp(HttpServletRequest request, HttpServletResponse response) {
        // 打开模板
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(XLS_EXCEL_TEMP_PATH);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(XLSX_EXCEL_TEMP_PATH);
        try (SimpleXlsDesigner simpleXlsDesigner = new SimpleXlsDesigner(inputStream)) {
            // 填充数据源
            Map<String, String> map = Maps.newConcurrentMap();

            ExcelData<BakGoods> excelData = new ExcelData<>(BakGoods.class, map, service.selectNotPar());
            simpleXlsDesigner.setData(excelData);

            // 数据加工
            simpleXlsDesigner.process();

            // 保存Excel
            simpleXlsDesigner.download(request, response, PoiPlusFileExtend.XLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
