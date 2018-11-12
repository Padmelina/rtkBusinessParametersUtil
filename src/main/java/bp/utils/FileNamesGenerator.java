package bp.utils;

import bp.model.Action;
import bp.model.ParametersType;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNamesGenerator {

    public static String generateFileName(ParametersType type, Action action) {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm");
        return  System.getProperty("user.dir").toString() + "/" + action.getAction() + "_"
                + type.getType() + "_"
                + formatter.format(date)
                + ".sql";
    }
}
