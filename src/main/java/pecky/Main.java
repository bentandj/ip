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

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Chat with Pecky! :D");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            pecky.MainWindow controller = fxmlLoader.getController();
            Ui.setMainWindow(controller);
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            Pecky pecky = new Pecky();
            fxmlLoader.<MainWindow>getController().setPecky(pecky); // inject the Pecky instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
