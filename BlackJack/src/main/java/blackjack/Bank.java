package blackjack;

import blackjack.util.Constants;
import blackjack.util.InputOutput;

public class Bank {
    private int balance; // shows how much money bank has.
    private int diff; // at the end of the game keeps sum of all player's balances .

    public Bank() {
        this.balance = Integer.MAX_VALUE;
        this.diff = 0;
    }

    /**
     * Allows someone to get money from bank.
     */
    public int getMoney() {
        int amount = InputOutput.rnd.nextInt(Constants.GET_FROM_BANK_MIN,
                Constants.GET_FROM_BANK_MAX);
        balance = balance - amount;

        return amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
