package bp;


import bp.controller.LoadAndProcessExcelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static bp.context.Context.getContext;
import static bp.model.resources.type.FormMessages.TitleMessage;

public class Main extends Application {
    private LoadAndProcessExcelController mainController = new LoadAndProcessExcelController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/load_and_process_excel.fxml"));
        loader.setController(mainController);
        Parent root = loader.load();
        primaryStage.setTitle(getContext().getResources().getMessages().get(TitleMessage));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        getContext().closeConnection();
        super.stop();
    }
}
