package ch.azure.aurore.crafteditor;

import ch.azure.aurore.crafteditor.main.Token;
import ch.azure.aurore.javaxt.fxml.AppSetting;

import java.util.HashMap;
import java.util.Map;

public class EditorSettings extends AppSetting {

    private Map<String, String[]> definedClasses = new HashMap<>();

    public static EditorSettings getInstance() {
        return (EditorSettings) App.getInstance().getSettings();
    }

    @SuppressWarnings("unused") // <- JSON serialisation
    public Map<String, String[]> getDefinedClasses() {
        return definedClasses;
    }

    @SuppressWarnings("unused") // <- JSON serialisation
    public void setDefinedClasses(Map<String, String[]> definedClasses) {
        this.definedClasses = definedClasses;
        checkWritePermission();
    }

    public String[] getDefinedClassStr(Token token) {
        if (definedClasses.containsKey(token.toString()))
            return definedClasses.get(token.toString());
        return new String[0];
    }
}
