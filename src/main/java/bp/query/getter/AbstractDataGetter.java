package bp.query.getter;

import bp.model.entity.AbstractEntity;

import java.sql.Connection;

public class AbstractDataGetter <T extends AbstractEntity> {
    protected Connection connection;

    public AbstractDataGetter(Connection connection) {
        this.connection = connection;
    }

    public String getStringValue(String tableName, String clauseFieldName, String seekingFieldName, String value) {
        return "";
    }
}
