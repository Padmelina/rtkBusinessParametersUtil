package bp.query;

import bp.model.resources.type.CheckError;
import bp.model.resources.type.FileNames;
import bp.model.resources.type.ParametersType;
import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.checker.entitycheckers.entity.OnlineTransfer;
import bp.query.generator.QueryAbstractCreator;
import bp.query.generator.implementations.InstallerVisitQueryCreator;
import bp.query.generator.implementations.OnlineTransferQueryCreator;
import bp.statistic.LogGenerator;
import bp.utils.FileNamesGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bp.model.resources.type.Action.ADD;
import static bp.model.resources.type.Action.DELETE;
import static bp.model.resources.type.ParametersType.INSTALLER_VISIT;
import static bp.model.resources.type.ParametersType.ONLINE_TRANSFER;

/**
 * Class for generating files with queries
 */
public class QueryFileGenerator {
    private FileNamesGenerator fileNamesGenerator;
    private QueryAbstractCreator queryGenerator;
    private LogGenerator logGenerator;

    public QueryFileGenerator() {
        fileNamesGenerator = new FileNamesGenerator();
        logGenerator = new LogGenerator(fileNamesGenerator);
    }

    public boolean generateScripts(Map<ParametersType, List<AbstractEntity>> validRows, Map<ParametersType, Map<AbstractEntity, CheckError>> results) {

        for (Map.Entry<ParametersType, List<AbstractEntity>> entry : validRows.entrySet()) {
            switch (entry.getKey()) {
                case INSTALLER_VISIT:
                    List <InstallerVisit> addInstallerVisit = new ArrayList<>();
                    List <InstallerVisit> deleteInstallerVisit = new ArrayList<>();
                    for (AbstractEntity visit : entry.getValue()) {
                        if (ADD.equals(((InstallerVisit) visit).getAction())) addInstallerVisit.add((InstallerVisit)visit);
                        if (DELETE.equals(((InstallerVisit) visit).getAction())) deleteInstallerVisit.add((InstallerVisit)visit);
                    }

                    FileNames addScripts = fileNamesGenerator.generateFileName(INSTALLER_VISIT, ADD);
                    FileNames deleteScripts = fileNamesGenerator.generateFileName(INSTALLER_VISIT, DELETE);

                    queryGenerator = new InstallerVisitQueryCreator();
                    queryGenerator.generateAdd(addScripts.getMainScriptName(), addInstallerVisit);
                    queryGenerator.generateCheckAdd(addScripts.getCheckMainScriptName(), addInstallerVisit);
                    queryGenerator.generateDelete(addScripts.getRevertScriptName(), addInstallerVisit);
                    queryGenerator.generateCheckDelete(addScripts.getCheckRevertScriptName(), addInstallerVisit);

                    queryGenerator.generateDelete(deleteScripts.getMainScriptName(), deleteInstallerVisit);
                    queryGenerator.generateCheckDelete(deleteScripts.getCheckMainScriptName(), deleteInstallerVisit);
                    queryGenerator.generateAdd(deleteScripts.getRevertScriptName(), deleteInstallerVisit);
                    queryGenerator.generateCheckAdd(deleteScripts.getCheckRevertScriptName(), deleteInstallerVisit);

                    logGenerator.generateLogFile(INSTALLER_VISIT, results.get(INSTALLER_VISIT));
                    break;
                case ONLINE_TRANSFER:
                    List <OnlineTransfer> addOnlineTransfer = new ArrayList<>();
                    List <OnlineTransfer> deleteOnlineTransfer = new ArrayList<>();
                    for (AbstractEntity transfer : entry.getValue()) {
                        if (ADD.equals(((OnlineTransfer) transfer).getAction())) addOnlineTransfer.add((OnlineTransfer) transfer);
                        if (DELETE.equals(((OnlineTransfer) transfer).getAction())) deleteOnlineTransfer.add((OnlineTransfer) transfer);
                    }

                    FileNames addTransferScripts = fileNamesGenerator.generateFileName(ONLINE_TRANSFER, ADD);
                    FileNames deleteTransferScripts = fileNamesGenerator.generateFileName(ONLINE_TRANSFER, DELETE);

                    queryGenerator = new OnlineTransferQueryCreator();
                    queryGenerator.generateAdd(addTransferScripts.getMainScriptName(), addOnlineTransfer);
                    queryGenerator.generateCheckAdd(addTransferScripts.getCheckMainScriptName(), addOnlineTransfer);
                    queryGenerator.generateDelete(addTransferScripts.getRevertScriptName(), addOnlineTransfer);
                    queryGenerator.generateCheckDelete(addTransferScripts.getCheckRevertScriptName(), addOnlineTransfer);

                    queryGenerator.generateDelete(deleteTransferScripts.getMainScriptName(), deleteOnlineTransfer);
                    queryGenerator.generateCheckDelete(deleteTransferScripts.getCheckMainScriptName(), deleteOnlineTransfer);
                    queryGenerator.generateAdd(deleteTransferScripts.getRevertScriptName(), deleteOnlineTransfer);
                    queryGenerator.generateCheckAdd(deleteTransferScripts.getCheckRevertScriptName(), deleteOnlineTransfer);
                    logGenerator.generateLogFile(ONLINE_TRANSFER, results.get(ONLINE_TRANSFER));
                    break;
            }
        }
        logGenerator.endLogFile();
        return false;
    }
}
