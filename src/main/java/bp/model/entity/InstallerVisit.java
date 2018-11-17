package bp.model.entity;

import bp.checker.entity.AbtractDbCheckEntity;
import bp.model.Action;
import lombok.Builder;
import lombok.Getter;

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
}
