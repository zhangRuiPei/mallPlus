package com.zrp.mallplus.utils.poi.designer;



import com.sun.media.sound.InvalidFormatException;
import com.zrp.mallplus.utils.poi.factory.ExcelMappingFactory;
import com.zrp.mallplus.utils.poi.pojo.ExcelData;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.utils.PoiUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;

/**
 * 单Xls模板导出
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/17 11:58
 */
public class SimpleXlsDesigner extends BaseXlsDesigner {
    public SimpleXlsDesigner() {
        super();
    }

    public SimpleXlsDesigner(InputStream inputStream) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        super(inputStream);
    }

    @Override
    public void process() throws IOException {
       this.process(false);
    }

    @Override
    public void process(boolean isTemp) throws IOException {
        ExcelData<?> excelData = this.dataList.get(0);
        ExcelMapping excelMapping = ExcelMappingFactory.get(excelData.getEntityClass());

        // 获取第一张工作簿
        Sheet sheet;
        if (super.hasTemp) {
            sheet = PoiUtils.getSheet(super.workBook, 0);
        } else {
            sheet = PoiUtils.newSheet(super.workBook, excelMapping.getName());
        }

        // 填充数据
        super.writeExcelData(sheet, excelData, excelMapping, isTemp);

        // 写工作簿
        super.write();
    }
}
