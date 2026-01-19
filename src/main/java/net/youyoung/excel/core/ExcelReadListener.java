package net.youyoung.excel.core;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel read listener that collects all rows.
 *
 * @param <T> the type of data model
 */
public class ExcelReadListener<T> implements ReadListener<T> {

    private final List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(T data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // No-op
    }

    public List<T> getDataList() {
        return dataList;
    }
}
