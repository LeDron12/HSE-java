package blackjack.players;

import blackjack.Bank;
import blackjack.util.InputOutput;
import blackjack.util.Constants;

import java.util.List;

public class Sharper extends Player {
    private List<Honest> honestPlayers;

    public Sharper(Bank bank, List<Honest> honestPlayers) {
        super(bank);
        this.honestPlayers = honestPlayers;
    }

    @Override
    public void run() {
        int sleepTime;

        while (this.isActive()) {
            if (InputOutput.rnd.nextDouble() < Constants.STEAL_PROBABILITY) {
                stealPoints();

                sleepTime = InputOutput.rnd.nextInt(Constants.STEAL_SLEEP_TIME_MIN,
                        Constants.STEAL_SLEEP_TIME_MAX + 1);
            } else {
                super.getPoints();

                sleepTime = InputOutput.rnd.nextInt(Constants.GET_FROM_BANK_SLEEP_TIME_MIN,
                        Constants.GET_FROM_BANK_SLEEP_TIME_MAX + 1);
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Sharper: " + getSurname() + " " + getName() + " can't fall asleep");
                e.printStackTrace();
            }
        }
    }

    /**
     * Sharper steals points from random Honest.
     */
    private void stealPoints() {
        Honest honest = getRandomHonest();

        synchronized (getBank()) {
            int amount = InputOutput.rnd.nextInt(Constants.STEAL_AMOUNT_MIN,
                    Constants.STEAL_AMOUNT_MAX + 1);

            if (amount > honest.getBalance()) {
                amount = honest.getBalance();
            }
            this.addToBalance(amount);
            honest.addToBalance(-amount);
        }
    }

    private Honest getRandomHonest() {
        int position = InputOutput.rnd.nextInt(honestPlayers.size());
        return honestPlayers.get(position);
    }

    @Override
    public void print() {
        System.out.println("Type: Sharper\n" +
                "Name: " + getName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Points: " + getBalance() + "\n");
    }
}
