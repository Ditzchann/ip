package angelapackage.gui;

import java.io.IOException;

import angelapackage.Angela;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Angela using FXML
 */
public class MainGUI extends Application {

    private Angela angela = new Angela();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setInstance(angela);  // inject the Angela instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
