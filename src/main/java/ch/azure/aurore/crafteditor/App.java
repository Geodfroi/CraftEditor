package ch.azure.aurore.crafteditor;

import ch.azure.aurore.crafteditor.main.Database;
import ch.azure.aurore.javaxt.fxml.AppSetting;
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
        Database.getInstance().close();
    }

    @Override
    protected Class<? extends AppState> getStateType() {
        return EditorState.class;
    }

    @Override
    protected Class<? extends AppSetting> getSettingType() {
        return EditorSettings.class;
    }
}
