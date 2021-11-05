import java.util.ArrayList;

public class Destroyer extends Ship {

    public Destroyer(int length) {
        super(length);
        this.Hits = new ArrayList<>(Values.DESTROYER_LEN);
    }

}