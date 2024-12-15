package ru.agniaendie.coursalwork.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private final List<NodeRelation> children = new ArrayList<>();
    private final String value;

    public Node(String value) {
        this.value = value;
    }

    public List<NodeRelation> getChildren() {
        return children;
    }

    public void addChild(NodeRelation[] child) {
        children.addAll(Arrays.asList(child));
    }

    public String getValue() {
        return value;
    }
}
