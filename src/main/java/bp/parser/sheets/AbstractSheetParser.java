package bp.parser.sheets;


import bp.model.Action;
import bp.model.entity.AbstractEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.jooq.tools.StringUtils.isEmpty;

public abstract class AbstractSheetParser <T extends AbstractEntity> {
    protected Map<String, Action> actions;

    public AbstractSheetParser(Map<String, Action> map) {
        actions = map;
    }

    public abstract List<T> parseSheet(Sheet sheet);

    protected List<Row> getAllRowsWithoutHeader(Sheet sheet) {
        if (sheet == null) return null;
        List<Row> rows = new ArrayList<>(sheet.getLastRowNum());
        for (Row currentRow : sheet) {
            int counter = 0;
            for (Cell cell : currentRow) {
                if (isEmpty(cell.getStringCellValue())) counter++;
            }
            if (counter == currentRow.getLastCellNum()) break;
            rows.add(currentRow);
        }
        if (rows.size() > 1) rows.remove(0);
        return rows;
    }
}
