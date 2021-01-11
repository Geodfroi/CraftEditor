package ch.azure.aurore.crafteditor;

import ch.azure.aurore.crafteditor.main.Token;
import ch.azure.aurore.javaxt.fxml.AppState;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @SuppressWarnings("unused")
    public String getCurrentDB() {
        return currentDB;
    }

    @JsonIgnore
    public Token getToken()  {
        return Token.fromString(currentDB);
    }
    public void setCurrentDB(String token) {
        this.currentDB = token;
    }
}
