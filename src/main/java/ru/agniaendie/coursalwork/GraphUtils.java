package ru.agniaendie.coursalwork;

import java.util.*;

public class GraphUtils {
    private int minimalCycleLength = Integer.MAX_VALUE;

    public void findMinimalCycle(Node node, List<Node> visited, List<Node> path, List<Node> minimalCycle) {
        DFS(node, visited, new ArrayList<>(), path, minimalCycle);
    }

    public void DFS(Node node, List<Node> visited, List<Node> inRec, List<Node> path, List<Node> minimalCycle) {
        if (inRec.contains(node)) {
            int cycleStartIndex = path.indexOf(node);
            int cycleLength = path.size() - cycleStartIndex;

            if (cycleLength < minimalCycleLength) {
                minimalCycleLength = cycleLength;
                minimalCycle.clear();
                minimalCycle.addAll(path.subList(cycleStartIndex, path.size()));
            }
            return;
        }

        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        inRec.add(node);
        path.add(node);

        for (Node child : node.getChildren()) {
            DFS(child, visited, inRec, path, minimalCycle);
        }

        inRec.remove(node);
        path.remove(path.size() - 1);
    }
}