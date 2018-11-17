package bp.parser.sheets;


import bp.model.Action;
import bp.model.entity.AbstractEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            rows.add(currentRow);
        }
        if (rows.size() > 1) rows.remove(0);
        return rows;
    }
}
