package ch.azure.aurore.crafteditor.data;

import ch.azure.aurore.javaxt.sqlite.wrapper.SQLiteData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class HierarchyNode extends SQLiteData {
    private String name;
    private Map<Integer, HierarchyNode> parents = new HashMap<>();
    private SQLiteData data;
    private int index;
    private NodeRelation relation = NodeRelation.UNKNOWN;

    private SQLiteData content;

    public SQLiteData getContent() {
        return content;
    }

    public void setContent(SQLiteData content) {
        this.content = content;
        setAsModified();
    }

    public Map<Integer, HierarchyNode> getParents() {
        return parents;
    }

    public void setParents(Map<Integer, HierarchyNode> parents) {
        this.parents = parents;
        setAsModified();
    }

    public SQLiteData getData() {
        return data;
    }

    public void setData(SQLiteData item) {
        this.data = item;
        setAsModified();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setAsModified();
    }

    @Override
    public String toString() {
        return name + "//: " + relation.toString();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void updateRelation(HierarchyNode selection) {
        if (this == selection) {
            relation = NodeRelation.IDENTITY;
            return;
        }
        if (this == selection.getParent()) {
            relation = NodeRelation.PARENT;
            return;
        }
        if (this.getParent() == selection) {
            relation = NodeRelation.CHILD;
            return;
        }
        if (this.getParent() == selection.getParent() || (this.getParent() == null && selection.getParent() == null)) {
            relation = NodeRelation.SIBLING;
            return;
        }
        relation = NodeRelation.NONE;
    }

    public HierarchyNode getParent() {
        if (parents.containsKey(index)) {
            return parents.get(index);
        }
        return null;
    }

    public void setParent(HierarchyNode selectedItem) {

//        Optional<NodeReference> existing =  parents.stream().filter(new Predicate<NodeReference>() {
//            @Override
//            public boolean test(NodeReference nodeReference) {
//                return nodeReference.getIndex() != index;
//            }
//        }).findAny();
//
//        if (existing.isPresent()){
//            if (selectedItem == null)
//                parents.remove(existing.get());
//            else
//                existing.get().setNode(selectedItem);
//        }else{
//            if (selectedItem != null)
//                parents.add(new NodeReference(index, selectedItem));
//        }
//
        if (selectedItem == null)
            parents.remove(index);
        else
            parents.put(index, selectedItem);
        setAsModified();
    }

    public boolean isVisible() {
        return relation == NodeRelation.IDENTITY ||
                relation == NodeRelation.CHILD ||
                relation == NodeRelation.PARENT ||
                relation == NodeRelation.SIBLING ||
                relation == NodeRelation.UNKNOWN;
    }

    public int compare(HierarchyNode o2) {
        int val = Integer.compare(this.relation.getPriority(), o2.relation.getPriority());
        if (val != 0)
            return val;
        return name.compareToIgnoreCase(o2.name);
    }

    public boolean isFolder() {
        return content == null;
    }

}