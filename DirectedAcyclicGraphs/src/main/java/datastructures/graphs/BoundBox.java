package datastructures.graphs;

import java.util.ArrayList;

public class BoundBox {
    private final Edges edges;
    private final OrientedGraph itemGraph;
    private int itemCount;

    public BoundBox(Edges edges) {
        this.edges = edges;
        this.itemGraph = new OrientedGraph();
        this.itemCount = 0;
    }

    public void addItem(Model item) {
        itemGraph.insert(item);
        itemCount++;
    }

    public void print() {
        itemGraph.printAll();
    }
}
