package bp.checker.entity.implementations;

import bp.checker.entity.AbtractDbCheckEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StringField implements AbtractDbCheckEntity {
    @Getter
    private String value;
}
