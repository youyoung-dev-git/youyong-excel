package net.youyoung.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import net.youyoung.excel.core.ColumnMetadata;
import net.youyoung.excel.core.SheetMetadata;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Write handler that hides columns when all values are empty/null.
 */
public class EmptyColumnHideHandler implements SheetWriteHandler {

    private final SheetMetadata metadata;
    private final List<?> data;
    private final Set<Integer> emptyColumnIndices = new HashSet<>();

    public EmptyColumnHideHandler(SheetMetadata metadata, List<?> data) {
        this.metadata = metadata;
        this.data = data;
        analyzeEmptyColumns();
    }

    private void analyzeEmptyColumns() {
        List<ColumnMetadata> columns = metadata.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            ColumnMetadata column = columns.get(i);
            if (column.isHideIfEmpty() && isColumnEmpty(column)) {
                emptyColumnIndices.add(i);
            }
        }
    }

    private boolean isColumnEmpty(ColumnMetadata column) {
        if (data == null || data.isEmpty()) {
            return true;
        }

        Field field = column.getField();
        field.setAccessible(true);

        for (Object row : data) {
            try {
                Object value = field.get(row);
                if (value != null && !value.toString().isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                // Continue checking other rows
            }
        }
        return true;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (emptyColumnIndices.isEmpty()) {
            return;
        }

        Sheet sheet = writeSheetHolder.getSheet();
        for (Integer columnIndex : emptyColumnIndices) {
            sheet.setColumnHidden(columnIndex, true);
        }
    }

    /**
     * Get the indices of empty columns that will be hidden.
     *
     * @return set of column indices
     */
    public Set<Integer> getEmptyColumnIndices() {
        return emptyColumnIndices;
    }
}
