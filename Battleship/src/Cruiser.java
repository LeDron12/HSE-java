import java.util.ArrayList;

public class Cruiser extends Ship {

    public Cruiser(int length) {
        super(length);
        this.Hits = new ArrayList<>(Values.CRUISER_LEN);
    }

}
