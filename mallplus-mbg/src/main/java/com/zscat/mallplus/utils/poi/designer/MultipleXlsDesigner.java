package com.zscat.mallplus.utils.poi.designer;



import com.sun.media.sound.InvalidFormatException;
import com.zscat.mallplus.utils.poi.factory.ExcelMappingFactory;
import com.zscat.mallplus.utils.poi.pojo.ExcelData;
import com.zscat.mallplus.utils.poi.pojo.ExcelMapping;
import com.zscat.mallplus.utils.poi.utils.PoiUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;

/**
 * 多Xls模板导出
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 11:59
 */
public class MultipleXlsDesigner extends BaseXlsDesigner {
    public MultipleXlsDesigner() {
        super();
    }

    public MultipleXlsDesigner(InputStream inputStream) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        super(inputStream);
    }

    @Override
    public void process() throws IOException {
        this.process(false);
    }

    @Override
    public void process(boolean isTemp) throws IOException {
        // 获取工作簿页数
        int sheetCount = PoiUtils.getSheetCount(super.workBook);
        if (!super.hasTemp) {
            sheetCount = this.dataList.size();
        }

        for (int i = 0; i < sheetCount; i++) {
            ExcelData<?> excelData = this.dataList.get(i);
            ExcelMapping excelMapping = ExcelMappingFactory.get(excelData.getEntityClass());

            // 获取第一张工作簿
            Sheet sheet;
            if (super.hasTemp) {
                sheet = PoiUtils.getSheet(super.workBook, i);
            } else {
                sheet = PoiUtils.newSheet(super.workBook, excelMapping.getName());
            }

            // 填充数据
            super.writeExcelData(sheet, excelData, excelMapping, isTemp);
        }

        super.write();
    }
}
