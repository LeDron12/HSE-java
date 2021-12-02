package datastructures.graphs;

public class Model implements Comparable, Printable {
    private Coord2D position;

    public Model(Coord2D position) {
        this.position = position;
    }

    public Coord2D getPosition() {
        return this.position;
    }

    public void setPosition(Coord2D position) {
        this.position = position;
    }

    public void print() {
        // sad overridden empty method :(
    }

    @Override
    public int compareTo(Object obj) {
        Model second = (Model) obj;

        return position.compareTo(second.position);
    }
}
