package bp.model.entity;

import bp.model.Action;
import lombok.Builder;
import lombok.Getter;

@Builder
public class InstallerVisit extends AbstractEntity {
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
