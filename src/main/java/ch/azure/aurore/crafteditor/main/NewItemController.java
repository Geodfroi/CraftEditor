package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.javaxt.strings.Strings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewItemController implements Initializable {

    @FXML
    public TextField labelTextField;
    @FXML
    public ComboBox<String> comboBox_class;

    private String itemName;
    private String clazz;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox_class.setOnAction(e -> clazz = comboBox_class.getSelectionModel().getSelectedItem());
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemClass() {
        return clazz;
    }

    public void setItemClass(String[] classes, String str) {
        comboBox_class.getItems().addAll(classes);
        clazz = Strings.isNullOrEmpty(str) ? classes[0] : str;
        comboBox_class.getSelectionModel().select(clazz);
    }
}
