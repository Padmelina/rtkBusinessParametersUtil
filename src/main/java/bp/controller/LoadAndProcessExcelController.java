package bp.controller;

import bp.model.CheckError;
import bp.configuration.ApplicationConfiguration;
import bp.model.ParametersType;
import bp.model.entity.AbstractEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.model.FormMessages.*;
import static bp.model.CheckError.Ok;
import static bp.model.ParametersType.INSTALLER_VISIT;

public class LoadAndProcessExcelController {
    @FXML
    private Button processFileButton;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button logButton;
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
        bpFile = configuration.getFileChooser().showOpenDialog(new Stage());

        filePathField.clear();
        if (bpFile == null) {
            messageArea.setText(configuration.getMessages().get(NoFileMessage));
            return;
        }
        filePathField.setText(bpFile.getAbsolutePath());
        allSheets = configuration.getFileParser().parseFile(bpFile);
        if (allSheets == null || allSheets.size() == 0) return;
        messageArea.setText(configuration.getMessages().get(FileCorrectMessage));
        processFileButton.setDisable(false);
        chooseFileButton.setDisable(true);
    }

    @FXML
    private void onProcessButtonAction(ActionEvent event) throws IOException {
        messageArea.setText(configuration.getMessages().get(FileCheckingMessage));
        processFileButton.setDisable(true);
        for (Map.Entry <ParametersType, List<AbstractEntity>> entryMap : allSheets.entrySet()) {
            switch (entryMap.getKey()) {
                case INSTALLER_VISIT:
                    List<AbstractEntity> valid = new ArrayList<>();
                    List<AbstractEntity> errors = new ArrayList<>();
                    Map<AbstractEntity, CheckError> report = new HashMap<>();
                    for (AbstractEntity entity : entryMap.getValue()) {
                        CheckError error = configuration.getEntityChecker().check(entryMap.getKey(), entity);
                        if (!Ok.equals(error)) {
                            errors.add(entity);
                        } else valid.add(entity);
                        report.put(entity, error);
                    }
                    checkResults.put(INSTALLER_VISIT, report);
                    validRows.put(INSTALLER_VISIT, valid);
                    invalidRows.put(INSTALLER_VISIT, errors);
                    break;
            }
        }
        messageArea.setText(configuration.getMessages().get(ScriptMessage));
        generateResultStatistic();
        logButton.setDisable(false);
    }

    private void generateResultStatistic() {
        messageArea.setText(configuration.getMessages().get(CheckEndedMessage));
        for (ParametersType type : configuration.getCheckedTypes()) {
            messageArea.appendText('\n' + configuration.getNamesBySheetType().get(type) + ": " + '\n');
            messageArea.appendText(MessageFormat.format(configuration.getMessages().get(StatisticSuccessMessage), validRows.get(type).size()) + '\n');
            messageArea.appendText(MessageFormat.format(configuration.getMessages().get(StatisticErrorsMessage), invalidRows.get(type).size()));

        }
    }
}
