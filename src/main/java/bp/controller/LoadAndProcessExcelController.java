package bp.controller;

import bp.model.resources.type.CheckError;
import bp.model.HandleResult;
import bp.model.resources.type.ParametersType;
import bp.checker.entitycheckers.entity.AbstractEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import static bp.context.Context.getContext;
import static bp.model.resources.type.FormMessages.*;
import static bp.model.resources.type.CheckError.Ok;
import static bp.model.constants.Constants.ResourceFilesNames.USER_DIR;
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

    private File bpFile;

    private Map<ParametersType, List<AbstractEntity>> allSheets = new HashMap<>();
    private Map<ParametersType, List<AbstractEntity>> validRows = new HashMap<>();
    private Map<ParametersType, List<AbstractEntity>> invalidRows = new HashMap<>();
    private Map<ParametersType, Map<AbstractEntity, CheckError>> checkResults = new HashMap<>();

    public LoadAndProcessExcelController() {

    }

    @FXML
    private void onChooseButtonAction(ActionEvent event) {
        messageArea.clear();
        bpFile = getContext().getFileChooser().showOpenDialog(new Stage());
        allSheets = null;
        validRows.clear();
        invalidRows.clear();
        checkResults.clear();
        getContext().getCheckedTypes().clear();
        messageArea.setText(getContext().getResources().getMessages().get(FileParsingMessage));
        if (bpFile == null) {
            filePathField.clear();
            messageArea.setText(getContext().getResources().getMessages().get(NoFileMessage));
            return;
        }
        filePathField.setText(bpFile.getAbsolutePath());
        allSheets = getContext().getFileParser().parseFile(bpFile);
        if (allSheets == null || allSheets.size() == 0) {
            messageArea.setText(getContext().getResources().getMessages().get(NoNeededSheetMessage));
            return;
        }
        for (Map.Entry<ParametersType, List<AbstractEntity>> entry : allSheets.entrySet()) {
            getContext().getCheckedTypes().add(entry.getKey());
        }
        messageArea.setText(getContext().getResources().getMessages().get(FileCorrectMessage));
        processFileButton.setDisable(false);
    }

    @FXML
    private void onProcessButtonAction(ActionEvent event) {
        chooseFileButton.setDisable(true);
        processFileButton.setDisable(true);
        messageArea.setText(getContext().getResources().getMessages().get(FileCheckingMessage));
        allSheets.entrySet().forEach(entry -> {
            HandleResult handleResult = collectEntityHandleResult(entry.getKey(), entry.getValue());
            checkResults.put(entry.getKey(), handleResult.getReport());
            validRows.put(entry.getKey(), handleResult.getValid());
            invalidRows.put(entry.getKey(), handleResult.getErrors());
        });
        messageArea.setText(getContext().getResources().getMessages().get(ScriptMessage));
        generateResultStatistic();
        generateScripts();
        processFileButton.setDisable(false);
        chooseFileButton.setDisable(false);
    }

    private HandleResult collectEntityHandleResult(ParametersType parametersType, List<AbstractEntity> entities) {
        Map<AbstractEntity, CheckError> report = entities
                .stream()
                .collect(toMap(entity -> entity, entity -> getContext().getEntityCheckers().get(parametersType).check(entity)));
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
        messageArea.setText(getContext().getResources().getMessages().get(CheckEndedMessage));
        for (ParametersType type : getContext().getCheckedTypes()) {
            messageArea.appendText('\n' + getContext().getResources().getNamesBySheetType().get(type) + ": " + '\n');
            messageArea.appendText(MessageFormat.format(getContext().getResources().getMessages().get(StatisticSuccessMessage), validRows.get(type).size()) + '\n');
            messageArea.appendText(MessageFormat.format(getContext().getResources().getMessages().get(StatisticErrorsMessage), invalidRows.get(type).size()));
        }
    }

    private void generateScripts() {
        getContext().getQueryGenerator().generateScripts(validRows, checkResults);
        try {
            Desktop.getDesktop().open(new File(System.getProperty(USER_DIR)));
        } catch (IOException e) {
            getContext().getLogger().error(e.getMessage(), e); }
    }
}
