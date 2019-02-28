package bp.query;

import bp.model.CheckError;
import bp.model.FileNames;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import bp.model.entity.InstallerVisit;
import bp.query.generator.QueryAbstractCreator;
import bp.query.generator.implementations.InstallerVisitQueryCreator;
import bp.statistic.AbstractLogGenerator;
import bp.statistic.implementations.InstallerVisitLogGenerator;
import bp.utils.FileNamesGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.model.Action.ADD;
import static bp.model.Action.DELETE;
import static bp.model.ParametersType.INSTALLER_VISIT;

/**
 * Class for generating files with queries
 */
public class QueryFileGenerator {
    private FileNamesGenerator fileNamesGenerator;
    private QueryAbstractCreator queryGenerator;
    private AbstractLogGenerator logGenerator;
    private Map<CheckError, String> errorText;
    private Map<String, String> constants;

    public QueryFileGenerator(FileNamesGenerator fileNamesGenerator, Map<String, String> constants, Map<CheckError, String> errorText) {
        this.fileNamesGenerator = fileNamesGenerator;
        this.constants = constants;
        this.errorText = errorText;
    }

    public boolean generateScripts(Map<ParametersType, List<AbstractEntity>> validRows, Map<ParametersType, Map<AbstractEntity, CheckError>> results) throws IOException {
        for (Map.Entry<ParametersType, List<AbstractEntity>> entry : validRows.entrySet()) {
            switch (entry.getKey()) {
                case INSTALLER_VISIT:
                    List <InstallerVisit> add = new ArrayList<>();
                    List <InstallerVisit> delete = new ArrayList<>();
                    for (AbstractEntity visit : entry.getValue()) {
                        if (ADD.equals(((InstallerVisit) visit).getAction())) add.add((InstallerVisit)visit);
                        if (DELETE.equals(((InstallerVisit) visit).getAction())) delete.add((InstallerVisit)visit);
                    }

                    FileNames addScripts = fileNamesGenerator.generateFileName(INSTALLER_VISIT, ADD);
                    FileNames deleteScripts = fileNamesGenerator.generateFileName(INSTALLER_VISIT, DELETE);

                    queryGenerator = new InstallerVisitQueryCreator(constants);
                    queryGenerator.generateAdd(addScripts.getMainScriptName(), add);
                    queryGenerator.generateDelete(addScripts.getRevertScriptName(), add);

                    queryGenerator.generateDelete(deleteScripts.getMainScriptName(), delete);
                    queryGenerator.generateAdd(deleteScripts.getRevertScriptName(), delete);

                    Map<InstallerVisit, CheckError> errors = new HashMap<>();

                    for (Map.Entry<AbstractEntity, CheckError> result : results.get(INSTALLER_VISIT).entrySet()) {
                        errors.put((InstallerVisit)result.getKey(), result.getValue());
                    }
                    logGenerator = new InstallerVisitLogGenerator(fileNamesGenerator, errorText);
                    logGenerator.generateLogFile(errors);
                    break;
            }
        }
        return false;
    }
}
