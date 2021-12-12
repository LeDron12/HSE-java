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
            var x = InputOutput.rnd.nextDouble();
            if (x < Constants.STEAL_PROBABILITY) {
                stealPoints();

                sleepTime = InputOutput.rnd.nextInt(Constants.STEAL_SLEEP_TIME_MIN,
                        Constants.STEAL_SLEEP_TIME_MAX + 1);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                super.run();
            }
        }
    }

    private void stealPoints() {
        Honest honest = getRandomHonest();

        synchronized (getBank()) {
            int amount = InputOutput.rnd.nextInt(Constants.STEAL_AMOUNT_MIN,
                    Constants.STEAL_AMOUNT_MAX + 1);

            if (amount > honest.getBalance()) {
                amount = honest.getBalance();
                this.addToBalance(amount);
                honest.addToBalance(-amount);
                System.out.println(getName() + " " + getSurname() + " sTEALING " + amount + " from: " + honest.getName() + " " + honest.getSurname());
            } else {
                this.addToBalance(amount);
                honest.addToBalance(-amount);
                System.out.println(getName() + " " + getSurname() + " STEALINg " + amount + " from: " + honest.getName() + " " + honest.getSurname());
            }
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
