package datastructures.graphs.coordinates;

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

        if(Math.abs(getX() - second.getX()) < 0.000001 &&
                Math.abs(getY() - second.getY()) < 0.000001) {
            return 0;
        }

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
