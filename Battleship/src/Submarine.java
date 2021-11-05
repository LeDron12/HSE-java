import java.util.ArrayList;

public class Submarine extends Ship {

    public Submarine(int length) {
        super(length);
        this.Hits = new ArrayList<>(Values.SUBMARINE_LEN);
    }

}
