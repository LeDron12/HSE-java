package datastructures.graphs;

import datastructures.graphs.coordinates.*;
import datastructures.graphs.exceptions.*;
import datastructures.graphs.model.*;

import java.util.*;

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
            if (!hasVertex(modelTwo)) {
                hashMap.put(modelTwo, new HashSet<Model>());
            }
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
        if (parentCoordinates == null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }
        Optional<Model> optionalParentModel = findModelByCoordinates(new Model(parentCoordinates));

        if (optionalParentModel.isEmpty()) {
            if (spaceOrigin.getPosition().compareTo(parentCoordinates) == 0) {
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


    public Edges recomputeBounds(Model parent, Model current) {
        Origin originParent = (Origin) parent;
        Origin originCurrent = null;
        Point pointCurrent = null;

        current.getBounds().reset(current.getPosition());
        if (current.getChildrenCount() != 0) {
            originCurrent = (Origin) current;
            //Edges currentEdges = current.getBounds().getEdges();
            List<Edges> helpEdges = new ArrayList<Edges>();
            for (var child : originCurrent.getChildren()) {
                helpEdges.add(recomputeBounds(originCurrent, child));
            }
            for (var edges : helpEdges) {
                originCurrent.getBounds().setNewEdges(edges);
            }
        }


        if (current.getClass() == Point.class) {
            pointCurrent = (Point) current;
            return originParent.getBounds().getEdges().addCoordinates(pointCurrent.getBounds().getEdges());
        } else {
            originCurrent = (Origin) current;
            return parent.getBounds().getEdges().addCoordinates(originCurrent.getBounds().getEdges());
        }
    }


    public Origin getSpaceOrigin() {
        return spaceOrigin;
    }
}
