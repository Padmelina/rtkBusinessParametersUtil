package bp.utils;

import bp.model.resources.type.Action;
import bp.model.resources.type.FileNames;
import bp.model.resources.type.ParametersType;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static bp.context.Context.getContext;
import static bp.model.resources.type.Action.CHECK;
import static bp.model.constants.Constants.DateConstants.LOG_FILE_FORMAT;
import static bp.model.constants.Constants.ResourceFilesNames.USER_DIR;

public class FileNamesGenerator {

    public FileNames generateFileName(ParametersType type, Action action) {
        Date date = new Date();
        Format formatter = new SimpleDateFormat(LOG_FILE_FORMAT);

        String main = System.getProperty(USER_DIR) + "/"
                + getContext().getResources().getActionsNamesMap().get(action) + "_"
                + getContext().getResources().getNamesBySheetType().get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String revert = System.getProperty(USER_DIR) + "/"
                + getContext().getResources().getActionsNamesMap().get(Action.REVERT) + "_"
                + getContext().getResources().getActionsNamesMap().get(action) + "_"
                + getContext().getResources().getNamesBySheetType().get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String checkMain = System.getProperty(USER_DIR) + "/"
                + getContext().getResources().getActionsNamesMap().get(CHECK) + "_"
                + getContext().getResources().getActionsNamesMap().get(action) + "_"
                + getContext().getResources().getNamesBySheetType().get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";
        String checkRevert = System.getProperty(USER_DIR) + "/"
                + getContext().getResources().getActionsNamesMap().get(CHECK) + "_"
                + getContext().getResources().getActionsNamesMap().get(Action.REVERT) + "_"
                + getContext().getResources().getActionsNamesMap().get(action) + "_"
                + getContext().getResources().getNamesBySheetType().get(type).replace(' ', '_') + "_"
                + formatter.format(date)
                + ".sql";

        return  new FileNames(main, revert, checkMain, checkRevert);
    }

    public String generateLogFileName() {
        Date date = new Date();
        Format formatter = new SimpleDateFormat(LOG_FILE_FORMAT);
        return System.getProperty(USER_DIR) + "\\log\\"
                + "log" + "_"
                + formatter.format(date)
                + ".csv";
    }
}
