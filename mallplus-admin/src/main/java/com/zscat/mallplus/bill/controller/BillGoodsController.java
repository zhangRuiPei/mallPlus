package com.zscat.mallplus.bill.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zscat.mallplus.bill.entity.BakGoods;
import com.zscat.mallplus.bill.service.IBakGoodsService;

import com.zscat.mallplus.utils.poi.constants.PoiPlusFileExtend;
import com.zscat.mallplus.utils.poi.designer.SimpleXlsDesigner;
import com.zscat.mallplus.utils.poi.designer.csv.CsvDesigner;
import com.zscat.mallplus.utils.poi.handler.ExcelReadHandler;
import com.zscat.mallplus.utils.poi.loader.SimpleXlsxLoader;
import com.zscat.mallplus.utils.poi.pojo.ExcelData;
import com.zscat.mallplus.utils.poi.pojo.ExcelErrorField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BillGoodsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillGoodsController.class);

    private static final String XLSX_EXCEL_TEMP_PATH = "template/templates/BakGoods.xls";


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

    /**
     * 下载excel（无模板）
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try (SimpleXlsDesigner simpleXlsDesigner = new SimpleXlsDesigner()) {
            // 填充数据源
            ExcelData<BakGoods> excelData = new ExcelData<>(BakGoods.class, service.selectNotPar());
            simpleXlsDesigner.setData(excelData);

            // 数据加工
            simpleXlsDesigner.process();

            // 保存Excel
            simpleXlsDesigner.download(request, response, PoiPlusFileExtend.XLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/template")
    public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
        try (SimpleXlsDesigner simpleXlsDesigner = new SimpleXlsDesigner()) {
            // 填充数据源
            ExcelData<BakGoods> excelData = new ExcelData<>(BakGoods.class);
            simpleXlsDesigner.setData(excelData);

            // 数据加工
            simpleXlsDesigner.process(true);

            // 保存Excel
            simpleXlsDesigner.download(request, response, PoiPlusFileExtend.XLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载CSV
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/csv")
    public void exportCsv(HttpServletRequest request, HttpServletResponse response) {
        ExcelData<BakGoods> excelData = new ExcelData<>(BakGoods.class, service.selectNotPar());
        CsvDesigner.build(excelData).download(request, response);
    }

    /**
     * Excel导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        // 执行文件导入.
        long beginTimeMillis = System.currentTimeMillis();
        final List<BakGoods> bakGoodsList = Lists.newArrayList();
        final List<Map<String, Object>> error = Lists.newArrayList();



        SimpleXlsxLoader.build(BakGoods.class).readXlsx(file.getInputStream(), -1,0,new ExcelReadHandler<BakGoods>() {
            @Override
            public void onSuccess(int sheet, int row, BakGoods bakGoods) {
                // 当前行读取成功, 入库或加入批量入库队列.
                bakGoodsList.add(bakGoods);
            }

            @Override
            public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                // 当前行读取失败, 获取失败详情.
                error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
            }
        });
        long time = ((System.currentTimeMillis() - beginTimeMillis) / 1000L);

        LOGGER.info("数据量: {}, 耗时: {}秒", bakGoodsList.size(), time);

        ImmutableMap<String, Object> retJsonMap = ImmutableMap.of(//
                "time", "耗时" + time + "秒",
                "data", bakGoodsList,
                "error", error
        );

        return ResponseEntity.ok(retJsonMap);

    }


}
