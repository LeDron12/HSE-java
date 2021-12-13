package blackjack;

import blackjack.util.InputOutput;

public class Main {
    public static void main(String[] args) {
        int honestPlayers, cardsharpers;

        // Validating arguments.
        if (args.length == 0) {
            System.out.println("arguments not found\n" +
                    "set default value for honest: 2\n" +
                    "set default value for sharpers: 2");
            honestPlayers = 2;
            cardsharpers = 2;
        } else if (args.length == 1) {
            int amount = InputOutput.strToInt(args[0]);
            if(amount == 0) {
                System.out.println("common argument can't be 0\n" +
                        "set default value for honest: 2\n" +
                        "set default value for sharpers: 2");
                amount = 2;
            }

            honestPlayers = amount;
            cardsharpers = amount;
        } else {
            honestPlayers = InputOutput.strToInt(args[0]);
            cardsharpers = InputOutput.strToInt(args[1]);

            if(honestPlayers == 0) {
                honestPlayers = 1;
                System.out.println("Honest players amount must be > 0\n" +
                        "set default value for honest: 2");
            }
        }

        Game game = new Game(honestPlayers, cardsharpers);
        game.start();
    }
}
