package bp.statistic;


import bp.model.resources.type.CheckError;
import bp.checker.entitycheckers.entity.AbstractEntity;
import com.opencsv.CSVWriter;

import java.util.Map;

public abstract class AbstractLogWriter<T extends AbstractEntity> {
    protected CSVWriter csvWriter;

    public AbstractLogWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public abstract void writeToLogFile(Map<T, CheckError> records);
}
