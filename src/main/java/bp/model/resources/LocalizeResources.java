package bp.model.resources;

import bp.model.resources.type.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * All localize resources in maps with correct types
 */

public class LocalizeResources {
    @Getter
    @Setter
    private Map<String, ParametersType> typesBySheetNames = new HashMap<>();
    @Getter
    @Setter
    private Map<ParametersType, String> namesBySheetType = new HashMap<>();
    @Getter
    @Setter
    private Map<String, Action> actionsMap = new HashMap<>();
    @Getter
    @Setter
    private Map<Action, String> actionsNamesMap = new HashMap<>();
    @Getter
    @Setter
    private Map<FormMessages, String> messages = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> sqlConstants = new HashMap<>();
    @Getter
    @Setter
    private Map<CheckError, String> errorText = new HashMap<>();
    @Getter
    @Setter
    private Map<ParametersType, Map<Titles, String>> heads = new HashMap<>();
}
