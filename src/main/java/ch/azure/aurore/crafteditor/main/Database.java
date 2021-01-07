package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.crafteditor.data.GameDataInfo;
import ch.azure.aurore.crafteditor.data.dummy.DummyDB;
import ch.azure.aurore.crafteditor.data.HierarchyNode;
import ch.azure.aurore.javaxt.sqlite.wrapper.SQLite;

import java.nio.file.Path;
import java.util.List;

public class Database {

    private static final Database instance = new Database();
    private SQLite sqLite;
    private GameDataInfo info;

    private Database() {
    }

    public static Database getInstance() {
        return instance;
    }

    private static String getFileName(Token token) {
        return switch (token) {
            case EDEN -> "edenData.sqlite";
            case CRAFT -> "craftData.sqlite";
            case DUMMY -> "dummyData.sqlite";
            default -> throw new IllegalStateException("Unexpected value: " + token);
        };
    }

    private static GameDataInfo loadInfo(SQLite sqlite, Token token) {
        GameDataInfo info = sqlite.queryItem(GameDataInfo.class);
        if (info == null) {
            info = new GameDataInfo();
            sqlite.updateItem(info);
            if (token == Token.DUMMY)
                DummyDB.load(sqlite);
        }
        return info;
    }

    public void load(Token token) {
        if (sqLite != null)
            close();

        Path p = Path.of(EditorState.getInstance().getLocation(), getFileName(token));
        sqLite = SQLite.connect(p);
        if (sqLite == null)
            throw new IllegalStateException("Cannot access [token] database");

        info = loadInfo(sqLite, token);
    }

    public void close() {
        if (sqLite != null)
            sqLite.close();
    }

    private void check() {
        if (sqLite == null)
            throw new IllegalStateException("Invalid operation: database hasn't been initialised");
    }

    public List<HierarchyNode> getNodes() {
        return sqLite.queryItems(HierarchyNode.class);
    }

    public void setCurrentSelection(int index, int id) {
        info.getCurrentEntry().put(index, id);
        info.setAsModified();
        sqLite.updateItem(info);
    }

    public void update(HierarchyNode node) {
        check();
        sqLite.updateItem(node);
    }
}
