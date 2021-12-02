package datastructures.graphs;

public class Coord2D implements Comparable{
    private double x;
    private double y;

    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(int value) {
        this.x = value;
    }

    public void setY(int value) {
        this.y = value;
    }

    @Override
    public int compareTo(Object obj) {
        Coord2D second = (Coord2D)obj;

        if(getX() > second.getX()) {
            return 1;
        } else if (getX() < second.getX()) {
            return -1;
        } else {
            if(getY() > second.getY()) {
                return 1;
            } else if (getY() < second.getY()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}

class Edges {
    private Coord2D first;
    private Coord2D second;

    private double height;
    private double length;

    private double size;

    public Edges(Coord2D firtst, Coord2D second) {
        this.first = firtst;
        this.second = second;

        this.length = computeLength(firtst.getX(), second.getX());
        this.height = computeLength(firtst.getY(), second.getY());

        size = length * height;
    }

    private double computeLength(double first, double second) {
        return Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2));
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getSize() {
        return size;
    }

    public Coord2D getFirst() {
        return first;
    }

    public void setFirst(Coord2D first) {
        this.first = first;
    }

    public Coord2D getSecond() {
        return second;
    }

    public void setSecond(Coord2D second) {
        this.second = second;
    }
}
