package ch.azure.aurore.crafteditor;

import ch.azure.aurore.javaxt.fxml.AppState;
import ch.azure.aurore.javaxt.fxml.FXApplication;
import javafx.fxml.FXMLLoader;

public class App extends FXApplication {
    private static final String MAIN = "main";

    @Override
    protected FXMLLoader getLoader(String s) {
        return new FXMLLoader(App.class.getResource("/ch/azure/aurore/crafteditor/fxml/Main.fxml"));
    }

    @Override
    protected void start() {
        switchScene(MAIN);
    }

    @Override
    protected void quit() {
    }

    @Override
    protected Class<? extends AppState> getStateType() {
        return EditorState.class;
    }
}
