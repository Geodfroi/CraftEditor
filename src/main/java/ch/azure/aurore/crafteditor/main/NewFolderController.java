package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.javaxt.strings.Strings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFolderController implements Initializable {

    @FXML
    public TextField labelTextField;
    @FXML
    public VBox root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelTextField.textProperty().addListener((observableValue, s, t1) -> validate(t1));

        labelTextField.requestFocus();
    }

    private void validate(String str) {
        boolean validated = !Strings.isNullOrEmpty(str);

        DialogPane dialogPane = (DialogPane) root.getParent();
        dialogPane.lookupButton(ButtonType.OK).setDisable(!validated);
    }

    public String getFolderName() {
        return labelTextField.getText();
    }
}
