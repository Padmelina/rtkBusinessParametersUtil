package bp.parser.sheets.implementations;

import bp.model.Action;
import bp.model.entity.InstallerVisit;
import bp.parser.sheets.AbstractSheetParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.jooq.tools.StringUtils.isEmpty;

public class InstallerVisitSheetParser extends AbstractSheetParser<InstallerVisit> {
    public InstallerVisitSheetParser(Map<String, Action> map) {
        super(map);
    }

    @Override
    public  List<InstallerVisit> parseSheet(Sheet sheet) {
        if (sheet == null) return null;
        List <InstallerVisit> sheetEntities = new ArrayList<>();
        List<Row> rows = getAllRowsWithoutHeader(sheet);

        for (Row row : rows) {
            Cell [] cells = new Cell[row.getLastCellNum()];
            int counter = 0;
            for (Cell cell : row) {
                cells[counter] = cell;
                counter++;
            }
            InstallerVisit entity = InstallerVisit.builder()
                    .technology(isEmpty(cells[0].getStringCellValue()) ? "" : cells[0].getStringCellValue().trim())
                    .typeOne(isEmpty(cells[1].getStringCellValue()) ? "" : cells[1].getStringCellValue().trim())
                    .typeTwo(isEmpty(cells[2].getStringCellValue()) ? "" : cells[2].getStringCellValue().trim())
                    .typeThree(isEmpty(cells[3].getStringCellValue()) ? "" : cells[3].getStringCellValue().trim())
                    .mrfId(isEmpty(cells[4].getStringCellValue()) ? "" : cells[4].getStringCellValue().trim())
                    .partNum(isEmpty(cells[5].getStringCellValue()) ? "": cells[5].getStringCellValue().trim())
                    .action(actions.get(cells[6].getStringCellValue().trim()))
                    .build();
            sheetEntities.add(entity);
        }

        return sheetEntities;
    }
}
