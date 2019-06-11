package bp.checker.dbcheckers.entity.factory;

import bp.checker.dbcheckers.entity.implementations.CaseTypes;
import bp.checker.dbcheckers.entity.implementations.StringField;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.checker.entitycheckers.entity.OnlineTransfer;

public class DbEntitiesFactory {

    public static StringField getTehnologyFromInstallerVisit(InstallerVisit visit) {
        return new StringField(visit.getTechnology());
    }

    public static CaseTypes getCaseTypesFromInstallerVisit(InstallerVisit visit) {
        return CaseTypes.builder()
                .typeOne(visit.getTypeOne())
                .typeTwo(visit.getTypeTwo())
                .typeThree(visit.getTypeThree())
                .build();
    }

    public static StringField getMrfFromInstallerVisit(InstallerVisit visit) {
        return new StringField(visit.getMrfId());
    }

    public static StringField getProductFromInstallerVisit(InstallerVisit visit) {
        return new StringField(visit.getPartNum());
    }

    public static StringField getTehnologyFromOnlineTransfer(OnlineTransfer transfer) {
        return new StringField(transfer.getTechnology());
    }

    public static CaseTypes getCaseTypesFromOnlineTransfer(OnlineTransfer transfer) {
        return CaseTypes.builder()
                .typeOne(transfer.getTypeOne())
                .typeTwo(transfer.getTypeTwo())
                .typeThree(transfer.getTypeThree())
                .build();
    }

    public static StringField getMrfFromOnlineTransfer(OnlineTransfer transfer) {
        return new StringField(transfer.getMrfId());
    }

    public static StringField getProductFromOnlineTransfer(OnlineTransfer transfer) {
        return new StringField(transfer.getPartNum());
    }
}
