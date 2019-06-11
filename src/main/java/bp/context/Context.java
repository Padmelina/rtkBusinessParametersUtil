package bp.context;

import bp.checker.entitycheckers.AbstractEntityChecker;
import bp.checker.entitycheckers.entity.AbstractEntity;
import bp.checker.entitycheckers.entity.InstallerVisit;
import bp.checker.entitycheckers.implementations.InstallerVisitEntityChecker;
import bp.checker.entitycheckers.implementations.OnlineTransferEntityChecker;
import bp.model.resources.type.ParametersType;
import bp.model.database.DbConnectionProperties;
import bp.model.loader.YamlLoader;
import bp.model.resources.mapper.LocalizeResourcesFromFileMapper;
import bp.model.resources.LocalizeResources;
import bp.parser.FileParser;
import bp.parser.sheets.AbstractSheetParser;
import bp.parser.sheets.implementations.InstallerVisitSheetParser;
import bp.parser.sheets.implementations.OnlineTransferSheetParser;
import bp.query.QueryFileGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.model.resources.type.FormMessages.ChooseFileMessage;
import static bp.model.resources.type.ParametersType.INSTALLER_VISIT;
import static bp.model.resources.type.ParametersType.ONLINE_TRANSFER;
import static bp.model.constants.Constants.DataBaseConstants.DRIVER_CLASS_NAME;
import static bp.model.constants.Constants.FileChooserConstants.*;
import static bp.model.constants.Constants.ResourceFilesNames.DB_CONNECTION;
import static bp.model.constants.Constants.ResourceFilesNames.LOCALIZE_RESOURCES;
import static bp.model.constants.Constants.ResourceFilesNames.USER_DIR;

/**
 * Application context, includes configuration and all localize string map
 */
public class Context {
    private static volatile Context Instance;

    private LocalizeResources resources;

    private HikariConfig hikariConfig;
    private HikariDataSource dataSource;
    private Connection connection;
    private DbConnectionProperties dbConnectionProperties;

    private QueryFileGenerator queryGenerator;

    private FileChooser fileChooser;
    private FileParser fileParser;

    private Logger logger;

    private Map<ParametersType, AbstractSheetParser<? extends AbstractEntity>> parsers;
    private Map<ParametersType, AbstractEntityChecker> entityCheckers;

    private List<ParametersType> checkedTypes = new ArrayList<>();

    private Context() {
        logger = LoggerFactory.getLogger("business-parameters-util");
        try {
            dbConnectionProperties = YamlLoader.loadConnectionProperties(DB_CONNECTION);
            resources = LocalizeResourcesFromFileMapper.map(YamlLoader.loadLocalizeResources(LOCALIZE_RESOURCES));
            queryGenerator = new QueryFileGenerator();
            fileParser = new FileParser();

            initMaps();

            hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(dbConnectionProperties.getUrl());
            hikariConfig.setUsername(dbConnectionProperties.getLogin());
            hikariConfig.setPassword(dbConnectionProperties.getPassword());
            hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
            dataSource = new HikariDataSource(hikariConfig);

            connection = dataSource.getConnection();
        } catch (IOException | SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Add here parser and checker implementation for new business parameter type
     */
    private void initMaps() {
        parsers = new HashMap<ParametersType, AbstractSheetParser<? extends AbstractEntity>>() {
            {
                put(INSTALLER_VISIT, new InstallerVisitSheetParser());
                put(ONLINE_TRANSFER, new OnlineTransferSheetParser());
            }
        };

        entityCheckers = new HashMap<ParametersType, AbstractEntityChecker>() {
            {
                put(INSTALLER_VISIT, new InstallerVisitEntityChecker());
                put(ONLINE_TRANSFER, new OnlineTransferEntityChecker());
            }
        };
    }

    public static synchronized Context getContext() {
        if (Instance == null) { Instance = new Context(); }
        return Instance;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) connection.close();
    }

    public LocalizeResources getResources() {
        return resources;
    }

    public FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle(resources.getMessages().get(ChooseFileMessage));
            fileChooser.setInitialDirectory(new File(System.getProperty(USER_DIR)));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(XLSX_FILES, XLSX_EXTENSION));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(XML_FILES, XML_EXTENSION));
        }
        return fileChooser;
    }

    public FileParser getFileParser() {
        return fileParser;
    }

    public Connection getConnection() {
        return connection;
    }

    public Logger getLogger() {
        return logger;
    }


    public Map<ParametersType, AbstractSheetParser<? extends AbstractEntity>> getParsers() {
        return parsers;
    }

    public Map<ParametersType, AbstractEntityChecker> getEntityCheckers() {
        return entityCheckers;
    }

    public List<ParametersType> getCheckedTypes() {
        return checkedTypes;
    }

    public QueryFileGenerator getQueryGenerator() {
        return queryGenerator;
    }
}
