package pecky;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Pecky pecky = new Pecky();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            pecky.MainWindow controller = fxmlLoader.getController();
            Ui.setMainWindow(controller);
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setPecky(pecky);  // inject the Pecky instance
            pecky.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
