package bp;

import bp.configuration.ApplicationConfiguration;
import bp.controller.LoadAndProcessExcelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private ApplicationConfiguration configuration = new ApplicationConfiguration();
    private LoadAndProcessExcelController mainController;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("/load_and_process_excel.fxml"));
        configuration.init();
        mainController = new LoadAndProcessExcelController(configuration);
        loader.setController(mainController);
        Parent root = loader.load();
        primaryStage.setTitle("Утилита автоматизированной настройки бизнес-параметров");
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        configuration.release();
        super.stop();
    }
}
