package ru.agniaendie.coursalwork.graph;

public class NodeRelation {
    private final Node source;
    private final Node destination;
    int cost;
    private boolean visited;

    public NodeRelation(Node source, Node destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public Node getSource() {
        return source;
    }

    public int getCost(){
        return cost;
    }

    public Node getDestination() {
        return destination;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
