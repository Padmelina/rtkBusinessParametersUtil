package bp.controller;

import bp.model.CheckError;
import bp.configuration.ApplicationConfiguration;
import bp.model.HandleResult;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import static bp.model.FormMessages.*;
import static bp.model.CheckError.Ok;
import static java.util.stream.Collectors.*;

public class LoadAndProcessExcelController {
    @FXML
    private Button processFileButton;
    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField filePathField;
    @FXML
    private TextArea messageArea;

    private ApplicationConfiguration configuration;

    private File bpFile;

    private Map<ParametersType, List<AbstractEntity>> allSheets = new HashMap<>();

    private Map<ParametersType, List<AbstractEntity>> validRows = new HashMap<>();
    private Map<ParametersType, List<AbstractEntity>> invalidRows = new HashMap<>();

    private Map<ParametersType, Map<AbstractEntity, CheckError>> checkResults = new HashMap<>();

    public LoadAndProcessExcelController(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @FXML
    private void onChooseButtonAction(ActionEvent event) throws IOException, InvalidFormatException {
        messageArea.clear();
        bpFile = configuration.getFileChooser().showOpenDialog(new Stage());
        allSheets = null;
        validRows.clear();
        invalidRows.clear();
        checkResults.clear();
        messageArea.setText(configuration.getMessages().get(FileParsingMessage));
        if (bpFile == null) {
            filePathField.clear();
            messageArea.setText(configuration.getMessages().get(NoFileMessage));
            return;
        }
        filePathField.setText(bpFile.getAbsolutePath());
        allSheets = configuration.getFileParser().parseFile(bpFile);
        if (allSheets == null || allSheets.size() == 0) {
            messageArea.setText(configuration.getMessages().get(NoNeededSheetMessage));
            return;
        }
        for (Map.Entry<ParametersType, List<AbstractEntity>> entry : allSheets.entrySet()) {
            configuration.getCheckedTypes().add(entry.getKey());
        }
        messageArea.setText(configuration.getMessages().get(FileCorrectMessage));
        processFileButton.setDisable(false);
    }

    @FXML
    private void onProcessButtonAction(ActionEvent event) throws IOException {
        chooseFileButton.setDisable(true);
        messageArea.setText(configuration.getMessages().get(FileCheckingMessage));
        allSheets.entrySet().forEach(entry -> handleEntity(entry.getKey(), entry.getValue()));
        messageArea.setText(configuration.getMessages().get(ScriptMessage));
        generateResultStatistic();
        generateScripts();
        chooseFileButton.setDisable(false);
    }

    private void handleEntity(ParametersType type, List<AbstractEntity> entities) {
        HandleResult handleResult = collectEntityHandleResult(type, entities);
        checkResults.put(type, handleResult.getReport());
        validRows.put(type, handleResult.getValid());
        invalidRows.put(type, handleResult.getErrors());
    }

    private HandleResult collectEntityHandleResult(ParametersType parametersType, List<AbstractEntity> entities) {
        Map<AbstractEntity, CheckError> report = entities
                .stream()
                .collect(toMap(entity -> entity, entity -> configuration.getEntityChecker().check(parametersType, entity)));
        List<AbstractEntity> valid = report.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == Ok)
                .map(Map.Entry::getKey)
                .collect(toList());
        List<AbstractEntity> errors = report.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != Ok)
                .map(Map.Entry::getKey)
                .collect(toList());
        return new HandleResult(valid, errors, report);
    }

    private void generateResultStatistic() {
        messageArea.setText(configuration.getMessages().get(CheckEndedMessage));
        for (ParametersType type : configuration.getCheckedTypes()) {
            messageArea.appendText('\n' + configuration.getNamesBySheetType().get(type) + ": " + '\n');
            messageArea.appendText(MessageFormat.format(configuration.getMessages().get(StatisticSuccessMessage), validRows.get(type).size()) + '\n');
            messageArea.appendText(MessageFormat.format(configuration.getMessages().get(StatisticErrorsMessage), invalidRows.get(type).size()));
        }
    }

    private void generateScripts() throws IOException {
        configuration.getQueryGenerator().generateScripts(validRows, checkResults);
        Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
    }
}
