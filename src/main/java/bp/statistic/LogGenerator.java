package bp.statistic;

import bp.model.CheckError;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import bp.model.entity.InstallerVisit;
import bp.model.entity.OnlineTransfer;
import bp.statistic.implementations.InstallerVisitLogWriter;
import bp.statistic.implementations.OnlineTransferLogWriter;
import bp.utils.FileNamesGenerator;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogGenerator {
    protected FileNamesGenerator fileNameGenerator;
    protected CSVWriter csvWriter;
    protected AbstractLogWriter logWriter;
    protected Map<CheckError, String> errorText = new HashMap<>();

    public LogGenerator(FileNamesGenerator fileNameGenerator, Map<CheckError, String> errorText) {
        this.fileNameGenerator = fileNameGenerator;
        this.errorText = errorText;

        new File(System.getProperty("user.dir") + "/log").mkdirs();
        File file = new File(fileNameGenerator.generateLogFileName());
        FileWriter writer = null;
        try { writer = new FileWriter(file); }
        catch (IOException e) { }
        csvWriter = new CSVWriter(writer,
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
    }

    public void generateLogFile(ParametersType type, Map<AbstractEntity, CheckError> records) throws IOException {
        csvWriter.writeNext(new String[] {fileNameGenerator.getNamesBySheetType().get(type)});
        switch (type) {
            case INSTALLER_VISIT:
                Map<InstallerVisit, CheckError> visits = new HashMap<>();
                for (Map.Entry<AbstractEntity, CheckError> record : records.entrySet()) {
                    visits.put((InstallerVisit) record.getKey(), record.getValue());
                }
                logWriter = new InstallerVisitLogWriter(csvWriter, errorText);
                logWriter.writeToLogFile(visits);
                break;
            case ONLINE_TRANSFER:
                Map<OnlineTransfer, CheckError> transfers = new HashMap<>();
                for (Map.Entry<AbstractEntity, CheckError> record : records.entrySet()) {
                    transfers.put((OnlineTransfer) record.getKey(), record.getValue());
                }
                logWriter = new OnlineTransferLogWriter(csvWriter, errorText);
                logWriter.writeToLogFile(transfers);
                break;
        }
    }

    public void endLogFile() throws IOException {
        csvWriter.close();
    }
}
