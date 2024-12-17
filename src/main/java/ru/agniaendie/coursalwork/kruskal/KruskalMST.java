package ru.agniaendie.coursalwork.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KruskalMST {
    public List<Edge> kruskal(List<Edge> edges, int numVertices) {
        edges.sort(Comparator.comparingInt(e -> e.weight));
        DisjointSet ds = new DisjointSet(numVertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;

            if (ds.find(u) != ds.find(v)) {
                ds.union(u, v);
                mst.add(edge);
            }
        }

        return mst;
    }
}