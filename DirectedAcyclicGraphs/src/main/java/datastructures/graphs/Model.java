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
        // Drawing library vibes...
    }

    @Override
    public int compareTo(Object obj) {
        Coord2D second = (Coord2D)obj;
        if(position.getX() > second.getX()) {
            return 1;
        } else if (position.getX() < second.getX()) {
            return -1;
        } else {
            if(position.getY() > second.getY()) {
                return 1;
            } else if (position.getY() < second.getY()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
