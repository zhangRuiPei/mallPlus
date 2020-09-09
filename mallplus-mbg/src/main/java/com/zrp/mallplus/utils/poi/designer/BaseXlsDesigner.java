package com.zrp.mallplus.utils.poi.designer;


import com.sun.media.sound.InvalidFormatException;
import com.zrp.mallplus.utils.ExcelXlsxWriter;
import com.zrp.mallplus.utils.poi.constants.MessageConstants;
import com.zrp.mallplus.utils.poi.constants.PoiPlusFileExtend;
import com.zrp.mallplus.utils.poi.exception.PoiPlusException;
import com.zrp.mallplus.utils.poi.factory.ExcelMappingFactory;
import com.zrp.mallplus.utils.poi.pojo.ExcelData;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.utils.FileUtils;
import com.zrp.mallplus.utils.poi.utils.PoiUtils;
import com.zrp.mallplus.utils.poi.validator.Assert;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Xls设计器
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 11:58
 */
public abstract class BaseXlsDesigner implements Closeable {
    /**
     * 工作表
     */
    protected Workbook workBook;

    /**
     * 文档输出流
     */
    protected ByteArrayOutputStream outStream;

    /**
     * 数据源
     */
    protected List<ExcelData> dataList;

    /**
     * 是否有导出模板，默认有
     */
    protected boolean hasTemp = true;

    protected BaseXlsDesigner() {
        this.hasTemp = false;
        outStream = new ByteArrayOutputStream();
        this.workBook = PoiUtils.newHSSFWorkbook();
    }

    protected BaseXlsDesigner(InputStream inputStream) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        outStream = new ByteArrayOutputStream();
        this.workBook = PoiUtils.newWorkbook(inputStream);
    }

    /**
     * 添加单个数据源
     *
     * @param excelData 数据源
     */
    public void setData(ExcelData excelData) {
        LinkedList<ExcelData> linkedList = new LinkedList<>();
        linkedList.add(excelData);
        this.dataList = linkedList;
    }

    /**
     * 添加多个数据源
     *
     * @param excelDataList 数据源列表
     */
    public void setData(List<ExcelData> excelDataList) {
        this.dataList = excelDataList;
    }

    /**
     * 数据处理
     *
     * @throws IOException
     */
    protected abstract void process() throws IOException;

    /**
     * 数据处理
     *
     * @param isTemp 是否是模板
     * @throws IOException
     */
    protected abstract void process(boolean isTemp) throws IOException;

    /**
     * 关闭资源
     */
    @Override
    public void close() {
        if (this.workBook != null) {
            try {
                this.workBook.close();
            } catch (IOException e) {
                throw new PoiPlusException(MessageConstants.MSG_WORKBOOK_CLOSE);
            }
        }
    }

    /**
     * 写入文件
     *
     * @throws IOException
     */
    protected void write() throws IOException {
        Assert.isNull(this.workBook, MessageConstants.MSG_WORKBOOK_IS_NULL);
        this.workBook.write(this.outStream);
    }

    /**
     * 写sheet数据
     *
     * @param sheet 工作簿
     */
    protected void writeExcelData(Sheet sheet, ExcelData<?> excelData, ExcelMapping excelMapping, boolean isTemp) {
        // 填充数据
        ExcelXlsxWriter excelXlsxWriter = new ExcelXlsxWriter(this.workBook, sheet, excelMapping, this.hasTemp, isTemp);
        excelXlsxWriter.writeData(excelData);
    }

    /**
     * 导出文件
     *
     * @param filePath 文件路径
     */
    public void saveFile(String filePath) {
        Assert.isBlank(filePath, MessageConstants.MSG_SAVE_FILE_PATH_ERROR);
        FileUtils.saveFile(outStream, filePath);
    }

    /**
     * 下载Excel文件
     *
     * @param request   客户端请求
     * @param response  服务端响应
     * @param fileTitle 文件标题
     */
    public void download(HttpServletRequest request, HttpServletResponse response, PoiPlusFileExtend poiPlusFileExtend, String fileTitle) {
        ExcelMapping excelMapping = ExcelMappingFactory.get(this.dataList.get(0).getEntityClass());
        FileUtils.downloadFile(outStream, excelMapping, request, response, poiPlusFileExtend, fileTitle);
    }

    /**
     * 下载Excel文件
     *
     * @param request  客户端请求
     * @param response 服务端响应
     */
    public void download(HttpServletRequest request, HttpServletResponse response, PoiPlusFileExtend poiPlusFileExtend) {
        ExcelMapping excelMapping = ExcelMappingFactory.get(this.dataList.get(0).getEntityClass());
        FileUtils.downloadFile(outStream, excelMapping, request, response, poiPlusFileExtend, null);
    }
}
