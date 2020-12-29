package ch.azure.aurore.crafteditor.data;

import ch.azure.aurore.javaxt.sqlite.wrapper.annotations.DatabaseClass;

@DatabaseClass
public class GameDataInfo {
    private boolean _modified;
    private int _id;

    public boolean is_modified() {
        return _modified;
    }

    public void set_modified(boolean _modified) {
        this._modified = _modified;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
