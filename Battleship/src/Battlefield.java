import java.io.IOException;
import java.util.ArrayList;

public class Battlefield {
    private int rows;
    private int columns;

    public ArrayList<Cell> Field;

    public Battlefield(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.Field = new ArrayList<Cell>();
    }

    // Filling battlefield and giving cells their numbers
    public void fillCells() {
        for (int i = 0; i < rows * columns; i++) {
            this.Field.add(new Cell(i));
        }
    }

    // Printing current battlefield view for player.
    public void printCurrent() throws IOException, InterruptedException {
        Values.CLEAR_CONSOLE.start().waitFor();
        System.out.println();

        System.out.print(Values.COLOR_BLUE);
        System.out.printf("%-3s", "၀");
        System.out.print(Values.COLOR_WHITE);
        System.out.print("- unexplored water\n");

        System.out.print(Values.COLOR_RED);
        System.out.printf("%-3s", "×");
        System.out.print(Values.COLOR_WHITE);
        System.out.print("- missed shot\n");

        System.out.print(Values.COLOR_GREEN);
        System.out.printf("%-3s", "✓");
        System.out.print(Values.COLOR_WHITE);
        System.out.print("- wrecked ship\n");

        System.out.print("   ");
        for(int i = 0; i < columns; i++) {
            System.out.printf("%-3d", i + 1);
        }
        System.out.println();

        for(int i = 0; i < rows; i++) {
            System.out.printf("%-3d", i + 1);

            for(int j = 0; j < columns; j++) {
                if(Field.get(i * columns + j).Dead && Field.get(i * columns + j).OnShip) {
                    System.out.print(Values.COLOR_GREEN);
                    System.out.printf("%-3s", "✓");
                    System.out.print(Values.COLOR_WHITE);
                } else if (Field.get(i * columns + j).Dead){
                    System.out.print(Values.COLOR_RED);
                    System.out.printf("%-3s", "×");
                    System.out.print(Values.COLOR_WHITE);
                } else {
                    System.out.print(Values.COLOR_BLUE);
                    System.out.printf("%-3s", "၀");
                    System.out.print(Values.COLOR_WHITE);
                }
            }
            System.out.println();
        }

        System.out.println("enter coordinates :\"x y\"");
    }

    // Printing original view of battlefield.
    public void printFinal() {
        System.out.println();

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(Field.get(i * columns + j).OnShip) {
                    System.out.print(Values.COLOR_CYAN);
                    System.out.printf("%-3s", "∎");
                } else {
                    System.out.print(Values.COLOR_BLUE);
                    System.out.printf("%-3s", "၀");
                }
                System.out.print(Values.COLOR_WHITE);
            }
            System.out.println();
        }
    }

}
