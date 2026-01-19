package net.youyoung.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import net.youyoung.excel.core.ColumnMetadata;
import net.youyoung.excel.core.SheetMetadata;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * Write handler that applies annotation-based configurations.
 */
public class AnnotationWriteHandler implements SheetWriteHandler, CellWriteHandler {

    private final SheetMetadata metadata;

    public AnnotationWriteHandler(SheetMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        List<ColumnMetadata> columns = metadata.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            ColumnMetadata column = columns.get(i);
            if (column.getWidth() > 0) {
                sheet.setColumnWidth(i, column.getWidth() * 256);
            }
        }

        if (metadata.getHeaderHeight() > 0) {
            sheet.setDefaultRowHeight((short) (metadata.getHeaderHeight() * 20));
        }
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        // Additional cell processing can be added here if needed
    }
}
