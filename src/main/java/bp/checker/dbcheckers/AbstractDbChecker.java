package bp.checker.dbcheckers;

import bp.checker.dbcheckers.entity.AbtractDbCheckEntity;

import java.sql.Connection;
import java.util.Map;

import static bp.context.Context.getContext;
import static bp.model.constants.Constants.TableNames.SA_TABLE;

public abstract class AbstractDbChecker <T extends AbtractDbCheckEntity> {
    protected String table;
    protected Connection connection;
    protected Map<String, String> constants;

    public AbstractDbChecker() {
        this.connection = getContext().getConnection();
        this.constants = getContext().getResources().getSqlConstants();
        this.table = SA_TABLE;
    }

    public abstract boolean check(T value);

}
