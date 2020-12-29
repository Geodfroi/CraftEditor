package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.javaxt.fxml.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {

    @FXML
    public CheckMenuItem checkMenuItem_craft;
    @FXML
    public CheckMenuItem checkMenuItem_dummy;
    @FXML
    public CheckMenuItem checkMenuItem_eden;
    @FXML
    public Menu menu_databaseSelection;
    @FXML
    public MenuItem menuItem_databaseFolder;
    @FXML
    public MenuItem menuItem_close;

    private MenuHandler menuHandler;
    private Database db;

    @Override
    protected void resume() {
    }

    @Override
    protected void start() {
        Token token= Token.fromString(EditorState.getInstance().getCurrentDB());
        if (token != Token.NONE)
            loadDB(token);
    }

    @Override
    public void quit() {
        if (db != null)
            db.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuHandler = new MenuHandler(this);
    }

    public void loadDB(Token token) {
        db = Database.load(token);
        EditorState.getInstance().setCurrentDB(token.toString());
    }
}
