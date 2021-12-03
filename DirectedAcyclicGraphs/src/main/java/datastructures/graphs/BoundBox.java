package datastructures.graphs;

import datastructures.graphs.coordinates.Coord2D;
import datastructures.graphs.coordinates.Edges;

public class BoundBox {
    private final Edges edges;


    public BoundBox(Coord2D coordinates) {
        this.edges = new Edges(coordinates, coordinates);
    }


    public void setNewEdges(Edges newEdges) {
        if (newEdges == null) {
            throw new IllegalArgumentException("Null arguments are restricted");
        }

        Coord2D newLeftHighCoordinates = new Coord2D(
                Math.min(newEdges.getFirst().getX(), edges.getFirst().getX()),
                Math.max(newEdges.getFirst().getY(), edges.getFirst().getY())
        );
        Coord2D newRightDownCoordinates = new Coord2D(
                Math.max(newEdges.getSecond().getX(), edges.getSecond().getX()),
                Math.min(newEdges.getSecond().getY(), edges.getSecond().getY())
        );

        edges.setFirst(newLeftHighCoordinates);
        edges.setSecond(newRightDownCoordinates);
    }


    public Edges getEdges() {
        return edges;
    }


    public void reset(Coord2D defaultCoordinates) {
        edges.setFirst(defaultCoordinates);
        edges.setSecond(defaultCoordinates);
    }
}
