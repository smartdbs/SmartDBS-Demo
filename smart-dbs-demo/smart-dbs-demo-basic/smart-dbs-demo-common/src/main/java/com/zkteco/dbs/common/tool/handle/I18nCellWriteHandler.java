/*
 * File Name: I18nCellWriteHandler
 * Project Name: smart-dbs-demo
 * Copyright:Copyright © 1985-2018 ZKTeco Inc.All right reserved.
 */
package com.zkteco.dbs.common.tool.handle;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zkteco.dbs.common.tool.resolver.PlaceholderResolver;
import com.zkteco.dbs.common.utils.I18nUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * I18nCellWriteHandler
 *
 * @author sheldon.wu
 * @date 2020/11/24 10:25
 * @since 1.0.0
 */
public class I18nCellWriteHandler implements CellWriteHandler {

    private final String lang;

    public I18nCellWriteHandler(String lang) {
        this.lang = lang;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head,
            Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            List<String> originHeadNames = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(originHeadNames)) {
                List<String> newHeadNames = originHeadNames.stream().
                        map(headName ->
                                PlaceholderResolver.getDefaultResolver().resolveByRule(headName,
                                        (name) -> I18nUtils.getValue(name, lang))).
                        collect(Collectors.toList());
                head.setHeadNameList(newHeadNames);
            }
        }
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head,
            Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell,
            Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList,
            Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }
}