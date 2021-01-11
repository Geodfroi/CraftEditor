package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.EditorSettings;
import ch.azure.aurore.crafteditor.EditorState;
import ch.azure.aurore.crafteditor.data.HierarchyNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListViewHandler {
    public static final String ROOT_LABEL = "//";
    private final ObservableList<HierarchyNode> nodes;
    private final MainController main;

    private final int index = 0;

    private HierarchyNode selectedNode;

    public ListViewHandler(MainController main) {
        this.main = main;

        nodes = FXCollections.observableList(new ArrayList<>());
        display();

        main.listView.setCellFactory(hierarchyNodeListView -> new NodeCell(this));

        main.listView.setOnMouseClicked(mouseEvent -> {
            mouseEvent.consume();
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (main.listView.getSelectionModel().getSelectedItem() != null)
                    selectNode(main.listView.getSelectionModel().getSelectedItem());
            }
        });

        //region context menu
        ContextMenu contextMenu = new ContextMenu();
        main.listView.setContextMenu(contextMenu);

        MenuItem addNewMenu = new MenuItem("Add Child Folder");
        addNewMenu.setOnAction(e -> {
            main.getMenuHandler().createNewFolder();
            e.consume();
        });
        contextMenu.getItems().add(addNewMenu);

        MenuItem addNewItem = new MenuItem("Add New Item");
        addNewItem.setOnAction(e -> {
            Token token = EditorState.getInstance().getToken();
            String[] classes = EditorSettings.getInstance().getDefinedClassStr(token);
            main.getMenuHandler().createNewItem(classes, null);
            e.consume();
        });
        contextMenu.getItems().add(addNewItem);

        MenuItem menuItem_deleteNode = new MenuItem("Delete Item");
        menuItem_deleteNode.setOnAction(e -> deleteItem(selectedNode));
        contextMenu.getItems().add(menuItem_deleteNode);

        contextMenu.setOnShowing(e -> {
            Token token = EditorState.getInstance().getToken();
            addNewMenu.setDisable(selectedNode == null || token == Token.NONE);
            addNewItem.setDisable(selectedNode == null);
            menuItem_deleteNode.setDisable(selectedNode == null || hasChildren(selectedNode));
        });
        //endregion
    }

    private void deleteItem(HierarchyNode node) {
        if (node == selectedNode){
            throw new IllegalStateException("Not Implemented");
        }
        throw new IllegalStateException("Not Implemented");
    }

    public MainController getMain() {
        return main;
    }

    public void createNewEntry(String clazz, String name) {
        HierarchyNode node = new HierarchyNode();
        node.setParent(getNewParent());
        // SQLiteData item = Reflection.createInstance(classStr);
        //node.setData(item);
        nodes.add(node);
        throw new IllegalStateException("not implemented");
    }

    public void createNewFolder(String name) {
        HierarchyNode node = new HierarchyNode();
        node.setParent(getNewParent());
        node.setName(name);
        nodes.add(node);

        Database.getInstance().update(node);
    }

    private void display() {
        FilteredList<HierarchyNode> filteredList = new FilteredList<>(nodes, HierarchyNode::isVisible);
        SortedList<HierarchyNode> sortedList = new SortedList<>(filteredList, HierarchyNode::compare);

        main.listView.setItems(sortedList);
    }

    public void dragItem(int origin, int id) {
        System.out.println(origin + "moved to " + id);
    }

    private HierarchyNode getNewParent() {
        if (selectedNode != null) {
            if (selectedNode.isFolder())
                return selectedNode;
            else {
                HierarchyNode parent = selectedNode.getParent();
                if (parent != null)
                    return parent;
                return getRoot();
            }
        } else
            return getRoot();
    }

    private HierarchyNode getRoot() {
        Optional<HierarchyNode> r = nodes.stream().
                filter(hierarchyNode -> hierarchyNode.getName().equals(ROOT_LABEL)).
                findFirst();

        if (r.isEmpty()) {
            HierarchyNode root = new HierarchyNode();
            root.setName(ROOT_LABEL);
            root.setIndex(index);
            nodes.add(root);
            Database.getInstance().update(root);
            return root;
        }
        return r.get();
    }

    private boolean hasChildren(HierarchyNode selectedNode) {
        return nodes.stream().anyMatch(node -> node.getParent() == selectedNode);
    }

    public void loadNodes() {
        List<HierarchyNode> nodes = Database.getInstance().getNodes();
        for (HierarchyNode node : nodes) {
            node.setIndex(index);
            if (!node.getName().equals(ROOT_LABEL) && node.getParent() == null) {
                node.setParent(getRoot());
                Database.getInstance().update(node);
            }
        }
        this.nodes.addAll(nodes);
    }

    private void selectNode(HierarchyNode node) {
        selectedNode = node;
        nodes.forEach(n -> n.updateRelation(node));
        Database.getInstance().setCurrentSelection(index, selectedNode.get_id());
        display();
    }
}
