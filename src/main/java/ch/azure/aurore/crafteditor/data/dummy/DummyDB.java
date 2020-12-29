package ch.azure.aurore.crafteditor.data.dummy;

import ch.azure.aurore.javaxt.sqlite.wrapper.SQLite;

public class DummyDB {

    public static void load(SQLite sqLite) {
        Enemy ghost = new Enemy();
        ghost.setXp(1200);
        ghost.setHealth(600);

        Enemy skeleton = new Enemy();
        skeleton.setXp(800);
        skeleton.setHealth(250);

        Character jordan = new Character();
        jordan.setHealth(800);
        jordan.setLiveCount(3);

        sqLite.updateItems(ghost, skeleton, jordan);
    }
}
