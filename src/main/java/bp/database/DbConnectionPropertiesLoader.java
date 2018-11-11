package bp.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class DbConnectionPropertiesLoader {
    public static DbConnectionProperties load(File configFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        DbConnectionProperties connection = mapper.readValue(configFile, DbConnectionProperties.class);
        return connection;
    }
}
