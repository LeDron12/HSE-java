package datastructures.graphs;

import datastructures.graphs.coordinates.*;
import datastructures.graphs.exceptions.*;
import datastructures.graphs.model.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class OrientedGraph {
    private final Origin spaceOrigin;
    private final HashMap<Model, HashSet<Model>> hashMap;

    public OrientedGraph(Coord2D spaceCoordinates) {
        hashMap = new HashMap<Model, HashSet<Model>>();
        spaceOrigin = new Origin(spaceCoordinates);
    }

    private boolean hasVertex(Model model) {
        return hashMap.containsKey(model);
    }

    private void addEdge(Model modelOne, Model modelTwo) throws DAGConstraintException {
        if (modelOne.getClass() == Point.class) {
            throw new DAGConstraintException("Point type can't have children");
        }
        if (hasEdge(modelTwo, modelOne)) {
            throw new DAGConstraintException("Can't create cycled edge");
        }
        if (hasEdge(modelOne, modelTwo)) {
            return;
        }

        if (hasVertex(modelOne)) {
            hashMap.get(modelOne).add(modelTwo);
        } else {
            if (!modelOne.equals(spaceOrigin)) {
                hashMap.put(modelOne, new HashSet<Model>());
                hashMap.get(modelOne).add(modelTwo);
            } else {
                spaceOrigin.getChildren().add(modelTwo); // не еужно
                hashMap.put(modelTwo, new HashSet<Model>());
            }
        }
        addChild(modelOne, modelTwo);
    }

    private boolean hasEdge(Model modelOne, Model modelTwo) {
        if (!hasVertex(modelOne)) {
            return false;
        }
        if (hashMap.get(modelOne).contains(modelTwo)) {
            return true;
        }
        return false;
    }

    private void addChild(Model modelOne, Model modelTwo) {
        Origin model = (Origin) modelOne;
        model.getChildren().add(modelTwo);
    }

    public Optional<Model> findModelByCoordinates(Model comparingModel) {
        for (var model : hashMap.keySet()) {
            if (model.compareTo(comparingModel) == 0) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }

    public void insert(Coord2D parentCoordinates, Model item) throws DAGConstraintException {
        Model parentModel;
        Optional<Model> optionalParentModel = findModelByCoordinates(new Model(parentCoordinates));

        if (optionalParentModel.isEmpty()) {
            if(spaceOrigin.getPosition().compareTo(parentCoordinates) == 0) {
                parentModel = spaceOrigin;
            } else {
                throw new DAGConstraintException("Cant find parent with coordinates "
                        + parentCoordinates.getX() + " " + parentCoordinates.getY());
            }
        } else {
            parentModel = optionalParentModel.get();
        }

        addEdge(parentModel, item);
    }

    public void recomputeBounds(Model parent, Model current) {
        Origin originParent = (Origin) parent;
        Origin originCurrent = null;
        Point pointCurrent = null;

        current.getBounds().reset(current.getPosition());
        if (current.getChildrenCount() != 0) {
            originCurrent = (Origin) current;
            for (var child : originCurrent.getChildren()) {
                recomputeBounds(originCurrent, child);
            }
        }

        if (current.getClass() == Point.class) {
            pointCurrent = (Point) current;
            Edges edgesToCompare = originParent.getBounds().getEdges().addCoordinates(pointCurrent.getBounds().getEdges());
            originParent.getBounds().setNewEdges(edgesToCompare);
        } else {
            originCurrent = (Origin) current;
            Edges edgesToCompare = parent.getBounds().getEdges().addCoordinates(originCurrent.getBounds().getEdges());
            originParent.getBounds().setNewEdges(edgesToCompare);
        }
    }

    public Origin getSpaceOrigin() {
        return spaceOrigin;
    }
}
