package bp.model;

import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.model.resources.type.CheckError;
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
