package bp.statistic;


import bp.model.CheckError;
import bp.model.entity.AbstractEntity;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractLogWriter<T extends AbstractEntity> {
    protected Map<CheckError, String> errorText;
    protected CSVWriter csvWriter;

    public AbstractLogWriter(CSVWriter csvWriter, Map<CheckError, String> errorText) {
        this.errorText = errorText;
        this.csvWriter = csvWriter;
    }

    public abstract void writeToLogFile(Map<T, CheckError> records) throws IOException;
}
