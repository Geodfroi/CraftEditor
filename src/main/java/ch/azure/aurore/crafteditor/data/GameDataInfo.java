package ch.azure.aurore.crafteditor.data;

import ch.azure.aurore.javaxt.sqlite.wrapper.SQLiteData;

import java.util.HashMap;
import java.util.Map;

public class GameDataInfo extends SQLiteData {

    private Map<Integer, Integer> currentEntry = new HashMap<>();

    public Map<Integer, Integer> getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(Map<Integer, Integer> currentEntry) {
        this.currentEntry = currentEntry;
    }
}
