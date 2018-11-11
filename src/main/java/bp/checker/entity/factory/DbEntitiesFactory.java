package bp.checker.entity.factory;

import bp.checker.entity.implementations.CaseTypes;
import bp.checker.entity.implementations.StringField;
import bp.model.entity.InstallerVisit;

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
}
