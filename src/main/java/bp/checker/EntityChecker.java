package bp.checker;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.dbcheckers.implementations.*;
import bp.model.CheckError;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import bp.model.entity.InstallerVisit;

import java.sql.Connection;
import java.util.Map;

import static bp.model.Action.ADD;
import static bp.model.Action.DELETE;
import static bp.model.Action.UNKNOWN;
import static bp.model.CheckError.*;
import static bp.checker.entity.factory.DbEntitiesFactory.*;


public class EntityChecker {
    private Connection connection;
    private Map<String, String> constants;

    public EntityChecker(Connection connection, Map<String, String> constants) {
        this.connection = connection;
        this.constants = constants;
    }

    public <T extends AbstractEntity> CheckError check(ParametersType type, T row) {
        CheckError error = Ok;
        switch (type) {
            case INSTALLER_VISIT: error = checkInstallerVisit((InstallerVisit) row);
        }
        return error;
    }

    private CheckError checkInstallerVisit(InstallerVisit visit) {
        if (!visit.validate()) return RecordContainsEmptyValues;
        AbstractDbChecker checker;
        checker = new TechnologyChecker(connection, constants);
        if (!checker.check(getTehnologyFromInstallerVisit(visit))) return IncorrectTechnology;
        checker = new CaseTypesChecker(connection, constants);
        if (!checker.check(getCaseTypesFromInstallerVisit(visit))) return IncorrectCaseTypes;
        checker = new TerritoryChecker(connection, constants);
        if (!checker.check(getMrfFromInstallerVisit(visit))) return IncorrectTerritoryId;
        checker = new ProductChecker(connection, constants);
        if (!checker.check(getProductFromInstallerVisit(visit))) return IncorrectProduct;
        if (UNKNOWN.equals(visit.getAction())) return IncorrectAction;
        checker = new XCWfmMapChecker(connection, constants);
        boolean isExists = checker.check(visit);
        if (ADD.equals(visit.getAction()) && isExists) return RecordAlreadyExists;
        if (DELETE.equals(visit.getAction()) && !isExists) return RecordNotExists;
        return Ok;
    }
}
