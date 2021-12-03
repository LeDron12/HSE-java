package datastructures.graphs.model;

import datastructures.graphs.BoundBox;
import datastructures.graphs.coordinates.Coord2D;

public class Model implements Comparable {
    private Coord2D position;
    private BoundBox bounds;

    public Model(Coord2D position) {
        this.position = position;
        this.bounds = new BoundBox(position);
    }

    public Coord2D getPosition() {
        return this.position;
    }

    public void setPosition(Coord2D position) {
        this.position = position;
    }


    @Override
    public int compareTo(Object obj) {
        Model second = (Model) obj;

        return position.compareTo(second.position);
    }


    public BoundBox getBounds() {
        return bounds;
    }


    public int getChildrenCount() {
        return 0;
    }
}
