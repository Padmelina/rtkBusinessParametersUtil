package bp.statistic;


import bp.model.CheckError;
import bp.model.entity.AbstractEntity;
import bp.utils.FileNamesGenerator;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractLogGenerator <T extends AbstractEntity> {
    protected FileNamesGenerator fileNameGenerator;
    protected Map<CheckError, String> errorText;
    protected CSVWriter csvWriter;

    public AbstractLogGenerator(FileNamesGenerator fileNameGenerator, Map<CheckError, String> errorText) {
        this.fileNameGenerator = fileNameGenerator;
        this.errorText = errorText;
    }

    public abstract void generateLogFile(Map<T, CheckError> records) throws IOException;
}
