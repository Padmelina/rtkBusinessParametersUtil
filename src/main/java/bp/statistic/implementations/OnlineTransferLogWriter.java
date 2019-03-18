package bp.statistic.implementations;

import bp.model.CheckError;
import bp.model.entity.OnlineTransfer;
import bp.statistic.AbstractLogWriter;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.Map;

public class OnlineTransferLogWriter extends AbstractLogWriter<OnlineTransfer> {
    public OnlineTransferLogWriter(CSVWriter csvWriter, Map<CheckError, String> errorText) {
        super(csvWriter, errorText);
    }

    @Override
    public void writeToLogFile(Map<OnlineTransfer, CheckError> records) throws IOException {
        for (Map.Entry<OnlineTransfer, CheckError> entity : records.entrySet()) {
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
