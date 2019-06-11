package bp.checker.entitycheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.dbcheckers.implementations.*;
import bp.checker.entitycheckers.AbstractEntityChecker;
import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.checker.entitycheckers.entity.OnlineTransfer;
import bp.model.resources.type.CheckError;

import static bp.checker.dbcheckers.entity.factory.DbEntitiesFactory.*;
import static bp.model.resources.type.Action.ADD;
import static bp.model.resources.type.Action.DELETE;
import static bp.model.resources.type.Action.UNKNOWN;
import static bp.model.resources.type.CheckError.*;
import static bp.model.resources.type.CheckError.Ok;
import static bp.model.resources.type.CheckError.RecordNotExists;

public class OnlineTransferEntityChecker extends AbstractEntityChecker {
    @Override
    public CheckError check(AbstractEntity row) {
        OnlineTransfer transfer = (OnlineTransfer) row;
        if (!transfer.validate()) return RecordContainsEmptyValues;
        AbstractDbChecker checker;
        checker = new TechnologyChecker();
        if (!checker.check(getTehnologyFromOnlineTransfer(transfer))) return IncorrectTechnology;
        checker = new CaseTypesChecker();
        if (!checker.check(getCaseTypesFromOnlineTransfer(transfer))) return IncorrectCaseTypes;
        checker = new TerritoryChecker();
        if (!checker.check(getMrfFromOnlineTransfer(transfer))) return IncorrectTerritoryId;
        checker = new ProductChecker();
        if (!checker.check(getProductFromOnlineTransfer(transfer))) return IncorrectProduct;
        if (UNKNOWN.equals(transfer.getAction())) return IncorrectAction;
        checker = new XCLstMapChecker();
        boolean isExists = checker.check(transfer);
        if (ADD.equals(transfer.getAction()) && isExists) return RecordAlreadyExists;
        if (DELETE.equals(transfer.getAction()) && !isExists) return RecordNotExists;
        return Ok;
    }
}
