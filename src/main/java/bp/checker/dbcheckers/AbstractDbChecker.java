package bp.checker.dbcheckers;

import bp.checker.entity.AbtractDbCheckEntity;

import java.sql.Connection;
import java.util.Map;

import static bp.model.Constants.TableNames.SA_TABLE;

public abstract class AbstractDbChecker <T extends AbtractDbCheckEntity> {
    protected String table;
    protected Connection connection;
    protected Map<String, String> constants;

    public AbstractDbChecker(Connection connection, Map<String, String> constants) {
        this.connection = connection;
        this.constants = constants;
        this.table = SA_TABLE;
    }

    public abstract boolean check(T value);

}
