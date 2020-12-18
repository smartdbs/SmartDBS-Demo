/*
 * File Name: EasyExcelI18nUtils
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.utils;

import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.zkteco.dbs.common.tool.handle.I18nCellWriteHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * EasyExcelI18nUtils
 *
 * @author sheldon.wu
 * @date 2020/11/24 10:33
 * @since 1.0.0
 */
public class EasyExcelUtils {

    public static void exportExcel(HttpServletResponse response, Class<?> head, List<?> list, String lang) {
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String tmp = DateUtils.format(new Date(), "yyyyMMddHHmmss");
            response.setHeader("Content-disposition", "attachment;filename=" + tmp + ".xlsx");
            write(response.getOutputStream(), head, lang).sheet("Sheet1").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ExcelWriterBuilder write(OutputStream outputStream, Class head, String lang) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (head == null) {
            throw new IllegalArgumentException("head must not be null");
        }
        excelWriterBuilder.head(head);
        excelWriterBuilder.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new I18nCellWriteHandler(lang));
        return excelWriterBuilder;
    }
}
