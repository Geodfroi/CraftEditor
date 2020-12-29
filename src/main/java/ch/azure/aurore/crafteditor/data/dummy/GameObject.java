package ch.azure.aurore.crafteditor.data.dummy;

import ch.azure.aurore.crafteditor.data.GameDataItem;

public abstract class GameObject extends GameDataItem {
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
