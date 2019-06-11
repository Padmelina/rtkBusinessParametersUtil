package bp.model.resources.mapper;

import bp.model.resources.type.*;
import bp.model.resources.LocalizeResources;
import bp.model.resources.ResourcesFromFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Methods to map ResourcesFromFile to LocalizeResources
 */
public class LocalizeResourcesFromFileMapper {
    public static LocalizeResources map(ResourcesFromFile resources) {
        LocalizeResources localize = new LocalizeResources();
        Map<String, ParametersType> typesBySheetNames = new HashMap<>();
        Map<ParametersType, String> namesBySheetType = new HashMap<>();
        Map<String, Action> actionsMap = new HashMap<>();
        Map<Action, String> actionsNamesMap = new HashMap<>();
        Map<FormMessages, String> messages = new HashMap<>();
        Map<CheckError, String> errorText = new HashMap<>();
        Map<ParametersType, Map<Titles, String>> heads = new HashMap<>();

        resources.getQueryNames().entrySet().forEach(name ->
            namesBySheetType.put(ParametersType.parseType(name.getKey()), name.getValue()));

        resources.getSheetNames().entrySet().forEach(sheet ->
                typesBySheetNames.put(sheet.getKey(), ParametersType.parseType(sheet.getValue())));

        resources.getActions().entrySet().forEach(action -> {
            actionsMap.put(action.getKey(), Action.parseAction(action.getValue()));
            actionsNamesMap.put(Action.parseAction(action.getValue()), action.getKey());});

        resources.getFormMessages().entrySet().forEach(step ->
            messages.put(FormMessages.parseMessage(step.getKey()), step.getValue()));

        resources.getErrorMapping().entrySet().forEach(error ->
            errorText.put(CheckError.parseError(error.getKey()), error.getValue()));

        resources.getHeads().entrySet().forEach(head -> {
            Map <Titles, String> titles = new HashMap<>();
            head.getValue().entrySet().forEach(title ->
                titles.put(Titles.parseTitle(title.getKey()), title.getValue()));
            heads.put(ParametersType.parseType(head.getKey()), titles);
            });

        localize.setTypesBySheetNames(typesBySheetNames);
        localize.setNamesBySheetType(namesBySheetType);
        localize.setActionsMap(actionsMap);
        localize.setActionsNamesMap(actionsNamesMap);
        localize.setMessages(messages);
        localize.setErrorText(errorText);
        localize.setHeads(heads);
        localize.setSqlConstants(resources.getSqlQueries());

        return localize;
    }
}
