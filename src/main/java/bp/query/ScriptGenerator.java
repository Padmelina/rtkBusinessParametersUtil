package bp.query;
import bp.checker.checkers.AbstractDbChecker;
import bp.checker.checkers.implementations.XCWfmMapChecker;
import bp.checker.entity.AbtractDbCheckEntity;
import bp.checker.entity.implementations.WfmMap;
import bp.model.CheckError;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import bp.model.entity.InstallerVisit;
import bp.query.add.AbstractAddScriptGenerator;
import bp.query.add.implementations.InstallerVisitAddScriptGenerator;
import bp.query.getter.AbstractDataGetter;
import bp.query.getter.implementations.InstallerVisitDataGetter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.model.Action.ADD;
import static bp.model.ParametersType.INSTALLER_VISIT;

public class ScriptGenerator {
    private Connection connection;
    private AbstractDataGetter dataGetter;
    private Map<ParametersType, Queries> queries = new HashMap<>();
    private AbstractAddScriptGenerator scriptGenerator;

    public ScriptGenerator(Connection connection) {
        this.connection = connection;
    }

    public Map<ParametersType, Queries> generateScripts(Map<ParametersType, List<AbstractEntity>> entityList) throws IOException {
        for (Map.Entry<ParametersType, List<AbstractEntity>> entryMap : entityList.entrySet()) {
            switch (entryMap.getKey()) {
                case INSTALLER_VISIT:
                    List<AbtractDbCheckEntity> dataList = getData(INSTALLER_VISIT, entryMap.getValue());
                    scriptGenerator = new InstallerVisitAddScriptGenerator(connection);
                    scriptGenerator.generateAddScript(dataList);
                    break;
            }
        }
        return queries;
    }

    private List<AbtractDbCheckEntity> getData(ParametersType type, List<AbstractEntity> entityList) {
        List<AbtractDbCheckEntity> result = new ArrayList<>();
        switch (type) {
            case INSTALLER_VISIT:
                dataGetter = new InstallerVisitDataGetter(connection);
                for (AbstractEntity entity : entityList) {
                    InstallerVisit visit = (InstallerVisit)entity;
                    if (!ADD.equals(visit.getAction())) continue;
                    WfmMap map = (WfmMap)dataGetter.getDataFromDb(entity);
                    result.add(map);
                }
                break;
        }
        return result;
    }
}
