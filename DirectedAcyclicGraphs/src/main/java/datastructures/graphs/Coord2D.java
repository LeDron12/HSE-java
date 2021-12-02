package datastructures.graphs;

public class Coord2D {
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
}
