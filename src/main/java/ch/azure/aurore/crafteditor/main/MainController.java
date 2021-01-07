package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.crafteditor.data.HierarchyNode;
import ch.azure.aurore.javaxt.fxml.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

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
    public MenuItem menuItem_createFolder;
    @FXML
    public MenuItem menuItem_close;
    @FXML
    public MenuItem menuItem_databaseFolder;
    @FXML
    public Menu menu_createItem;
    @FXML
    public Menu menu_databaseSelection;
    @FXML
    public Menu menu_edit;

    @FXML
    public ListView<HierarchyNode> listView;
    @FXML
    public BorderPane root;

    private MenuHandler menuHandler;
    private ListViewHandler listViewHandler;

    @Override
    protected void resume() {
    }

    @Override
    protected void start() {
        Token token = Token.fromString(EditorState.getInstance().getCurrentDB());
        if (token != Token.NONE){
            Database.getInstance().load(token);
            listViewHandler.loadNodes();
        }
    }

    @Override
    public void quit() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuHandler = new MenuHandler(this);
        listViewHandler = new ListViewHandler(this);
    }

    public ListViewHandler getListViewHandler() {
        return listViewHandler;
    }
}