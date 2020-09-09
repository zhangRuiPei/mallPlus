package com.zrp.mallplus.utils.poi.designer.csv;

import com.google.common.collect.Lists;

import com.zrp.mallplus.utils.poi.constants.PoiPlusFileExtend;
import com.zrp.mallplus.utils.poi.exception.PoiPlusCsvWriteException;
import com.zrp.mallplus.utils.poi.factory.ExcelMappingFactory;
import com.zrp.mallplus.utils.poi.pojo.ExcelData;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.pojo.ExcelProperty;
import com.zrp.mallplus.utils.poi.utils.ExcelPropertyUtils;
import com.zrp.mallplus.utils.poi.utils.FileUtils;
import com.zrp.mallplus.utils.poi.utils.StringUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class CsvDesigner {
    /**
     * 数据源
     */
    private ExcelData excelData;


    private CsvDesigner(ExcelData excelData) {
        this.excelData = excelData;
    }

    public static CsvDesigner build(ExcelData excelData) {
        return new CsvDesigner(excelData);
    }

    /**
     * 导出CSV
     *
     * @param filePath 导出文件路径
     */
    public void export(String filePath) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath)
        ) {
            // 生成CSV
            this.generateCsv(fos);
        } catch (IOException e2) {
            throw new PoiPlusCsvWriteException(e2);
        }
    }

    /**
     * 下载CSV
     *
     * @param request  客户端请求
     * @param response 服务端响应
     */
    public void download(HttpServletRequest request, HttpServletResponse response) {
        this.download(request, response, null);
    }

    /**
     * 下载CSV
     *
     * @param request   客户端请求
     * @param response  服务端响应
     * @param fileTitle 文件标题
     */
    public void download(HttpServletRequest request, HttpServletResponse response, String fileTitle) {
        // 设置response参数，可以打开下载页面
        response.reset();

        ExcelMapping excelMapping = ExcelMappingFactory.get(this.excelData.getEntityClass());

        try (
                OutputStream os = response.getOutputStream()
        ) {
            String fileName = FileUtils.getDownloadFileName(request,excelMapping, fileTitle, PoiPlusFileExtend.CSV);
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            // 生成CSV
            this.generateCsv(os);
        } catch (IOException e2) {
            throw new PoiPlusCsvWriteException(e2);
        }
    }

    /**
     * 创建CSVFormat
     *
     * @param propertyList Excel实体属性
     * @return
     */
    private CSVFormat createCSVFormat(List<ExcelProperty> propertyList) {
        List<String> csvHeaderList = Lists.newArrayList();
        for (ExcelProperty property : propertyList) {
            String title = property.getTitle();
            if (StringUtils.isBlank(title)) {
                title = property.getName();
            }
            csvHeaderList.add(title);
        }
        return CSVFormat.DEFAULT.withHeader(StringUtil.toStringArray(csvHeaderList));
    }

    /**
     * 生成CSV
     *
     * @param os 输出流
     * @throws IOException
     */
    private void generateCsv(OutputStream os) throws IOException {
        ExcelMapping excelMapping = ExcelMappingFactory.get(this.excelData.getEntityClass());
        List<ExcelProperty> propertyList = excelMapping.getPropertyList();
        CSVFormat csvFormat = this.createCSVFormat(propertyList);
        try (
                OutputStreamWriter osw = new OutputStreamWriter(os, "GBK");
                CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat)
        ) {
            List<?> dataList = this.excelData.getDataList();
            for (Object entity : dataList) {
                List<String> csvDataList = Lists.newArrayList();
                for (ExcelProperty property : propertyList) {
                    String cellValue = ExcelPropertyUtils.getCellValueByExcelProperty(entity, property);
                    csvDataList.add(cellValue);
                }
                csvPrinter.printRecord(StringUtil.toStringArray(csvDataList));
            }

            csvPrinter.flush();
        }
    }
}
