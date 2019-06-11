package bp.statistic.implementations;

import bp.model.resources.type.CheckError;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.statistic.AbstractLogWriter;
import com.opencsv.CSVWriter;

import java.util.Map;

import static bp.context.Context.getContext;

public class InstallerVisitLogWriter extends AbstractLogWriter<InstallerVisit> {
    public InstallerVisitLogWriter(CSVWriter csvWriter) {
        super(csvWriter);
    }

    @Override
    public void writeToLogFile(Map<InstallerVisit, CheckError> records) {
        for (Map.Entry<InstallerVisit, CheckError> entity : records.entrySet()) {
            String[] line = { entity.getKey().getTechnology(),
                                entity.getKey().getPartNum(),
                                entity.getKey().getMrfId(),
                                entity.getKey().getTypeOne(),
                                entity.getKey().getTypeTwo(),
                                entity.getKey().getTypeThree(),
                                entity.getKey().getAction() == null ? "" : entity.getKey().getAction().getAction(),
                                getContext().getResources().getErrorText().get(entity.getValue()) };
            csvWriter.writeNext(line);
        }
    }
}
