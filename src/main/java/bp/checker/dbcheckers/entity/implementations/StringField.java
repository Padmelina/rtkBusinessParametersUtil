package bp.checker.dbcheckers.entity.implementations;

import bp.checker.dbcheckers.entity.AbtractDbCheckEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StringField implements AbtractDbCheckEntity {
    @Getter
    private String value;
}
