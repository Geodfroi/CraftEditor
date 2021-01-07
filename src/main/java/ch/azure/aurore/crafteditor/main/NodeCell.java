package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.data.HierarchyNode;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * https://stackoverflow.com/questions/25390888/dragging-and-dropping-list-view-items-between-different-javafx-windows
 * https://gist.github.com/jewelsea/7821196
 */
public final class NodeCell extends ListCell<HierarchyNode> {

    public NodeCell(ListViewHandler listViewHandler) {

        this.setOnDragDetected(e -> {
            HierarchyNode item = getItem();
            if (item == null)
                return;

            Dragboard dragBoard = listViewHandler.getMain().listView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(Integer.valueOf(item.get_id()).toString());
            dragBoard.setContent( content );
            e.consume();
        });

        this.setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.MOVE);
            e.consume();
        });

        this.setOnDragDropped(e -> {
            Dragboard c = e.getDragboard();
            int origin = Integer.parseInt(c.getString());
            listViewHandler.dragItem(origin, getItem().get_id());
            e.consume();
        });
    }

    @Override
    protected void updateItem(HierarchyNode item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
            setText(null);
        else {
            setText(item.toString());
        }
    }
}