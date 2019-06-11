package bp.checker.dbcheckers.entity.implementations;

import bp.checker.dbcheckers.entity.AbtractDbCheckEntity;
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
