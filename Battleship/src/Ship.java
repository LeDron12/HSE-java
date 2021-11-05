import java.util.ArrayList;

public class Ship {
    private int length;

    public boolean Placed;
    public boolean Alive;
    ArrayList<Cell> Hits; // Cells where ship is situated.

    public Ship(int length) {
        this.Placed = false;
        this.Alive = true;
        this.length = length;
    }

    // Checking is ship is alive.
    public boolean checkIfAlive() {
        for (int i = 0; i < Hits.size(); i++) {
            if(Hits.get(i).Dead == false) {
                return true;
            }
        }
        Alive = false;
        return false;
    }

    public int getLength() {
        return length;
    }

}
