package bp.checker.entitycheckers;

import bp.model.resources.type.CheckError;
import bp.checker.entitycheckers.entity.AbstractEntity;

public abstract class AbstractEntityChecker {
    public abstract CheckError check(AbstractEntity row);
}
