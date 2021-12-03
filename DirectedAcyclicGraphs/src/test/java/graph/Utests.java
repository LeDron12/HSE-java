package graph;

import datastructures.graphs.exceptions.DAGConstraintException;
import datastructures.graphs.coordinates.*;
import datastructures.graphs.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import datastructures.graphs.*;

public class Utests {

    @Test
    void checkDefaultTestFromTask() throws DAGConstraintException {
        Coord2D startCoords, first, second, third, fourth;
        startCoords = new Coord2D(1, 1);

        Space space = new Space(startCoords);

        first = new Coord2D(1, 1);
        second = new Coord2D(-2, 1);
        third = new Coord2D(1, 0);
        fourth = new Coord2D(-1, -1);

        space.addItem(first, second, Origin.class);
        space.addItem(first, third, Origin.class);
        space.addItem(second, fourth, Point.class);
        space.addItem(third, fourth, Point.class);

        Model model = space.getItem(first);

        Assertions.assertEquals(2.0 ,
                model.getBounds().getEdges().getFirst().getX() +
                model.getBounds().getEdges().getFirst().getY() +
                        model.getBounds().getEdges().getSecond().getX() +
                        model.getBounds().getEdges().getSecond().getY()
                );
    }

    @Test
    void createSpaceWithNullCoordinates() throws NullPointerException {
        Assertions.assertThrows(NullPointerException.class, () -> new Space(null));
    }

    @Test
    void createInsertWithNullCoordinates() throws NullPointerException {
        Space space = new Space(new Coord2D(1, 1));
        Assertions.assertThrows(NullPointerException.class,
                () -> space.addItem(null, new Coord2D(1, 1), Point.class));
    }

    @Test
    void createItemWithWrongClass() {
        Space space = new Space(new Coord2D(1, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> space.addItem(new Coord2D(12, 12), new Coord2D(1, 1), Model.class));
    }

    @Test
    void getItemWithNullCoordinates() throws DAGConstraintException {
        Space space = new Space(new Coord2D(1, 1));
        Assertions.assertThrows(NullPointerException.class, () -> space.getItem(null));
    }

    @Test
    void getItemThatDoesntBelongToSpace() throws DAGConstraintException {
        Coord2D start = new Coord2D(5, 5);
        Space space = new Space(start);

        space.addItem(start, new Coord2D(1, 1), Origin.class);
        space.addItem(start, new Coord2D(2, 2), Point.class);
        Assertions.assertThrows(DAGConstraintException.class, () -> space.getItem(new Coord2D(3, 3)));
    }

    @Test
    void hugeCorrectTest() throws DAGConstraintException {
        Coord2D start = new Coord2D(3, 4);
        Model model;
        Space space = new Space(start);

        space.addItem(new Coord2D(3, 4), new Coord2D(-1, -2), Origin.class);
        model = space.getItem(start);
        System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
        System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
        System.out.println();

        space.addItem(new Coord2D(-1, -2), new Coord2D(3, -3), Origin.class);
        model = space.getItem(start);
        System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
        System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
        System.out.println();

        space.addItem(new Coord2D(3, 4), new Coord2D(-7, -5), Origin.class);
        model = space.getItem(start);
        System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
        System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
        System.out.println();

        space.addItem(new Coord2D(-1, -2), new Coord2D(-3, 5), Origin.class);
        model = space.getItem(start);
        System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
        System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
        System.out.println();

        space.addItem(new Coord2D(-3, 5), new Coord2D(1, -10), Origin.class);
        model = space.getItem(start);
        System.out.println(model.getBounds().getEdges().getFirst().getX() + " " + model.getBounds().getEdges().getFirst().getY());
        System.out.println(model.getBounds().getEdges().getSecond().getX() + " " + model.getBounds().getEdges().getSecond().getY());
        System.out.println();

        double ret = model.getBounds().getEdges().getFirst().getX() +
                model.getBounds().getEdges().getFirst().getY() +
                model.getBounds().getEdges().getSecond().getX() +
                model.getBounds().getEdges().getSecond().getY();
        Assertions.assertEquals(5.0, ret);
    }

    @Test
    void callSetNewEdgesWithNullArgument() throws DAGConstraintException {
        Coord2D start = new Coord2D(3, 4);
        Space space = new Space(start);
        space.addItem(new Coord2D(3, 4), new Coord2D(2, 2), Origin.class);
        Model model = space.getItem(new Coord2D(2, 2));

        Assertions.assertThrows(IllegalArgumentException.class, () -> model.getBounds().setNewEdges(null));
    }

    @Test
    void callCompaToWithBadArgumentType() throws DAGConstraintException {
        Coord2D start = new Coord2D(3, 4);
        Space space = new Space(start);
        space.addItem(new Coord2D(3, 4), new Coord2D(2, 2), Origin.class);
        Model model = space.getItem(new Coord2D(2, 2));

        Assertions.assertThrows(IllegalArgumentException.class, () -> model.getBounds().getEdges().getFirst().compareTo(space));
    }

    @Test
    void edgesSum() {
        Edges edges = new Edges(new Coord2D(1, 2), new Coord2D(12, 2));
        edges.addCoordinates(new Edges(new Coord2D(1, 21), new Coord2D(12, 2)));

        Assertions.assertEquals(2.0, edges.getFirst().getY());
    }
}
