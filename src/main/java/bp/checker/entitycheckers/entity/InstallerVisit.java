package bp.checker.entitycheckers.entity;

import bp.checker.dbcheckers.entity.AbtractDbCheckEntity;
import bp.model.resources.type.Action;
import lombok.Builder;
import lombok.Getter;

import static bp.utils.JavaExtensions.isNotEmpty;

@Builder
public class InstallerVisit extends AbstractEntity implements AbtractDbCheckEntity {
    @Getter
    private String technology;
    @Getter
    private String typeOne;
    @Getter
    private String typeTwo;
    @Getter
    private String typeThree;
    @Getter
    private String mrfId;
    @Getter
    private String partNum;
    @Getter
    private Action action;

    public boolean validate() {
        return isNotEmpty(technology) &&
                isNotEmpty(typeOne) &&
                isNotEmpty(typeTwo) &&
                isNotEmpty(typeThree) &&
                isNotEmpty(mrfId) &&
                isNotEmpty(partNum) &&
                isNotEmpty(action);

    }
}
