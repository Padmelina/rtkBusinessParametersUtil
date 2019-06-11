package bp.parser;

import bp.model.resources.type.ParametersType;
import bp.checker.entitycheckers.entity.AbstractEntity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.context.Context.getContext;

public class FileParser {
    private Map<ParametersType, Sheet> sheets = new HashMap<>();

    public <T extends AbstractEntity> Map<ParametersType, List<T>> parseFile(File file) {
        Map<ParametersType, List<T>> result = new HashMap<>();
        try {
            Workbook workbook  = new XSSFWorkbook(file);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                ParametersType listType = getContext().getResources().getTypesBySheetNames().get(sheet.getSheetName());
                if (listType != null) sheets.put(listType, sheet);
            }

            for (Map.Entry<ParametersType, Sheet> sheet : sheets.entrySet()) {
                result.put(sheet.getKey(), (List<T>) getContext().getParsers().get(sheet.getKey()).parseSheet(sheet.getValue()));
            }

        } catch (IOException | InvalidFormatException e) {
            getContext().getLogger().error(e.getMessage(), e);
        }
        return result;
    }


}
