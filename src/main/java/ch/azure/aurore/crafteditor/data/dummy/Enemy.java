package ch.azure.aurore.crafteditor.data.dummy;

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