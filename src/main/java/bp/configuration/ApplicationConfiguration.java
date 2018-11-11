package bp.configuration;

import bp.checker.EntityChecker;
import bp.database.DbConnectionProperties;
import bp.database.DbConnectionPropertiesLoader;
import bp.model.Action;
import bp.model.ApplicationStep;
import bp.model.ParametersType;
import bp.parser.FileParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static bp.model.Constants.DataBaseConstants.DRIVER_CLASS_NAME;
import static bp.model.Constants.FileChooserConstants.*;
import static bp.utils.ymlResourcesParser.getMapFromRecources;

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
    private Map<String, ParametersType> sheetNamesMap = new HashMap<>();
    @Getter
    private Map<String, Action> actionsMap = new HashMap<>();
    @Getter
    private Map<ApplicationStep, String> messages = new HashMap<>();
    @Getter
    private FileParser fileParser;

    private FileChooser fileChooser;

    public void init() throws IOException, SQLException {
        URL configFileUrl = ApplicationConfiguration.class.getClassLoader().getResource("db_connection.yml");
        if (configFileUrl == null) return;
        File configFile = new File(configFileUrl.getFile());
        dbConnectionProperties = DbConnectionPropertiesLoader.load(configFile);

        Map<String, String> sheetNames = getMapFromRecources("sheet_names.yml");
        for (Map.Entry <String, String> name : sheetNames.entrySet()) {
            sheetNamesMap.put(name.getKey(), ParametersType.parseType(name.getValue()));
        }

        Map<String, String> actions = getMapFromRecources("actions.yml");
        for (Map.Entry <String, String> action : actions.entrySet()) {
            actionsMap.put(action.getKey(), Action.parseAction(action.getValue()));
        }

        Map<String, String> steps = getMapFromRecources("messages.yml");
        for (Map.Entry <String, String> step : steps.entrySet()) {
            messages.put(ApplicationStep.parseStep(step.getKey()), step.getValue());
        }


        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbConnectionProperties.getUrl());
        hikariConfig.setUsername(dbConnectionProperties.getLogin());
        hikariConfig.setPassword(dbConnectionProperties.getPassword());
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);

        dataSource = new HikariDataSource(hikariConfig);
        connection = dataSource.getConnection();

        fileParser = new FileParser(actionsMap, sheetNamesMap);

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
}