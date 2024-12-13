import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.agniaendie.coursalwork.GraphUtils;
import ru.agniaendie.coursalwork.Node;

import java.util.*;

public class GraphTest {
    @Test
    public void TestNormalGraph() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.getChildren().add(nodeB);
        nodeB.getChildren().add(nodeC);
        nodeB.getChildren().add(nodeD);
        nodeC.getChildren().add(nodeF);
        nodeF.getChildren().add(nodeD);
        nodeD.getChildren().add(nodeE);

        GraphUtils utils = new GraphUtils();
        List<Node> path = new ArrayList<>();
        List<Node> visited = new ArrayList<>();
        List<Node> minimalCycle = new ArrayList<>();


        utils.DFS(nodeA, visited, new ArrayList<>(), path, minimalCycle);
        Assertions.assertEquals(6, visited.size());
        displayGraph(visited);

    }

    @Test
    @DisplayName("Finding Minimal Cycle in Non-Oriented Graph")
    public void TestFindMinimalCycleInNonOrientedGraph() {

        Node nodeA = new Node("A");

        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        Node nodeG = new Node("G");
        Node nodeH = new Node("H");
        Node nodeJ = new Node("J");
        Node nodeK = new Node("K");
        Node nodeL = new Node("L");
        Node nodeM = new Node("M");
        Node nodeN = new Node("N");
        Node nodeO = new Node("O");
        Node nodeP = new Node("P");
        Node nodeR = new Node("R");
        Node nodeS = new Node("S");
        Node nodeT = new Node("T");


        //    A
        //   / \
        //  B   T
        // / \ / \
        //C   D   S
        //|   |   |
        //E---H---R
        //|   |   |
        //F   G   P
        //|   |   |
        //M---N---O
        //|   |
        //L---K
        //|
        //J

        // Создание связей между узлами
        nodeA.getChildren().add(nodeB);
        nodeA.getChildren().add(nodeT);

        nodeT.getChildren().add(nodeA);
        nodeT.getChildren().add(nodeD);
        nodeT.getChildren().add(nodeS);

        nodeB.getChildren().add(nodeA);
        nodeB.getChildren().add(nodeD);
        nodeB.getChildren().add(nodeC);

        nodeC.getChildren().add(nodeE);
        nodeC.getChildren().add(nodeB);

        nodeE.getChildren().add(nodeC);
        nodeE.getChildren().add(nodeH);
        nodeE.getChildren().add(nodeF);

        nodeH.getChildren().add(nodeD);
        nodeH.getChildren().add(nodeE);
        nodeH.getChildren().add(nodeR);
        nodeH.getChildren().add(nodeG);

        nodeD.getChildren().add(nodeH);
        nodeD.getChildren().add(nodeB);
        nodeD.getChildren().add(nodeT);

        nodeR.getChildren().add(nodeS);
        nodeR.getChildren().add(nodeH);
        nodeR.getChildren().add(nodeP);

        nodeS.getChildren().add(nodeT);
        nodeS.getChildren().add(nodeR);

        nodeF.getChildren().add(nodeE);
        nodeF.getChildren().add(nodeM);

        nodeG.getChildren().add(nodeH);
        nodeG.getChildren().add(nodeN);

        nodeP.getChildren().add(nodeR);
        nodeP.getChildren().add(nodeO);

        nodeM.getChildren().add(nodeN);
        nodeM.getChildren().add(nodeF);
        nodeM.getChildren().add(nodeL);

        nodeN.getChildren().add(nodeG);
        nodeN.getChildren().add(nodeM);
        nodeN.getChildren().add(nodeK);
        nodeN.getChildren().add(nodeO);

        nodeO.getChildren().add(nodeP);
        nodeO.getChildren().add(nodeN);

        nodeL.getChildren().add(nodeM);
        nodeL.getChildren().add(nodeK);
        nodeL.getChildren().add(nodeJ);

        nodeK.getChildren().add(nodeN);
        nodeK.getChildren().add(nodeL);

        nodeL.getChildren().add(nodeK);
        nodeL.getChildren().add(nodeJ);


        GraphUtils utils = new GraphUtils();
        List<Node> path = new ArrayList<>();
        List<Node> visited = new ArrayList<>();
        List<Node> minimalCycle = new ArrayList<>();

        utils.findMinimalCycle(nodeA, path, visited, minimalCycle);

        Assertions.assertEquals(2, minimalCycle.size());

        System.out.println("Longest Cycle Length: " + minimalCycle.size());
        displayGraph(minimalCycle);


    }

    @Test
    @DisplayName("Find minimal cycle in oriented graph")
    public void TestFindMinimalOrientedGraphCycle(){
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        nodeA.getChildren().add(nodeB);
        nodeB.getChildren().add(nodeC);
        nodeC.getChildren().add(nodeA);
        // A -> B -> C -> A

        GraphUtils utils = new GraphUtils();

        List<Node> path = new ArrayList<>();
        List<Node> visited = new ArrayList<>();
        List<Node> minimalCycle = new ArrayList<>();

        utils.findMinimalCycle(nodeA, path, visited, minimalCycle);

        Assertions.assertEquals(3, minimalCycle.size());
        displayGraph(minimalCycle);
    }


    // printNode - staff method to display graph
    private void displayGraph(Collection<Node> path) {
        for (Node node : path) {
            System.out.println(node.getValue());
        }
    }

}
