package bp.model.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Resources mapped to string hash maps from file localize_resources.yml
 * In case of adding new group of constants new variables are needed
 */
public class ResourcesFromFile {
    @Getter
    @Setter
    private Map<String, String> sheetNames = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> queryNames = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> actions = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> formMessages = new HashMap<>();
    @Getter
    @Setter
    private Map<String, Map<String, String>> heads = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> errorMapping = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> sqlQueries = new HashMap<>();
}
