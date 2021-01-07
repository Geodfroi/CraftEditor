package ch.azure.aurore.crafteditor.data;

public enum NodeRelation {
    IDENTITY(2), PARENT(1), CHILD(4), SIBLING(2), NONE(6), UNKNOWN(5);

    private final int priority;

    NodeRelation(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
