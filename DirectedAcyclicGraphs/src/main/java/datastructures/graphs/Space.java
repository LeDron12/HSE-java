package datastructures.graphs;

public class Space {
    private final Edges edges; // Edge coordinates.
    private final BoundBox boundBox; // Container with all items.
    private final Coord2D startCoordinates; // Start coordinates.

    public Space(Coord2D startCoordinates) {
        this.startCoordinates = startCoordinates;
        this.edges = new Edges(startCoordinates, startCoordinates);
        this.boundBox = new BoundBox(edges);
    }

    public void addItem(Coord2D coordinates, Class<? extends Model> itemClass) {
        if(itemClass == Point.class) {
            checkEdges(coordinates);
            boundBox.addItem(new Point(coordinates));
        } else if (itemClass == Origin.class) {
            checkEdges(coordinates);
            boundBox.addItem(new Origin(coordinates));
        } else {
            System.out.println("Incorrect item type.\n" +
                    "\"origin\" or \"point\" expected. ");
        }
    }

    private void checkEdges(Coord2D coordinates) {
        Coord2D newLeftHighCoordinates = new Coord2D(
                Math.min(coordinates.getX(), edges.getFirst().getX()),
                Math.max(coordinates.getY(), edges.getFirst().getY())
        );
        Coord2D newRightDownCoordinates = new Coord2D(
                Math.max(coordinates.getX(), edges.getSecond().getX()),
                Math.min(coordinates.getY(), edges.getSecond().getY())
        );

        edges.setFirst(newLeftHighCoordinates);
        edges.setSecond(newRightDownCoordinates);
    }

    public void print() {
        boundBox.print();
    }
}
