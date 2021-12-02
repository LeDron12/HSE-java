package datastructures.graphs;

public class Space {
    private Edges edges; // Edge coordinates.
    private BoundBox boundBox; // Container with all items.
    private Coord2D startCoordinates; // Start coordinates.

    public Space(Coord2D startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public void addItem(Coord2D coordinates, Class<? extends Model> itemClass) {
        if(itemClass == Point.class) {
            boundBox.addItem(new Point(coordinates));
        } else if (itemClass == Origin.class) {
            boundBox.addItem(new Origin(coordinates));
        } else {
            System.out.println("Incorrect item type.\n" +
                    "\"origin\" or \"point\" expected. ");
        }
    }

    public void print() {
        boundBox.print();
    }
}
