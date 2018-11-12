package bp.checker.entity.implementations;

import bp.checker.entity.AbtractDbCheckEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
public class WfmMap implements AbtractDbCheckEntity {
    @Getter
    private String technologyName;
    @Getter
    private String typeThreeObjid;
    @Getter
    private String territoryObjid;
    @Getter
    private String partNumObjid;
}
