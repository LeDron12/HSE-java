package datastructures.graphs.coordinates;

public class Edges {
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

    public Edges addCoordinates(Edges secondEdges) {
        Coord2D newCoordOne = new Coord2D(
                first.getX() + secondEdges.first.getX(),
                first.getY() + secondEdges.first.getY());
        Coord2D newCoordTwo = new Coord2D(
                second.getX() + secondEdges.second.getX(),
                second.getY() + secondEdges.second.getY());

        return new Edges(newCoordOne, newCoordTwo);
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
