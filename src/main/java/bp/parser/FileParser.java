package bp.parser;

import bp.model.Action;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import bp.parser.sheets.AbstractSheetParser;
import bp.parser.sheets.implementations.InstallerVisitSheetParser;
import bp.parser.sheets.implementations.OnlineTransferSheetParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {
    private Map <String, Action> actions = new HashMap<>();
    private Map<String, ParametersType> sheetNames = new HashMap<>();
    private Map<ParametersType, Sheet> sheets = new HashMap<>();
    private AbstractSheetParser sheetParser;

    public FileParser(Map <String, Action> actions, Map<String, ParametersType> sheetNames) {
        this.actions = actions;
        this.sheetNames = sheetNames;
    }

    public <T extends AbstractEntity> Map<ParametersType, List<T>> parseFile(File file) throws IOException, InvalidFormatException {
        Map<ParametersType, List<T>> result = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            ParametersType listType = sheetNames.get(sheet.getSheetName());
            if (listType != null) sheets.put(listType, sheet);
        }

        for (Map.Entry<ParametersType, Sheet> sheet : sheets.entrySet()) {
            switch (sheet.getKey()) {
                case INSTALLER_VISIT:
                    sheetParser = new InstallerVisitSheetParser(actions);
                    result.put(sheet.getKey(), sheetParser.parseSheet(sheets.get(ParametersType.INSTALLER_VISIT)));
                    break;
                case ONLINE_TRANSFER:
                    sheetParser = new OnlineTransferSheetParser(actions);
                    result.put(sheet.getKey(), sheetParser.parseSheet(sheets.get(ParametersType.ONLINE_TRANSFER)));
                    break;
            }
        }

        return result;
    }
}
