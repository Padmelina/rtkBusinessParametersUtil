package bp.checker.entity.implementations;

import bp.checker.entity.AbtractDbCheckEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
public class CaseTypes implements AbtractDbCheckEntity {
    @Getter
    private String typeOne;
    @Getter
    private String typeTwo;
    @Getter
    private String typeThree;
}
