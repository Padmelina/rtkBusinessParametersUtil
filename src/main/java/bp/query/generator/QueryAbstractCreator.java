package bp.query.generator;

import bp.checker.entitycheckers.entity.AbstractEntity;

import java.util.List;

public abstract class QueryAbstractCreator<T extends AbstractEntity> {
    public abstract boolean generateAdd(String fileName, List<T> records);
    public abstract boolean generateDelete(String fileName, List<T> records);
    public abstract boolean generateCheckAdd(String fileName, List<T> records);
    public abstract boolean generateCheckDelete(String fileName, List<T> records);
}
