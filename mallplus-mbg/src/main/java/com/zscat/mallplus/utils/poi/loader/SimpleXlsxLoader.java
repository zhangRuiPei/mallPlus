package com.zscat.mallplus.utils.poi.loader;


import com.zscat.mallplus.utils.ExcelXlsxReader;
import com.zscat.mallplus.utils.poi.exception.PoiPlusException;
import com.zscat.mallplus.utils.poi.factory.ExcelMappingFactory;
import com.zscat.mallplus.utils.poi.handler.ExcelReadHandler;
import com.zscat.mallplus.utils.poi.pojo.ExcelMapping;
import com.zscat.mallplus.utils.poi.utils.PoiUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/22 16:02
 */
public class SimpleXlsxLoader implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleXlsxLoader.class);

    private Class<?> entityClazz;

    private static final ThreadLocal<ByteArrayOutputStream> INPUT_STREAM_CACHE = new ThreadLocal<>();

    private SimpleXlsxLoader(Class<?> clazz) {
        this.entityClazz = clazz;
    }

    public static SimpleXlsxLoader build(Class<?> clazz) {
        return new SimpleXlsxLoader(clazz);
    }

    public void readXlsx(InputStream inputStream, ExcelReadHandler<?> excelReadHandler) {
        this.readXlsx(inputStream, -1, -1, excelReadHandler);
    }

    public void readXlsx(InputStream inputStream, int beginReadRowIndex, ExcelReadHandler<?> excelReadHandler) {
        this.readXlsx(inputStream, -1, beginReadRowIndex, excelReadHandler);
    }

    public void readXlsx(InputStream inputStream, int sheetIndex, Integer beginReadRowIndex, ExcelReadHandler<?> excelReadHandler) {
        ExcelMapping excelMapping = ExcelMappingFactory.get(this.entityClazz);

        // 把文件流复制到本地缓存中，便于复用
        try {
            inputStreamCacher(inputStream);
        } catch (IOException e) {
            throw new PoiPlusException(e);
        }

        if (beginReadRowIndex == -1) {
            // 用户未知道读取数据起始行时，启动识别导入文件起始行号
            try (InputStream is = new ByteArrayInputStream(INPUT_STREAM_CACHE.get().toByteArray())) {
                beginReadRowIndex = PoiUtils.getBeginReadRowIndex(is, excelMapping);
            } catch (PoiPlusException e1) {
                throw new PoiPlusException(e1);
            } catch (IOException | InvalidFormatException e2) {
                beginReadRowIndex = null;
            }
        }

        // 执行导入
        try (InputStream is = new ByteArrayInputStream(INPUT_STREAM_CACHE.get().toByteArray())) {
            ExcelXlsxReader excelXlsReader = new ExcelXlsxReader(this.entityClazz, excelMapping, beginReadRowIndex, excelReadHandler);
            if (sheetIndex >= 0) {
                excelXlsReader.process(is, sheetIndex);
            } else {
                excelXlsReader.process(is);
            }
        } catch (IOException e) {
            LOGGER.error("InputStream create error!", e);
        } finally {
            try {
                this.close();
            } catch (IOException e) {
                LOGGER.error("InputStream close error!", e);
            }
        }
    }

    /**
     * 把文件流复制到本地缓存中，便于复用
     *
     * @param inputStream
     * @throws IOException
     */
    private void inputStreamCacher(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                out.write(buffer, 0, len);
            }
            INPUT_STREAM_CACHE.set(out);
        }
    }

    @Override
    public void close() throws IOException {
        if (null != INPUT_STREAM_CACHE.get()) {
            INPUT_STREAM_CACHE.remove();
        }
    }
}
