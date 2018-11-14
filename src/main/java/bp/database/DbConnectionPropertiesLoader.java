package bp.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import static bp.model.Constants.FileChooserConstants.UTF_8_CHARSET;

public class DbConnectionPropertiesLoader {
    public static DbConnectionProperties load(String fileName) throws IOException {
        URL url = DbConnectionPropertiesLoader.class.getClassLoader().getResource(fileName);
        InputStreamReader streamReader = new InputStreamReader((InputStream) url.getContent(), Charset.forName(UTF_8_CHARSET));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        DbConnectionProperties connection = mapper.readValue(streamReader, DbConnectionProperties.class);
        return connection;
    }
}
