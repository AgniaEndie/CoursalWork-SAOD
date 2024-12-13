package ru.agniaendie.coursalwork;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Node> children = new ArrayList<>();
    private String value = "";

    public Node(String value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getValue() {
        return value;
    }
}
