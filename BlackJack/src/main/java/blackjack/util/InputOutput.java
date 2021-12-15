package blackjack.util;

import java.util.Random;

public class InputOutput {
    public static Random rnd = new Random();

    /**
     * Parsing string number to integer.
     */
    public static int strToInt(String strNum) {
        int ret;

        try {
            ret = Integer.parseInt(strNum);
        } catch (NumberFormatException exception) {
            System.out.println("can't parse number: " + strNum + "\n" +
                    "number value is set to default: 2");
            ret = 2;
        }

        return ret;
    }
}
