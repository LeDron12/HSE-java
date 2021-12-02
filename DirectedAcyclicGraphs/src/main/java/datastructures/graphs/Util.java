package datastructures.graphs;

import java.util.Scanner;

public class Util {
    public static Scanner consoleScanner = new Scanner(System.in);

    public static String readNext() {
        return consoleScanner.next().trim();
    }

    public static <T extends Model> Class<? extends Model> getItemClass(String itemName) throws ClassNotFoundException{
        if(itemName.toLowerCase().contains("point")) {
            return Point.class;
        } else if (itemName.toLowerCase().contains("origin")) {
            return Origin.class;
        } else {
            throw new ClassNotFoundException("No such class");
        }
    }

    public static Coord2D validateCoordinates(String first, String second) {
        double x = 0, y = 0;
        boolean flag;

        do {
            flag = true;

            try {
                x = Double.parseDouble(first);
                y = Double.parseDouble(second);
            } catch (NumberFormatException formatException) {
                System.out.println("Incorrect data format.\n" +
                        "Please enter coordinates in format: \"x y\"\n");
                flag = false;
            } catch (NullPointerException nullException) {
                System.out.println("Command line is empty.\n" +
                        "Please, enter coordinates in format \"x y\"\n");
                flag = false;
            }

            if(flag == false) {
                first = consoleScanner.next();
                second = consoleScanner.next();
            }
        } while (!flag);

        return new Coord2D(x, y);
    }
}
