package ch.azure.aurore.crafteditor.data.dummy;

import ch.azure.aurore.crafteditor.data.GameData;

public abstract class GameObject extends GameData {
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
