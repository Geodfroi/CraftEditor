package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.javaxt.strings.Strings;
import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MenuHandler {
    private final MainController main;
    private final Map<Token, CheckMenuItem> map = new HashMap<>();

    public MenuHandler(MainController main) {
        this.main = main;
        main.menuItem_close.setOnAction(e -> Platform.exit());
        main.menuItem_databaseFolder.setOnAction(e -> selectDatabaseLocation());

        map.put(Token.CRAFT, main.checkMenuItem_craft);
        map.put(Token.DUMMY, main.checkMenuItem_dummy);
        map.put(Token.EDEN, main.checkMenuItem_eden);

        for (CheckMenuItem c : map.values()) {
            c.setOnAction(actionEvent -> {
                for (Map.Entry<Token, CheckMenuItem> i : map.entrySet()) {
                    i.getValue().setSelected(i.getValue() == c);
                    if (i.getValue() == c)
                        main.loadDB(i.getKey());
                }
            });
        }
        refreshMenus();
    }

    public void refreshMenus() {
        boolean noLocation = Strings.isNullOrEmpty(EditorState.getInstance().getLocation());
        main.menu_databaseSelection.setVisible(!noLocation);
        main.menuItem_databaseFolder.setVisible(noLocation);

        Token token = Token.fromString(EditorState.getInstance().getCurrentDB());

        for (var i: map.entrySet()) {
            if (i.getKey().equals(token))
                i.getValue().setSelected(true);
        }
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
