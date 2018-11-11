package bp.query;

import java.sql.Connection;

public class QueryDataGetter {
    protected Connection connection;

    public QueryDataGetter(Connection connection) {
        this.connection = connection;
    }

    public String getStringValue(String tableName, String clauseFieldName, String seekingFieldName, String value) {
        return "";
    }
}
