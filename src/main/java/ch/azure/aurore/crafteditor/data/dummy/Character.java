package ch.azure.aurore.crafteditor.data.dummy;

import ch.azure.aurore.javaxt.sqlite.wrapper.annotations.DatabaseClass;

@DatabaseClass
public class Character extends GameObject {

    private int liveCount;

    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }
}