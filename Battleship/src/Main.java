import java.io.IOException;
import java.util.Scanner;

class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) throws InterruptedException, IOException {
        int rows = 0, columns = 0;
        boolean checked = false;

        System.out.println(Values.COLOR_GREEN + "!!!ANNOTATION!!!" + Values.COLOR_WHITE + '\n' +
                "columns * rows has minimal value cause otherwise we cant place 1 2 3 4 5 ships\n" +
                "number of ships counts automaticly according to fields's size and proportional to 1 2 3 4 5, for example (3 6 9 12 15)\n" +
                "have a good game!\n");

        // Checking if there are no arguments in command line
        // Or arguments exceed integer values.
        System.out.println("please enter field's size:\n" +
                "rows and columns: \"rows columns\"");
        do {
            try {
                if(args.length < 2 || args[0].length() > 9 || args[1].length() > 9 || checked) {
                    rows = Integer.parseInt(scanner.next());
                    columns = Integer.parseInt(scanner.next());
                } else {
                    rows = Integer.parseInt(args[0]);
                    columns = Integer.parseInt(args[1]);
                    checked = true;
                }
            } catch (NumberFormatException exception) {
                    rows = 10;
                    columns = 10;
                    System.out.println(Values.COLOR_RED + "incorrect agruments OR rows*columns is less than 70\n"
                            + Values.COLOR_WHITE + "replaced to defaults: rows = 10 ; columns = 10");
            } finally {
                if(rows * columns < 70) {
                    System.out.println(Values.COLOR_RED + "rows * columns is less than 70" + Values.COLOR_WHITE);
                }
            }
        }
        while (rows * columns < 70);

        System.out.println(Values.COLOR_GREEN + "Size accepted" + Values.COLOR_WHITE);
        Values.MULTIPLIER = (rows*columns / 35 + 1) / 3;

        Wait();
        Game game = new Game(rows, columns);
    }

    // Function makes countdown until new game starts.
    public static void Wait() throws InterruptedException, IOException {
        // Countdown.
        System.out.println("Game starts in:");
        System.out.println("3");
        Thread.sleep(Values.SLEEP_TIME);
        System.out.println("2");
        Thread.sleep(Values.SLEEP_TIME);
        System.out.println("1");
        Thread.sleep(Values.SLEEP_TIME);

        // Clear console.
        Values.CLEAR_CONSOLE.start().waitFor();
    }
}
