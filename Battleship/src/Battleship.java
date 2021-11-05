import java.util.ArrayList;

public class Battleship extends Ship {

    public Battleship(int length) {
        super(length);
        this.Hits = new ArrayList<>(Values.BATTLESHIP_LEN);
    }

}
