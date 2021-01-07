package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.App;
import ch.azure.aurore.crafteditor.EditorSettings;
import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.javaxt.strings.Strings;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuHandler {
    private final MainController main;
    private final Map<Token, CheckMenuItem> map = new HashMap<>();

    public MenuHandler(MainController main) {
        this.main = main;
        main.menuItem_close.setOnAction(e -> Platform.exit());
        main.menuItem_databaseFolder.setOnAction(e -> selectDatabaseLocation());
        main.menuItem_createFolder.setOnAction(e -> createNewFolder());

        map.put(Token.CRAFT, main.checkMenuItem_craft);
        map.put(Token.DUMMY, main.checkMenuItem_dummy);
        map.put(Token.EDEN, main.checkMenuItem_eden);

        for (CheckMenuItem c : map.values()) {
            c.setOnAction(actionEvent -> {
                for (Map.Entry<Token, CheckMenuItem> i : map.entrySet()) {
                    i.getValue().setSelected(i.getValue() == c);
                    if (i.getValue() == c){
                        Database.getInstance().load(i.getKey());
                        EditorState.getInstance().setCurrentDB(i.getKey().toString());
                    }
                }
                refreshMenus();
            });
        }

        refreshMenus();
    }

    private void createNewFolder() {
//        HierarchyNode selectedNode = main.getListViewHandler().getSelectedNode();
//        if (selectedNode == null || !selectedNode.isFolder()){
//            displayWarning("Can't create new folder as no parent folder is selected");
//            return;
//        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(main.root.getScene().getWindow());
        dialog.setTitle("Create new folder");

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/ch/azure/aurore/crafteditor/fxml/NewFolder.fxml"));
        NewFolderController dialogController = new NewFolderController();
        loader.setController(dialogController);

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create create dialog");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String name = dialogController.getFolderName();
            main.getListViewHandler().createNewFolder(name);
        }
    }

    public void refreshMenus() {
        boolean noLocation = Strings.isNullOrEmpty(EditorState.getInstance().getLocation());
        main.menu_databaseSelection.setVisible(!noLocation);
        main.menuItem_databaseFolder.setVisible(noLocation);

        Token token = Token.fromString(EditorState.getInstance().getCurrentDB());

        map.forEach((key, value) -> {
            if (key.equals(token))
                value.setSelected(true);
        });

        main.menu_createItem.getItems().clear();
        for (String classStr : EditorSettings.getInstance().getDefinedClassStr(token)) {
            MenuItem menu = new MenuItem("Create new [" + classStr + "]");
            main.menu_createItem.getItems().add(menu);
            menu.setOnAction(e -> {
                main.getListViewHandler().createNewEntry(classStr);
                System.out.printf("creating %s%n", classStr);
            });
        }

        main.menu_edit.setDisable(token == Token.NONE);
    }

    private void displayWarning(String label) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(label);
        alert.showAndWait();
    }

    private void selectDatabaseLocation() {
        System.out.println("select location");
        DirectoryChooser dialog = new DirectoryChooser();
        dialog.setTitle("Select data folder");
        File folder = dialog.showDialog(main.getScene().getWindow());

        if (folder != null) {
            String folderPath = folder.getAbsolutePath();
            EditorState.getInstance().setLocation(folderPath);
            refreshMenus();
        }
    }
}
