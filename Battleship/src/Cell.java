public class Cell {
    private int position;

    public boolean OnShip; // If cell is under any ship.
    public boolean NearShip; // If cell is near any ship.
    public boolean Dead; // If cell is shot.

    public Cell(int position) {
        this.position = position;
        this.OnShip = false;
        this.NearShip = false;
        this.Dead = false;
    }

    public int getPosition() {
        return position;
    }
}
