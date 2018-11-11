package bp.checker.checkers;

import bp.checker.entity.AbtractDbCheckEntity;

import java.sql.Connection;

import static bp.model.Constants.TableNames.SA_TABLE;

public abstract class AbstractDbChecker <T extends AbtractDbCheckEntity> {
    protected String table;
    protected Connection connection;

    public AbstractDbChecker(Connection connection) {
        this.connection = connection;
        this.table = SA_TABLE;
    }

    public abstract boolean check(T value);

}
