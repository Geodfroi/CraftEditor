package ch.azure.aurore.crafteditor;

import ch.azure.aurore.javaxt.fxml.AppState;

public class EditorState extends AppState {
    private String location;
    private String currentDB;

    public static EditorState getInstance() {
        return (EditorState) App.getInstance().getState();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentDB() {
        return currentDB;
    }

    public void setCurrentDB(String token) {
        this.currentDB = token;
    }
}
