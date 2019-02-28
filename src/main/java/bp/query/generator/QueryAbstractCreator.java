package bp.query.generator;

import bp.model.entity.AbstractEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class QueryAbstractCreator<T extends AbstractEntity> {
    protected Map<String, String> constants;

    public QueryAbstractCreator(Map<String, String> constants) {
        this.constants = constants;
    }

    public abstract boolean generateAdd(String fileName, List<T> records) throws IOException;
    public abstract boolean generateDelete(String fileName, List<T> records) throws IOException;
}
