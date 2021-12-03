package datastructures.graphs;

import datastructures.graphs.coordinates.*;
import datastructures.graphs.model.*;
import datastructures.graphs.exceptions.*;

public class Main {
    public static void main(String[] args) {
        Coord2D startCoords, first, second, third, fourth;
        Model model = null;
        startCoords = new Coord2D(1, 1);

        Space space = new Space(startCoords);

        first = new Coord2D(1, 1);
        second = new Coord2D(-2, 1);
        third = new Coord2D(1, 0);
        fourth = new Coord2D(-1, -1);
        try {
            space.addItem(first, second, Origin.class);
            model = space.getItem(first);
            System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
            System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
            System.out.println();

            space.addItem(first, third, Origin.class);
            model = space.getItem(first);
            System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
            System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
            System.out.println();

            space.addItem(second, fourth, Point.class);
            model = space.getItem(first);
            System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
            System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
            System.out.println();

            space.addItem(third, fourth, Point.class);
            model = space.getItem(first);
            System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
            System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
            System.out.println();
        } catch (DAGConstraintException exception) {
            System.out.println("GGWP");
        } catch (Exception ex) {
            System.out.println("model is null :(");
        }

        double str;
        str = model.getBounds().getEdges().getFirst().getX() +
                model.getBounds().getEdges().getFirst().getY() +
                model.getBounds().getEdges().getSecond().getX() +
                model.getBounds().getEdges().getSecond().getY();
        System.out.println(str);
    }
}
