package datastructures.graphs.model;

import datastructures.graphs.coordinates.Coord2D;

import java.util.HashSet;
import java.util.Set;

public class Origin extends Model {
    private Set<Model> children;

    public Origin(Coord2D position) {
        super(position);

        this.children = new HashSet<>();
    }

    public Set<Model> getChildren() {
        return children;
    }

    @Override
    public int getChildrenCount() {
        return children.size();
    }
}

