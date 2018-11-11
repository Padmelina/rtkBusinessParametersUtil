package bp.utils;

import bp.configuration.ApplicationConfiguration;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Map;

public class ymlResourcesParser {
    public static Map<String, String> getMapFromRecources(String recourceName) throws FileNotFoundException, YamlException {
        URL sheetsFileUrl = ApplicationConfiguration.class.getClassLoader().getResource(recourceName);
        if (sheetsFileUrl == null) return null;
        File sheetNamesFile = new File(sheetsFileUrl.getFile());
        YamlReader reader = new YamlReader(new FileReader(sheetNamesFile));
        return (Map<String, String>) reader.read();
    }
}
