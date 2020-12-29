package ch.azure.aurore.crafteditor.data;

public abstract class GameDataItem {
    private int _id;
    private boolean _modified;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean is_modified() {
        return _modified;
    }

    public void set_modified(boolean _modified) {
        this._modified = _modified;
    }
}
