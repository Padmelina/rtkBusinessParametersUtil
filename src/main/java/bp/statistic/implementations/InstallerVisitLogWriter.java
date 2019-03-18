package bp.statistic.implementations;

import bp.model.CheckError;
import bp.model.entity.InstallerVisit;
import bp.statistic.AbstractLogWriter;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.Map;

public class InstallerVisitLogWriter extends AbstractLogWriter<InstallerVisit> {
    public InstallerVisitLogWriter(CSVWriter csvWriter, Map<CheckError, String> errorText) throws IOException {
        super(csvWriter, errorText);
    }

    @Override
    public void writeToLogFile(Map<InstallerVisit, CheckError> records) throws IOException {
        for (Map.Entry<InstallerVisit, CheckError> entity : records.entrySet()) {
            String[] line = { entity.getKey().getTechnology(),
                                entity.getKey().getPartNum(),
                                entity.getKey().getMrfId(),
                                entity.getKey().getTypeOne(),
                                entity.getKey().getTypeTwo(),
                                entity.getKey().getTypeThree(),
                                entity.getKey().getAction() == null ? "" : entity.getKey().getAction().getAction(),
                                errorText.get(entity.getValue()) };
            csvWriter.writeNext(line);
        }
    }
}
