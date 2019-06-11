package bp.parser.sheets;

import bp.checker.entitycheckers.entity.AbstractEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static bp.utils.JavaExtensions.isEmpty;

public abstract class AbstractSheetParser <T extends AbstractEntity> {
    public abstract List<T> parseSheet(Sheet sheet);

    protected List<Row> getAllRowsWithoutHeader(Sheet sheet) {
        if (sheet == null) return null;
        List<Row> rows = new ArrayList<>(sheet.getLastRowNum());
        for (Row currentRow : sheet) {
            int emptyCellCounter = 0;
            int cellCounter = 0;
            for (Cell cell : currentRow) {
                if (isEmpty(cell.getStringCellValue())) emptyCellCounter++;
                cellCounter++;
            }
            if (emptyCellCounter == cellCounter) break;
            rows.add(currentRow);
        }
        if (rows.size() > 1) rows.remove(0);
        return rows;
    }

    protected List<String> getHeaderRow(Sheet sheet) {
        if (sheet == null) return null;
        List<String> headerList = new ArrayList();
        for (Cell cell : sheet.getRow(0)) {
            headerList.add(cell.getStringCellValue());
        }
        return headerList;
    }
}
