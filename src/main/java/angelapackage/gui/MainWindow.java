package angelapackage.gui;
//solution below adapted from SE-EDU textbook
import angelapackage.Angela;
import angelapackage.exception.AngelaException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Angela instance;

    //image taken from https://library-of-ruina.fandom.com/wiki/Roland
    private Image rolandImage = new Image(this.getClass().getResourceAsStream("/images/Roland.png"));
    //image taken from https://lobotomycorp.fandom.com/wiki/Angela
    private Image angelaImage = new Image(this.getClass().getResourceAsStream("/images/Angela.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Angela instance */
    public void setInstance(Angela a) {
        instance = a;
        String response = Output.getLastOut();
        dialogContainer.getChildren().addAll(
                DialogBox.getAngelaDialog(response, angelaImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Angela's reply and then
     * appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        boolean bExit = false;
        if (input.isEmpty()) {
            Output.idleOutput();
        } else {
            bExit = instance.processCommand(input);
        }
        String response = Output.getLastOut();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, rolandImage),
                DialogBox.getAngelaDialog(response, angelaImage)
        );
        userInput.clear();
        if (bExit) {
            Platform.exit();
        }
    }
}