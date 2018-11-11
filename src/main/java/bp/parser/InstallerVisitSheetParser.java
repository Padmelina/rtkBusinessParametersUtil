package bp.parser;

import bp.model.Action;
import bp.model.entity.InstallerVisit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstallerVisitSheetParser extends AbstractSheetParser <InstallerVisit> {
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
                    .technology(cells[0].getStringCellValue().trim())
                    .typeOne(cells[1].getStringCellValue().trim())
                    .typeTwo(cells[2].getStringCellValue().trim())
                    .typeThree(cells[3].getStringCellValue().trim())
                    .mrfId(cells[4].getStringCellValue().trim())
                    .partNum(cells[5].getStringCellValue().trim())
                    .action(actions.get(cells[6].getStringCellValue().trim()))
                    .build();
            sheetEntities.add(entity);
        }

        return sheetEntities;
    }
}
