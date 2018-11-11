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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bp.model.ApplicationStep.FileCheckingStep;
import static bp.model.ApplicationStep.FileCorrectStep;
import static bp.model.ApplicationStep.FileParsingStep;

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

    private Map<ParametersType, List<AbstractEntity>> allSheets;

    private Map<ParametersType, Map <AbstractEntity, CheckError>> checkResults = new HashMap<>();

    public LoadAndProcessExcelController(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @FXML
    private void onChooseButtonAction(ActionEvent event) throws IOException, InvalidFormatException {
        bpFile = configuration.getFileChooser().showOpenDialog(new Stage());
        messageArea.setText(configuration.getMessages().get(FileParsingStep));
        if (bpFile != null) filePathField.setText(bpFile.getAbsolutePath());
        allSheets = configuration.getFileParser().parseFile(bpFile);
        if (allSheets == null || allSheets.size() == 0) return;
        messageArea.setText(configuration.getMessages().get(FileCorrectStep));
        processFileButton.setDisable(false);
        chooseFileButton.setDisable(true);
    }

    @FXML
    private void onProcessButtonAction(ActionEvent event)  {
        messageArea.setText(configuration.getMessages().get(FileCheckingStep));
        for (Map.Entry <ParametersType, List<AbstractEntity>> entryMap : allSheets.entrySet()) {
            switch (entryMap.getKey()) {
                case INSTALLER_VISIT:
                    for (AbstractEntity entity : entryMap.getValue()) {
                        CheckError error = configuration.getEntityChecker().check(entryMap.getKey(), entity);
                    }
            }
        }
    }
}
