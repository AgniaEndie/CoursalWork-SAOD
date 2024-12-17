package ru.agniaendie.coursalwork;

import ru.agniaendie.coursalwork.graph.GraphUtils;
import ru.agniaendie.coursalwork.graph.Node;
import ru.agniaendie.coursalwork.graph.NodeRelation;
import ru.agniaendie.coursalwork.kruskal.Edge;
import ru.agniaendie.coursalwork.kruskal.KruskalMST;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        GraphUtils graphUtils = new GraphUtils();
        ArrayList<Node> nodes = buildDefaultNode();
        graphUtils.drawAdjacencyMatrix(nodes.get(0));
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)); BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Main.drawCommandMessage(writer);
            while (true) {
                try {
                    String command = reader.readLine();
                    switch (command) {
                        case "1":
                            List<Node> path = new ArrayList<>();
                            List<Node> minimalCycle = new ArrayList<>();
                            graphUtils.findMinimalCycle(nodes.get(0), path, minimalCycle, Integer.MAX_VALUE, Integer.MAX_VALUE);
                            drawCollectionResult(writer, minimalCycle);

                            writer.newLine();
                            writer.flush();
                            Main.drawCommandMessage(writer);
                            break;
                        case "2":
                            //rw -> fix dead end
                            writer.write("Обход графа в ширину");
                            writer.newLine();
                            writer.flush();
                            Queue<Node> queue = new LinkedList<>();
                            Node currentNode = nodes.get(2);
                            if (currentNode.getChildren().isEmpty()) {
                                writer.write("Тупиковый элемент");
                                writer.newLine();
                                currentNode = nodes.get(0);
                            }

                            graphUtils.breadthFirstSearch(currentNode, queue, true);
                            writer.newLine();
                            writer.flush();
                            Main.drawCommandMessage(writer);
                            break;
                        case "3":
                            graphUtils.dijkstra(nodes, writer);
                            writer.newLine();
                            writer.flush();
                            Main.drawCommandMessage(writer);
                            break;
                        case "4":
                            List<Edge> edges = new ArrayList<>();
                            edges.add(new Edge(1, 2, 10));
                            edges.add(new Edge(1, 3, 1));
                            edges.add(new Edge(1, 4, 5));

                            edges.add(new Edge(3, 1, 1));
                            edges.add(new Edge(3, 4, 3));
                            edges.add(new Edge(3, 5, 2));
                            edges.add(new Edge(3, 6, 4));

                            edges.add(new Edge(5, 3, 2));
                            edges.add(new Edge(5, 6, 1));

                            edges.add(new Edge(6, 5, 1));
                            edges.add(new Edge(6, 4, 15));

                            edges.add(new Edge(4, 3, 3));
                            edges.add(new Edge(4, 1, 5));
                            edges.add(new Edge(4, 6, 1));

                            KruskalMST kruskal = new KruskalMST();
                            List<Edge> mst = kruskal.kruskal(edges, edges.size());

                            writer.write("Рёбра минимального остовного дерева:");
                            writer.newLine();
                            for (Edge edge : mst) {
                                writer.write(edge.getSource() + " -- " + edge.getDestination() + " == " + edge.getWeight());
                                writer.newLine();
                            }

                            writer.newLine();
                            writer.flush();
                            Main.drawCommandMessage(writer);
                            break;
                        case "5":
                            writer.write("Завершение работы");
                            writer.flush();
                            writer.close();
                            reader.close();
                            System.exit(0);
                        default:
                            writer.write("Неизвестная команда " + command);
                            writer.newLine();
                            writer.write("Вот список поддерживаемых команд: ");
                            writer.flush();
                            Main.drawCommandMessage(writer);
                            break;
                    }
                } catch (IOException e) {
                    writer.write("Error reading command: " + e.getMessage());
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Node> buildDefaultNode() {
        Node nodeOne = new Node("1");
        Node nodeTwo = new Node("2");
        Node nodeThree = new Node("3");
        Node nodeFour = new Node("4");
        Node nodeFive = new Node("5");
        Node nodeSix = new Node("6");

        NodeRelation OneToTwo = new NodeRelation(nodeOne, nodeTwo, 10);
        NodeRelation OneToThree = new NodeRelation(nodeOne, nodeThree, 1);
        NodeRelation OneToFour = new NodeRelation(nodeOne, nodeFour, 5);

        NodeRelation ThreeToOne = new NodeRelation(nodeThree, nodeOne, 1);
        NodeRelation ThreeToFour = new NodeRelation(nodeThree, nodeFour, 3);
        NodeRelation ThreeToFive = new NodeRelation(nodeThree, nodeFive, 2);
        NodeRelation ThreeToSix = new NodeRelation(nodeThree, nodeSix, 4);

        NodeRelation FourToThree = new NodeRelation(nodeFour, nodeThree, 3);
        NodeRelation FourToOne = new NodeRelation(nodeFour, nodeOne, 5);
        NodeRelation FourToSix = new NodeRelation(nodeFour, nodeSix, 1);

        NodeRelation FiveToThree = new NodeRelation(nodeFive, nodeThree, 2);
        NodeRelation FiveToSix = new NodeRelation(nodeFive, nodeSix, 1);

        NodeRelation SixToThree = new NodeRelation(nodeSix, nodeThree, 4);
        NodeRelation SixToFour = new NodeRelation(nodeSix, nodeFour, 1);
        NodeRelation SixToFive = new NodeRelation(nodeSix, nodeFive, 1);

        nodeOne.addChild(new NodeRelation[]{OneToTwo, OneToThree, OneToFour});

        nodeThree.addChild(new NodeRelation[]{ThreeToOne, ThreeToFour, ThreeToFive, ThreeToSix});

        nodeFour.addChild(new NodeRelation[]{FourToOne, FourToSix, FourToThree});

        nodeFive.addChild(new NodeRelation[]{FiveToThree, FiveToSix});

        nodeSix.addChild(new NodeRelation[]{SixToThree, SixToFour, SixToFive});

        ArrayList<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, nodeOne, nodeTwo, nodeThree, nodeFour, nodeFive, nodeSix);
        return nodeList;
    }

    private static void drawCommandMessage(BufferedWriter writer) throws IOException {
        writer.write("1 - Определить самый короткий цикл в графе.");
        writer.newLine();
        writer.write("2 - Выполнить обход графа в ширину");
        writer.newLine();
        writer.write("3 - Определить кратчайший путь из заданной вершины во все остальные");
        writer.newLine();
        writer.write("4 - Построить минимальное остовное дерево с помощью алгоритма Крускала.");
        writer.newLine();
        writer.newLine();
        writer.newLine();
        writer.flush();
    }

    private static void drawCollectionResult(BufferedWriter writer, List<Node> result) throws IOException {
        for (int i = 0; i < result.size(); i++) {
            Node node = result.get(i);
            writer.write(node.getValue());
            if (i < result.size() - 1) {
                writer.write(" -> ");
            }
        }
        writer.flush();
    }
}
