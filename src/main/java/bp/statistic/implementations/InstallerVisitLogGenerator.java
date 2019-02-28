package bp.statistic.implementations;

import bp.model.CheckError;
import bp.model.entity.InstallerVisit;
import bp.statistic.AbstractLogGenerator;
import bp.utils.FileNamesGenerator;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class InstallerVisitLogGenerator extends AbstractLogGenerator <InstallerVisit> {
    public InstallerVisitLogGenerator(FileNamesGenerator fileNameGenerator, Map<CheckError, String> errorText) throws IOException {
        super(fileNameGenerator, errorText);
    }

    @Override
    public void generateLogFile(Map<InstallerVisit, CheckError> records) throws IOException {
        new File(System.getProperty("user.dir") + "/log").mkdirs();
        File file = new File(fileNameGenerator.generateLogFileName());
        FileWriter writer = new FileWriter(file);
        csvWriter = new CSVWriter(writer,
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

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
        csvWriter.close();
    }
}
