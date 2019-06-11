package bp.statistic;

import bp.model.resources.type.CheckError;
import bp.model.resources.type.ParametersType;
import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.checker.entitycheckers.entity.OnlineTransfer;
import bp.statistic.implementations.InstallerVisitLogWriter;
import bp.statistic.implementations.OnlineTransferLogWriter;
import bp.utils.FileNamesGenerator;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static bp.context.Context.getContext;
import static bp.model.constants.Constants.ResourceFilesNames.USER_DIR;

public class LogGenerator {
    protected FileNamesGenerator fileNameGenerator;
    protected CSVWriter csvWriter;
    protected AbstractLogWriter logWriter;

    public LogGenerator(FileNamesGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;

        new File(System.getProperty(USER_DIR) + "/log").mkdirs();
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

    public void generateLogFile(ParametersType type, Map<AbstractEntity, CheckError> records) {
        csvWriter.writeNext(new String[] {getContext().getResources().getNamesBySheetType().get(type)});
        switch (type) {
            case INSTALLER_VISIT:
                Map<InstallerVisit, CheckError> visits = new HashMap<>();
                for (Map.Entry<AbstractEntity, CheckError> record : records.entrySet()) {
                    visits.put((InstallerVisit) record.getKey(), record.getValue());
                }
                logWriter = new InstallerVisitLogWriter(csvWriter);
                logWriter.writeToLogFile(visits);
                break;
            case ONLINE_TRANSFER:
                Map<OnlineTransfer, CheckError> transfers = new HashMap<>();
                for (Map.Entry<AbstractEntity, CheckError> record : records.entrySet()) {
                    transfers.put((OnlineTransfer) record.getKey(), record.getValue());
                }
                logWriter = new OnlineTransferLogWriter(csvWriter);
                logWriter.writeToLogFile(transfers);
                break;
        }
    }

    public void endLogFile() {
        try { csvWriter.close(); }
        catch (IOException e) { getContext().getLogger().error(e.getMessage(), e); }
    }
}
