package bp.model;

import bp.model.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class HandleResult {
    List<AbstractEntity> valid;
    List<AbstractEntity> errors;
    Map<AbstractEntity, CheckError> report;
}
