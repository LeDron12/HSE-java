package datastructures.graphs;

public class Main {
    public static void main(String[] args) {
        String first, second, itemClassName;
        Coord2D coordinates;
        Class<? extends Model> itemClass;

        System.out.println("Please, enter start coordinates here, if you haven't done it in arguments' line:\n");
        coordinates = Util.validateCoordinates(args[0], args[1]);

        Space space = new Space(coordinates);

        System.out.println("!!! Input \"clear\" if you made a mistake !!!\n" +
                "!!! Input \"end\" if you want to finnish !!!");
        do {
            System.out.println("Input first coordinate: \"x\"\n");
            first = Util.readNext();
            if(first.contains("end")) {
                System.out.println("Input stopped\n");
                break;
            }
            if (first.contains("clear")) {
                System.out.println("Input cleared\n");
                continue;
            }

            System.out.println("Input second coordinate: \"y\"\n");
            second = Util.readNext();
            if(second.contains("end")) {
                System.out.println("Input stopped\n");
                break;
            }
            if (second.contains("clear")) {
                System.out.println("Input cleared\n");
                continue;
            }

            System.out.println("Input item type: \"origin\" or \"point\"\n");
            itemClassName = Util.readNext();
            if(itemClassName.contains("end")) {
                System.out.println("Input stopped\n");
                break;
            }
            if (itemClassName.contains("clear")) {
                System.out.println("Input cleared\n");
                continue;
            }

            coordinates = Util.validateCoordinates(first, second);
            try {
                itemClass = Util.getItemClass(itemClassName);
            } catch (ClassNotFoundException exception) {
                System.out.println("Type " + itemClassName + " is invalid\n");
                continue;
            }
            space.addItem(coordinates, itemClass);

            System.out.println(first + " " + second + " " + itemClassName + " " + itemClass.getName());
        } while (true);
    }
}
