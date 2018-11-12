package bp.query.add;

import bp.checker.entity.AbtractDbCheckEntity;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public abstract class AbstractAddScriptGenerator <T extends AbtractDbCheckEntity> {
    protected Connection connection;

    public AbstractAddScriptGenerator(Connection connection) {
        this.connection = connection;
    }

    public abstract void generateAddScript(List<T> addRows) throws IOException;

    public abstract void generateRevertAddScript();
}
