import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    private final Random rnd = new Random();

    private Battlefield battlefield;

    private ArrayList<Carrier> carrierList;
    private ArrayList<Battleship> battleshipList;
    private ArrayList<Cruiser> cruiserList;
    private ArrayList<Destroyer> destroyerList;
    private ArrayList<Submarine> submarineList;

    private boolean gameOver = false;

    private final int rows;
    private final int columns;

    // Coordinates.
    private int x;
    private int y;

    public Game(int rows, int columns) throws IOException, InterruptedException {
        this.rows = rows;
        this.columns = columns;

        this.carrierList = fillShips(Values.CARRIER_LEN, 1 * Values.MULTIPLIER);
        this.battleshipList = fillShips(Values.BATTLESHIP_LEN, 2 * Values.MULTIPLIER);
        this.cruiserList = fillShips(Values.CRUISER_LEN, 3 * Values.MULTIPLIER);
        this.destroyerList = fillShips(Values.DESTROYER_LEN, 4 * Values.MULTIPLIER);
        this.submarineList = fillShips(Values.SUBMARINE_LEN, 5 * Values.MULTIPLIER);

        this.battlefield = new Battlefield(rows, columns);
        battlefield.fillCells();
        fillBattlefield();
        startGame();
    }

    // Interraction with player.
    private void startGame() throws IOException, InterruptedException {
        battlefield.printCurrent();

        do {
            boolean check;
            do {
                check = true;
                try {
                    x = Integer.parseInt(scanner.next());
                    y = Integer.parseInt(scanner.next());
                } catch (NumberFormatException exception) {
                    System.out.println(Values.COLOR_RED + "incorrect coordinates" + Values.COLOR_WHITE);
                    check = false;
                }

                if(x <= 0 || x > rows || y <= 0 || y > columns) {
                    check = false;
                    System.out.println(Values.COLOR_RED + "incorrect coordinates" + Values.COLOR_WHITE);
                } else {
                    x--;
                    y--;
                }
            }
            while (!check);

            battlefield.Field.get(x * columns + y).Dead = true;
            battlefield.printCurrent();

            if(!anyShipAlive()) {
                gameOver = true;
            }
        }
        while (!gameOver);

        System.out.println("Congratulations, you WON!!!");
        battlefield.printFinal();
    }

    // Checks if there is any ship alive on battlefield.
    private boolean anyShipAlive() {
        for (Carrier carrier : carrierList) {
            carrier.checkIfAlive();
            if(carrier.Alive) {
                return true;
            } else {
                System.out.println("you have just sunk Carrier!");
            }
        }
        for (Battleship battleship : battleshipList) {
            battleship.checkIfAlive();
            if(battleship.Alive) {
                return true;
            } else {
                System.out.println("you have just sunk Battleship!");
            }
        }
        for (Cruiser cruiser : cruiserList) {
            cruiser.checkIfAlive();
            if(cruiser.Alive) {
                return true;
            } else {
                System.out.println("you have just sunk Cruiser!");
            }
        }
        for (Destroyer destroyer : destroyerList) {
            destroyer.checkIfAlive();
            if(destroyer.Alive) {
                return true;
            } else {
                System.out.println("you have just sunk Destroyer!");
            }
        }
        for (Submarine submarine : submarineList) {
            submarine.checkIfAlive();
            if(submarine.Alive) {
                return true;
            } else {
                System.out.println("you have just sunk Submarine!");
            }
        }
        return false;
    }

    // Placing each ship on the battlefield.
    private void fillBattlefield() {
        for (Carrier carrier : carrierList) {
            if(!placeShip(carrier, rnd.nextInt(rows * columns), 0)){
                System.out.println(Values.COLOR_RED + "Cant place ship " + Values.COLOR_WHITE + "(carrier)");
                return;
            }
        }
        for (Battleship battleship : battleshipList) {
            if(!placeShip(battleship, rnd.nextInt(rows * columns), 0)){
                System.out.println(Values.COLOR_RED + "Cant place ship" + Values.COLOR_WHITE + "(battleship)");
                return;
            }
        }
        for (Cruiser cruiser : cruiserList) {
            if(!placeShip(cruiser, rnd.nextInt(rows * columns), 0)){
                System.out.println(Values.COLOR_RED + "Cant place ship" + Values.COLOR_WHITE + "(cruiser)");
                return;
            }
        }
        for (Destroyer destroyer : destroyerList) {
            if(!placeShip(destroyer, rnd.nextInt(rows * columns), 0)){
                System.out.println(Values.COLOR_RED + "Cant place ship" + Values.COLOR_WHITE + "(destroyer)");
                return;
            }
        }
        for (Submarine submarine : submarineList) {
            if(!placeShip(submarine, rnd.nextInt(rows * columns), 0)){
                System.out.println(Values.COLOR_RED + "Cant place ship" + Values.COLOR_WHITE + "(submarine)");
                return;
            }
        }
    }

    // Placing only ship and marking all surrounding cells.
    private boolean placeShip(Ship ship, int pos, int counter) {
        if(counter == rows * columns + 1) {
            System.out.println(Values.COLOR_RED + "Cant chose cell for ship" + Values.COLOR_WHITE);
            return false;
        }
        if(battlefield.Field.get(pos).OnShip == false && battlefield.Field.get(pos).NearShip == false && canPlaceNext(ship, pos)) {
            markNearCells(ship);
        } else {
            placeShip(ship, (pos + 1) % (rows * columns), counter + 1); // If we have chosen busy cell.
        }
        return true;
    }

    // Checks if ship can be placed on the battlefield and places it.
    private boolean canPlaceNext(Ship ship, int pos) {

        // 4 vectors of possible ship positions
        int[][] vector = new int[4][];
        vector[0] = new int[ship.getLength() - 1];
        vector[1] = new int[ship.getLength() - 1];
        vector[2] = new int[ship.getLength() - 1];
        vector[3] = new int[ship.getLength() - 1];

        boolean[] isVector = {true, true, true, true};

        // Checking which vector can we place the ship.
        for (int i = 1; i < ship.getLength(); i++) {
            int posUp = pos - (i * columns);
            if (posUp >= 0 && !battlefield.Field.get(posUp).OnShip && !battlefield.Field.get(posUp).NearShip && isVector[0]) {
                vector[0][i - 1] = posUp;
            } else {
                isVector[0] = false;
            }

            int posRight = pos + i;
            if (posRight < rows * columns && (posRight % columns) != 0 && !battlefield.Field.get(posRight).OnShip && !battlefield.Field.get(posRight).NearShip && isVector[1]) {
                vector[1][i - 1] = posRight;
            } else {
                isVector[1] = false;
            }

            int posDown = pos + (i * columns);
            if (posDown < rows * columns && !battlefield.Field.get(posDown).OnShip && !battlefield.Field.get(posDown).NearShip && isVector[2]) {
                vector[2][i - 1] = posDown;
            } else {
                isVector[2] = false;
            }

            int posLeft = pos - (i * columns); //ТУТ просто - i
            if (posLeft >= 0 && (posLeft % columns) != columns - 1 && !battlefield.Field.get(posLeft).OnShip && !battlefield.Field.get(posLeft).NearShip && isVector[3]) {
                vector[3][i - 1] = posLeft;
            } else {
                isVector[3] = false;
            }
        }

        // Filling cells that ship is situated on.
        for(int j = 0; j < 4; j++) {
            if(isVector[j]) {
                ship.Hits.add(battlefield.Field.get(pos));
                battlefield.Field.get(pos).OnShip = true;
                for(int i = 0; i < ship.getLength() - 1; i++) {
                    ship.Hits.add(battlefield.Field.get(vector[j][i]));
                    battlefield.Field.get(vector[j][i]).OnShip = true;
                }
                return true;
            }
        }
        return false;
    }

    // Method that marks cells, that surrounds ship.
    private void markNearCells(Ship ship) {
        int posUpLeft, posUp, posUpRight, posRight, posDownLeft, posDown, posDownRight, posLeft;

        for (Cell cell : ship.Hits) {
            posUpLeft = cell.getPosition() - columns - 1;
            posUp = cell.getPosition() - columns;
            posUpRight = cell.getPosition() - columns + 1;
            posRight = cell.getPosition() + 1;
            posDownRight = cell.getPosition() + columns + 1;
            posDown = cell.getPosition() + columns;
            posDownLeft = cell.getPosition() + columns - 1;
            posLeft = cell.getPosition() - 1;

            if(posUpLeft >= 0 && !battlefield.Field.get(posUpLeft).OnShip) {
                battlefield.Field.get(posUpLeft).NearShip = true;
            }
            if(posUp >= 0 && !battlefield.Field.get(posUp).OnShip) {
                battlefield.Field.get(posUp).NearShip = true;
            }
            if(posUpRight >= 0 && !battlefield.Field.get(posUpRight).OnShip) {
                battlefield.Field.get(posUpRight).NearShip = true;
            }
            if(posRight < rows * columns && !battlefield.Field.get(posRight).OnShip) {
                battlefield.Field.get(posRight).NearShip = true;
            }
            if(posDownLeft < rows * columns && !battlefield.Field.get(posDownLeft).OnShip) {
                battlefield.Field.get(posDownLeft).NearShip = true;
            }
            if(posDown < rows * columns && !battlefield.Field.get(posDown).OnShip) {
                battlefield.Field.get(posDown).NearShip = true;
            }
            if(posDownRight < rows * columns && !battlefield.Field.get(posDownRight).OnShip) {
                battlefield.Field.get(posDownRight).NearShip = true;
            }
            if(posLeft >= 0 && !battlefield.Field.get(posLeft).OnShip) {
                battlefield.Field.get(posLeft).NearShip = true;
            }
        }
    }

    // Filling lists with instances of ships.
    private ArrayList fillShips(int index, int capacity) {
        ArrayList ships = new ArrayList();

        for (int i = 0; i < capacity; i++) {
            switch (index) {
                case (5):
                    if(ships.size() == 0) {
                        ships = new ArrayList<Carrier>();
                    }
                    ships.add(new Carrier(Values.CARRIER_LEN));
                    break;
                case (4):
                    if(ships.size() == 0) {
                        ships = new ArrayList<Battleship>();
                    }
                    ships.add(new Battleship(Values.BATTLESHIP_LEN));
                    break;
                case (3):
                    if(ships.size() == 0) {
                        ships = new ArrayList<Cruiser>();
                    }
                    ships.add(new Cruiser(Values.CRUISER_LEN));
                    break;
                case (2):
                    if(ships.size() == 0) {
                        ships = new ArrayList<Destroyer>();
                    }
                    ships.add(new Destroyer(Values.DESTROYER_LEN));
                    break;
                case (1):
                    if(ships.size() == 0) {
                        ships = new ArrayList<Submarine>();
                    }
                    ships.add(new Submarine(Values.SUBMARINE_LEN));
                    break;
            }
        }

        return ships;
    }
}
