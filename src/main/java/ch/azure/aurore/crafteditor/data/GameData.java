package ch.azure.aurore.crafteditor.data;

import ch.azure.aurore.javaxt.sqlite.wrapper.SQLiteData;

public abstract class GameData extends SQLiteData {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
