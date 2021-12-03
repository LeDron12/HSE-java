package datastructures.graphs;

import datastructures.graphs.exceptions.*;
import datastructures.graphs.model.*;
import datastructures.graphs.coordinates.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Space {
    private final OrientedGraph itemGraph; // Container with all items.
    private final Coord2D startCoordinates; // Start coordinates.


    public Space(Coord2D startCoordinates) throws NullPointerException {
        if (startCoordinates == null) {
            throw new NullPointerException("Null arguments are restricted");
        }
        this.startCoordinates = startCoordinates;
        itemGraph = new OrientedGraph(startCoordinates);
    }


    /// Use space position if you want to create new independent Origin.
    public void addItem(Coord2D parentCoordinates,
                        Coord2D coordinates,
                        Class<? extends Model> itemClass) throws IllegalArgumentException, NullPointerException, DAGConstraintException {
        if (parentCoordinates == null || coordinates == null || itemClass == null) {
            throw new NullPointerException("Null arguments are restricted");
        }

        if (itemClass == Point.class) {
            itemGraph.insert(parentCoordinates, new Point(coordinates));
        } else if (itemClass == Origin.class) {
            itemGraph.insert(parentCoordinates, new Origin(coordinates));
        } else {
            throw new IllegalArgumentException("Type " + itemClass.getName() + " is restricted");
        }
    }


    public Model getItem(Coord2D coordinates) throws DAGConstraintException {
        Model item;
        Optional<Model> optionalItem = itemGraph.findModelByCoordinates(new Model(coordinates));

        if (coordinates == null) {
            throw new NullPointerException("Null arguments are restricted");
        }

        if (optionalItem.isEmpty()) {
            if (coordinates.compareTo(startCoordinates) == 0) {
                item = itemGraph.getSpaceOrigin();
            } else {
                throw new DAGConstraintException("Cant find item with coordinates "
                        + coordinates.getX() + " " + coordinates.getY());
            }
        } else {
            item = optionalItem.get();
        }

        itemGraph.getSpaceOrigin().getBounds().reset(startCoordinates);
        List<Edges> helpEdges = new ArrayList<Edges>();
        for (var child : itemGraph.getSpaceOrigin().getChildren()) {
            helpEdges.add(itemGraph.recomputeBounds(itemGraph.getSpaceOrigin(), child));
        }
        for (var edges : helpEdges) {
            itemGraph.getSpaceOrigin().getBounds().setNewEdges(edges);
        }

        return item;
    }
}
