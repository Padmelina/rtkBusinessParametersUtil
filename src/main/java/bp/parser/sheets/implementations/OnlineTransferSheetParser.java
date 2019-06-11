package bp.parser.sheets.implementations;

import bp.checker.entitycheckers.entity.OnlineTransfer;
import bp.model.resources.type.Titles;
import bp.parser.sheets.AbstractSheetParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static bp.context.Context.getContext;
import static bp.model.resources.type.ParametersType.ONLINE_TRANSFER;
import static org.jooq.tools.StringUtils.isEmpty;

public class OnlineTransferSheetParser extends AbstractSheetParser<OnlineTransfer>  {
    @Override
    public List<OnlineTransfer> parseSheet(Sheet sheet) {
        if (sheet == null) return null;
        List <OnlineTransfer> sheetEntities = new ArrayList<>();
        List<Row> rows = getAllRowsWithoutHeader(sheet);
        List<String> headers = getHeaderRow(sheet);

        int technologyIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.TECHNOLOGY));
        int typeOneIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.TYPE_ONE));
        int typeTwoIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.TYPE_TWO));
        int typeThreeIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.TYPE_THREE));
        int mrfIdIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.MRF_ID));
        int productIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.PRODUCT));
        int actionIndex = headers.indexOf(getContext().getResources().getHeads().get(ONLINE_TRANSFER).get(Titles.ACTION));

        for (Row row : rows) {
            Cell[] cells = new Cell[row.getLastCellNum()];
            int counter = 0;
            for (Cell cell : row) {
                cells[counter] = cell;
                counter++;
            }

            OnlineTransfer entity = OnlineTransfer.builder()
                    .technology(isEmpty(cells[0].getStringCellValue()) ? "" : cells[technologyIndex].getStringCellValue().trim())
                    .typeOne(isEmpty(cells[1].getStringCellValue()) ? "" : cells[typeOneIndex].getStringCellValue().trim())
                    .typeTwo(isEmpty(cells[2].getStringCellValue()) ? "" : cells[typeTwoIndex].getStringCellValue().trim())
                    .typeThree(isEmpty(cells[3].getStringCellValue()) ? "" : cells[typeThreeIndex].getStringCellValue().trim())
                    .mrfId(isEmpty(cells[4].getStringCellValue()) ? "" : cells[mrfIdIndex].getStringCellValue().trim())
                    .partNum(isEmpty(cells[5].getStringCellValue()) ? "": cells[productIndex].getStringCellValue().trim())
                    .action(getContext().getResources().getActionsMap().get(cells[actionIndex].getStringCellValue().trim()))
                    .build();
            sheetEntities.add(entity);
        }

        return sheetEntities;
    }
}
