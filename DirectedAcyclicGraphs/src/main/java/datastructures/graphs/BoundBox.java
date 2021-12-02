package datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

public class BoundBox {
    Edges edges;
    List<Model> itemBox;

    public BoundBox(Edges edges) {
        this.edges = edges;
        this.itemBox = new ArrayList<Model>();
    }

    public void addItem(Model item) {
        itemBox.add(item);
    }

    public void print() {
        double length = edges.getLength();
        double height = edges.getHeight();

        for (int i = 0; i < edges.getLength(); i++){
            for (int j = 0; j < edges.getHeight(); j++) {
                // Add drawing library
            }
        }
    }
}
