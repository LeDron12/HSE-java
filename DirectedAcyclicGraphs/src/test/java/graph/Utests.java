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
}
