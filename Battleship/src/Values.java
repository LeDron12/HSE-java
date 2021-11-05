public final class Values {
    // Colors.
    public static final String COLOR_WHITE = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_CYAN = "\u001B[36m";

    // Sleep time.
    public static final long SLEEP_TIME = 1000;

    // Multiplier for ships amount calculation.
    public static int MULTIPLIER;

    // Ship's length.
    public static final int CARRIER_LEN = 5;
    public static final int BATTLESHIP_LEN = 4;
    public static final int CRUISER_LEN = 3;
    public static final int DESTROYER_LEN = 2;
    public static final int SUBMARINE_LEN = 1;

    // Clearing console variable to execute methods .start().waitFor() on.
    public static ProcessBuilder CLEAR_CONSOLE;

    static {
        try {
            // Choosing command depending on OS.
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                CLEAR_CONSOLE = new ProcessBuilder("cmd", "/c", "cls").inheritIO();
            } else {
                CLEAR_CONSOLE = new ProcessBuilder("cmd", "/c", "clear").inheritIO();
            }
        } catch (Exception e) {
            System.out.println(COLOR_RED + "cant clear console" + COLOR_WHITE);
        }
    }

}
