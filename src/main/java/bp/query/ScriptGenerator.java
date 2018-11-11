package bp.query;


import bp.model.ParametersType;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ScriptGenerator {
    private Connection connection;
    private Map<ParametersType, Queries> queries = new HashMap<>();

    public ScriptGenerator(Connection connection) {
        this.connection = connection;
    }

    public Map<ParametersType, Queries> generateScripts() {
        return queries;
    }
}
