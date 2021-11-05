import java.util.ArrayList;

public class Carrier extends Ship {

    public Carrier(int length) {
        super(length);
        this.Hits = new ArrayList<>(Values.CARRIER_LEN);
    }

}
