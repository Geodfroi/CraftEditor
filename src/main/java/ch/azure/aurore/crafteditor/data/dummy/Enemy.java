package ch.azure.aurore.crafteditor.data.dummy;

import ch.azure.aurore.javaxt.sqlite.wrapper.annotations.DatabaseClass;

@DatabaseClass
public class Enemy extends GameObject {

    private boolean _modified;

    private int xp;

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
