module CraftEditor {
    requires javafx.fxml;
    requires javaxt;

    exports ch.azure.aurore.crafteditor to javafx.graphics, javaxt, com.fasterxml.jackson.databind;
    exports ch.azure.aurore.crafteditor.main to javafx.fxml;
}