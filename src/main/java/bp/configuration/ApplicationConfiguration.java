package bp.configuration;

import bp.checker.EntityChecker;
import bp.database.DbConnectionProperties;
import bp.database.DbConnectionPropertiesLoader;
import bp.model.Action;
import bp.model.FormMessages;
import bp.model.ParametersType;
import bp.parser.FileParser;
import bp.utils.FileNamesGenerator;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static bp.model.Constants.DataBaseConstants.DRIVER_CLASS_NAME;
import static bp.model.Constants.FileChooserConstants.*;
import static bp.model.Constants.ResourceFilesNames.*;
import static bp.model.ParametersType.INSTALLER_VISIT;

public class ApplicationConfiguration {
    @Getter
    private HikariConfig hikariConfig;
    @Getter
    private DbConnectionProperties dbConnectionProperties;
    @Getter
    private HikariDataSource dataSource;
    @Getter
    private EntityChecker entityChecker;
    @Getter
    private Connection connection;
    @Getter
    private Map<String, ParametersType> typesBySheetNames = new HashMap<>();
    @Getter
    private Map<ParametersType, String> namesBySheetType = new HashMap<>();
    @Getter
    private Map<String, Action> actionsMap = new HashMap<>();
    @Getter
    private Map<Action, String> actionsNamesMap = new HashMap<>();
    @Getter
    private Map<FormMessages, String> messages = new HashMap<>();
    @Getter
    private FileParser fileParser;
    @Getter
    private FileNamesGenerator fileNamesGenerator;
    @Getter
    private List<ParametersType> checkedTypes = new ArrayList<>(Arrays.asList(INSTALLER_VISIT));
    private FileChooser fileChooser;

    public void init() throws IOException, SQLException {

        dbConnectionProperties = DbConnectionPropertiesLoader.load(DB_CONNECTION);

        Map<String, String> sheetNames = getMapFromRecources(SHEETS_NAME);
        for (Map.Entry <String, String> name : sheetNames.entrySet()) {
            typesBySheetNames.put(name.getKey(), ParametersType.parseType(name.getValue()));
            namesBySheetType.put(ParametersType.parseType(name.getValue()), name.getKey());
        }

        Map<String, String> actions = getMapFromRecources(ACTIONS);
        for (Map.Entry <String, String> action : actions.entrySet()) {
            actionsMap.put(action.getKey(), Action.parseAction(action.getValue()));
            actionsNamesMap.put(Action.parseAction(action.getValue()), action.getKey());
        }

        Map<String, String> steps = getMapFromRecources(MESSAGES);
        for (Map.Entry <String, String> step : steps.entrySet()) {
            messages.put(FormMessages.parseMessage(step.getKey()), step.getValue());
        }

        fileNamesGenerator = new FileNamesGenerator(actionsNamesMap, namesBySheetType);

        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbConnectionProperties.getUrl());
        hikariConfig.setUsername(dbConnectionProperties.getLogin());
        hikariConfig.setPassword(dbConnectionProperties.getPassword());
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);

        dataSource = new HikariDataSource(hikariConfig);
        connection = dataSource.getConnection();

        fileParser = new FileParser(actionsMap, typesBySheetNames);

        entityChecker = new EntityChecker(connection);
    }

    public FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle(CHOOSER_TITLE);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(XLSX_FILES, XLSX_EXTENSION));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(XML_FILES, XML_EXTENSION));
        }
        return fileChooser;
    }

    public void release() throws SQLException {
        if (connection != null) connection.close();
    }

    private Map<String, String> getMapFromRecources(String recourceName) throws IOException {
        URL url = getClass().getClassLoader().getResource(recourceName);
        InputStreamReader streamReader = new InputStreamReader((InputStream) url.getContent(), Charset.forName(UTF_8_CHARSET));
        YamlReader reader = new YamlReader(streamReader);
        return (Map<String, String>) reader.read();
    }
}