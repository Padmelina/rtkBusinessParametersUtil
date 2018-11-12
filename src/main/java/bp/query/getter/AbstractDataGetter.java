package bp.query.getter;

import bp.checker.entity.AbtractDbCheckEntity;
import bp.model.entity.AbstractEntity;

import java.sql.Connection;

public abstract class AbstractDataGetter <T extends AbstractEntity, E extends AbtractDbCheckEntity> {
    protected Connection connection;

    public AbstractDataGetter(Connection connection) {
        this.connection = connection;
    }

    public abstract E getDataFromDb(T value);
}
