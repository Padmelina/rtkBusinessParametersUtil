package bp.model.loader;

import bp.model.database.DbConnectionProperties;
import bp.model.resources.ResourcesFromFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import static bp.model.constants.Constants.FileChooserConstants.UTF_8_CHARSET;

public class YamlLoader {
    public static DbConnectionProperties loadConnectionProperties(String fileName) throws IOException {
        URL url = YamlLoader.class.getClassLoader().getResource(fileName);
        InputStreamReader streamReader = new InputStreamReader((InputStream) url.getContent(), Charset.forName(UTF_8_CHARSET));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        DbConnectionProperties connection = mapper.readValue(streamReader, DbConnectionProperties.class);
        return connection;
    }

    public static ResourcesFromFile loadLocalizeResources(String fileName) throws IOException {
        URL url = YamlLoader.class.getClassLoader().getResource(fileName);
        InputStreamReader streamReader = new InputStreamReader((InputStream) url.getContent(), Charset.forName(UTF_8_CHARSET));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ResourcesFromFile resources = mapper.readValue(streamReader, ResourcesFromFile.class);
        return resources;
    }
}
