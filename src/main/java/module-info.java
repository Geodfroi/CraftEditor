module CraftEditor {
    requires javafx.fxml;
    requires javaxt;
    requires javafx.controls;

    exports ch.azure.aurore.crafteditor to javafx.graphics, javaxt, com.fasterxml.jackson.databind;
    exports ch.azure.aurore.crafteditor.main to javafx.fxml;
    exports ch.azure.aurore.crafteditor.data to javaxt;
    exports ch.azure.aurore.crafteditor.data.dummy to javaxt;
}