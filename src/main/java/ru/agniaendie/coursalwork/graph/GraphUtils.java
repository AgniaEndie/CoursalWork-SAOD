package ru.agniaendie.coursalwork.graph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class GraphUtils {

    public void findMinimalCycle(Node node, List<Node> path, List<Node> minimalCycle, int minimalCycleCost, int minimalCycleLength) {
        if (path.contains(node)) {
            int cycleStartIndex = path.indexOf(node);
                int currentCost = getCycleCost(path, cycleStartIndex);
                if (currentCost < minimalCycleCost) {
                    minimalCycleCost = currentCost;
                    minimalCycle.clear();
                    minimalCycle.addAll(path.subList(cycleStartIndex, path.size()));
                    minimalCycle.add(minimalCycle.get(0));
                } else if (currentCost == minimalCycleCost && path.size() < minimalCycleLength) {
                    minimalCycleLength = path.size();
                    minimalCycle.clear();
                    minimalCycle.addAll(path.subList(cycleStartIndex, path.size()));
                    minimalCycle.add(minimalCycle.get(0));
                }

            return;
        }

        path.add(node);

        List<NodeRelation> children = node.getChildren();
        for (NodeRelation child : children) {
            if (!child.isVisited()) {
                child.setVisited(true);
                findMinimalCycle(child.getDestination(), path, minimalCycle, minimalCycleCost, minimalCycleLength);
                child.setVisited(false);
            }
        }
        path.remove(path.size() - 1);
    }

    private int getCycleCost(List<Node> path, int cycleStartIndex) {
        int cost = 0;
        for (int i = cycleStartIndex; i < path.size(); i++) {
            Node currentNode = path.get(i);
            Node nextNode = path.get((i + 1) % path.size());
            for (NodeRelation relation : currentNode.getChildren()) {
                if (relation.getDestination().equals(nextNode)) {
                    cost += relation.getCost();
                    break;
                }
            }
        }
        return cost;
    }

    public void drawAdjacencyMatrix(Node startNode) {
        Set<Node> allNodes = new HashSet<>();
        gatherNodes(startNode, allNodes);

        List<Node> nodeList = new ArrayList<>(allNodes);
        nodeList.sort(Comparator.comparing(Node::getValue));
        int size = nodeList.size();
        int[][] adjacencyMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }

        for (Node node : nodeList) {
            int rowIndex = nodeList.indexOf(node);
            for (NodeRelation relation : node.getChildren()) {
                Node destination = relation.getDestination();
                int colIndex = nodeList.indexOf(destination);
                adjacencyMatrix[rowIndex][colIndex] = relation.cost; // Set the cost
            }
        }

        printMatrix(adjacencyMatrix, nodeList);
    }

    private void gatherNodes(Node node, Set<Node> allNodes) {
        if (allNodes.contains(node)) {
            return;
        }
        allNodes.add(node);
        for (NodeRelation relation : node.getChildren()) {
            gatherNodes(relation.getDestination(), allNodes);
        }
    }

    private void printMatrix(int[][] matrix, List<Node> nodes) {
        System.out.print("   ");
        for (Node node : nodes) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(nodes.get(i).getValue() + " | ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void breadthFirstSearch(Node node, Queue<Node> queue, boolean draw) {
        queue.add(node);

        Set<Node> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            if (draw) {
                System.out.println(currentNode.getValue());
            }

            for (NodeRelation child : currentNode.getChildren()) {
                Node childNode = child.getDestination();
                if (!visited.contains(childNode)) {
                    queue.add(childNode);
                }
            }
        }
    }

    public void dijkstra(List<Node> baseNodes, BufferedWriter writer) {
        for (Node nodeLevelA : baseNodes) {
            for (Node nodeLevelB : baseNodes) {
                dijkstraForOne(nodeLevelA, nodeLevelB, writer, baseNodes);
            }
        }
    }

    private void dijkstraForOne(Node startNode, Node endNode, BufferedWriter writer, List<Node> baseNodes) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Node> visited = new HashSet<>();

        for (Node node : baseNodes) {
            distances.put(node, Integer.MAX_VALUE);
            previousNodes.put(node, null);
        }
        distances.put(startNode, 0);
        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            if (currentNode.equals(endNode)) {
                break;
            }

            for (NodeRelation relation : currentNode.getChildren()) {
                Node neighbor = relation.getDestination();
                int currentDistance = distances.getOrDefault(currentNode, Integer.MAX_VALUE);
                int newDist = currentDistance + relation.getCost();

                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    priorityQueue.add(neighbor);
                }
            }
        }

        try {
            if (distances.get(endNode) == Integer.MAX_VALUE) {
                writer.write("(" + startNode.getValue() + "," + endNode.getValue() + ") -> Путь не найден");
                writer.newLine();
            } else {
                List<Node> path = new ArrayList<>();
                for (Node at = endNode; at != null; at = previousNodes.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);

                StringBuilder route = new StringBuilder();
                for (int i = 0; i < path.size(); i++) {
                    route.append(path.get(i).getValue());
                    if (i < path.size() - 1) {
                        route.append(" -> ");
                    }
                }
                writer.write("(" + startNode.getValue() + "," + endNode.getValue() + ") -> " + distances.get(endNode) + "; Путь: " + route.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}