package datastructures.graphs;

import java.util.HashSet;
import java.util.Set;

public class Origin extends Model {
    private Set<Model> children;

    public Origin(Coord2D position) {
        super(position);

        this.children = new HashSet<>();
    }

    private void printChildren() {
        while (children.iterator().hasNext()) {
            children.iterator().next().print();
        }
    }
}

