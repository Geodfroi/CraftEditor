package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.crafteditor.data.GameDataInfo;
import ch.azure.aurore.crafteditor.data.dummy.DummyDB;
import ch.azure.aurore.javaxt.sqlite.wrapper.SQLite;

import java.nio.file.Path;

public class Database {

    private SQLite sqLite;
    private GameDataInfo info = new GameDataInfo();

    private Database() {
    }

    public static Database load(Token token) {
        Database d = new Database();
        d.ini(token);
        return d;
    }

    private static String getFileName(Token token) {
        return switch (token) {
            case EDEN -> "edenData.sqlite";
            case CRAFT -> "craftData.sqlite";
            case DUMMY -> "dummyData.sqlite";
            default -> throw new IllegalStateException("Unexpected value: " + token);
        };
    }

    private void ini(Token token) {
        Path p = Path.of(EditorState.getInstance().getLocation(), getFileName(token));
        sqLite = SQLite.connect(p);
        if (sqLite == null)
            throw new IllegalStateException("Cannot access [token] database");

        GameDataInfo info = sqLite.queryItem(GameDataInfo.class);
        if (info != null)
            this.info = info;
        else{
            if (token == Token.DUMMY)
                DummyDB.load(sqLite);
        }
    }

    public void close() {
        sqLite.close();
    }
}
