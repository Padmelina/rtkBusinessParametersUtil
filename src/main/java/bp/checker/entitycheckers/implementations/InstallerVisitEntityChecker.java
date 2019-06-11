package bp.checker.entitycheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.dbcheckers.implementations.*;
import bp.checker.entitycheckers.AbstractEntityChecker;
import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.model.resources.type.CheckError;

import static bp.checker.dbcheckers.entity.factory.DbEntitiesFactory.*;
import static bp.model.resources.type.Action.ADD;
import static bp.model.resources.type.Action.DELETE;
import static bp.model.resources.type.Action.UNKNOWN;
import static bp.model.resources.type.CheckError.*;
import static bp.model.resources.type.CheckError.Ok;
import static bp.model.resources.type.CheckError.RecordNotExists;

public class InstallerVisitEntityChecker extends AbstractEntityChecker {
    @Override
    public CheckError check(AbstractEntity row) {
        InstallerVisit visit = (InstallerVisit) row;
        if (!visit.validate()) return RecordContainsEmptyValues;
        AbstractDbChecker checker;
        checker = new TechnologyChecker();
        if (!checker.check(getTehnologyFromInstallerVisit(visit))) return IncorrectTechnology;
        checker = new CaseTypesChecker();
        if (!checker.check(getCaseTypesFromInstallerVisit(visit))) return IncorrectCaseTypes;
        checker = new TerritoryChecker();
        if (!checker.check(getMrfFromInstallerVisit(visit))) return IncorrectTerritoryId;
        checker = new ProductChecker();
        if (!checker.check(getProductFromInstallerVisit(visit))) return IncorrectProduct;
        if (UNKNOWN.equals(visit.getAction())) return IncorrectAction;
        checker = new XCWfmMapChecker();
        boolean isExists = checker.check(visit);
        if (ADD.equals(visit.getAction()) && isExists) return RecordAlreadyExists;
        if (DELETE.equals(visit.getAction()) && !isExists) return RecordNotExists;
        return Ok;
    }
}
