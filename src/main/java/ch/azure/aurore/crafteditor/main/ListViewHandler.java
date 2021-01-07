package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.crafteditor.data.HierarchyNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListViewHandler {
    public static final String ROOT_LABEL = "//";
    private final ObservableList<HierarchyNode> entries;
    private final MainController main;

    private final int index = 0;

    private HierarchyNode selectedNode;

    public ListViewHandler(MainController main) {
        this.main = main;

        entries = FXCollections.observableList(new ArrayList<>());
        display();

        main.listView.setCellFactory(hierarchyNodeListView -> new NodeCell(this));

        main.listView.setOnMouseClicked(mouseEvent -> {
            mouseEvent.consume();
            selectNode(main.listView.getSelectionModel().getSelectedItem());
        });
    }

    private void selectNode(HierarchyNode node) {
        selectedNode = node;

        entries.forEach(n -> n.updateRelation(node));
        Database.getInstance().setCurrentSelection(index, selectedNode.get_id());
        display();
    }

    private void display() {
        FilteredList<HierarchyNode> filteredList = new FilteredList<>(entries, HierarchyNode::isVisible);
        SortedList<HierarchyNode> sortedList = new SortedList<>(filteredList, HierarchyNode::compare);

        main.listView.setItems(sortedList);
    }

    public void createNewEntry(String classStr) {
        HierarchyNode node = new HierarchyNode();
        node.setParent(getNewParent());
        // SQLiteData item = Reflection.createInstance(classStr);
        //node.setData(item);
        entries.add(node);
        throw new IllegalStateException("not implemented");
    }

    public void createNewFolder(String name) {
        HierarchyNode node = new HierarchyNode();
        node.setParent(getNewParent());
        node.setName(name);
        entries.add(node);

        Database.getInstance().update(node);
    }

    private HierarchyNode getNewParent() {
        if (selectedNode != null){
            if (selectedNode.isFolder())
                return selectedNode;
            else{
                HierarchyNode parent = selectedNode.getParent();
                if (parent != null)
                    return parent;
                return getRoot();
            }
        }else
            return getRoot();
    }

    private HierarchyNode getRoot() {
        Optional<HierarchyNode> r = entries.stream().
                filter(hierarchyNode -> hierarchyNode.getName().equals(ROOT_LABEL)).
                findFirst();

        if (r.isEmpty()){
            HierarchyNode root = new HierarchyNode();
            root.setName(ROOT_LABEL);
            root.setIndex(index);
            entries.add(root);
            Database.getInstance().update(root);
            return root;
        }
        return r.get();
    }

    public void loadNodes() {
        List<HierarchyNode> nodes = Database.getInstance().getNodes();
        for (HierarchyNode node : nodes) {
            node.setIndex(index);
            if (!node.getName().equals(ROOT_LABEL) && node.getParent() == null){
                node.setParent(getRoot());
                Database.getInstance().update(node);
            }
        }
        entries.addAll(nodes);
    }

    public MainController getMain() {
        return main;
    }

    public void dragItem(int origin, int id) {
        System.out.println(origin + "moved to " + id);
    }
}
