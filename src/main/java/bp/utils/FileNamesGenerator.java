package bp.utils;

import bp.model.Action;
import bp.model.FileNames;
import bp.model.ParametersType;
import lombok.Getter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static bp.model.Action.CHECK;

public class FileNamesGenerator {
    private Map<Action, String> actionsNamesMap = new HashMap<>();
    @Getter
    private Map<ParametersType, String> namesBySheetType = new HashMap<>();

    public FileNamesGenerator(Map<Action, String> actionsNamesMap,  Map<ParametersType, String> namesBySheetType) {
        this.actionsNamesMap = actionsNamesMap;
        this.namesBySheetType = namesBySheetType;
    }

    public FileNames generateFileName(ParametersType type, Action action) {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        String main = System.getProperty("user.dir") + "/" + actionsNamesMap.get(action) + "_"
                + namesBySheetType.get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String revert = System.getProperty("user.dir") + "/" + actionsNamesMap.get(Action.REVERT) + "_"
                + actionsNamesMap.get(action) + "_"
                + namesBySheetType.get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String checkMain = System.getProperty("user.dir") + "/" + actionsNamesMap.get(CHECK) + "_" + actionsNamesMap.get(action) + "_"
                + namesBySheetType.get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String checkRevert = System.getProperty("user.dir") + "/" + actionsNamesMap.get(CHECK) + "_" + actionsNamesMap.get(Action.REVERT) + "_"
                + actionsNamesMap.get(action) + "_"
                + namesBySheetType.get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        return  new FileNames(main, revert, checkMain, checkRevert);
    }

    public String generateLogFileName() {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        return System.getProperty("user.dir") + "\\log\\"
                + "log" + "_"
                + formatter.format(date)
                + ".csv";
    }
}
