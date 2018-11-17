package bp.utils;

import bp.model.Action;
import bp.model.ParametersType;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileNamesGenerator {
    private Map<Action, String> actionsNamesMap = new HashMap<>();
    private Map<ParametersType, String> namesBySheetType = new HashMap<>();

    public FileNamesGenerator(Map<Action, String> actionsNamesMap,  Map<ParametersType, String> namesBySheetType) {
        this.actionsNamesMap = actionsNamesMap;
        this.namesBySheetType = namesBySheetType;
    }

    public String generateFileName(ParametersType type, Action action) {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        return  System.getProperty("user.dir") + "/" + actionsNamesMap.get(action) + "_"
                + namesBySheetType.get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
    }
}
