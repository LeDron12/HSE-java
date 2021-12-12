package blackjack.players;

import blackjack.Bank;
import blackjack.util.Constants;
import blackjack.util.InputOutput;

public class Player implements Runnable {
    private final Bank bank;
    private boolean active;

    private String name;
    private String surname;

    private int balance;

    public Player(Bank bank) {
        this.bank = bank;
        this.active = false;

        name = Constants.playersNames.get(InputOutput.rnd.nextInt(Constants.playersNames.size())).toString();
        surname = Constants.playersSurnames.get(InputOutput.rnd.nextInt(Constants.playersSurnames.size())).toString();

        this.balance = 0;
    }

    @Override
    public void run() {
        int sleepTime;

        while (this.isActive()) {
            getPoints();

            sleepTime = InputOutput.rnd.nextInt(Constants.GET_FROM_BANK_SLEEP_TIME_MIN,
                    Constants.GET_FROM_BANK_SLEEP_TIME_MAX + 1);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getPoints() {
        synchronized (bank) {
            int getAmount = bank.getMoney();
            balance = balance + getAmount;
            System.out.println(name + " " + surname + " getting from bank: " + getAmount);
        }
    }

    public void print() {
        System.out.println("???");
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBalance() {
        return balance;
    }

    public void addToBalance(int amount) {
        this.balance = balance + amount;
    }

    public Bank getBank() {
        return this.bank;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
