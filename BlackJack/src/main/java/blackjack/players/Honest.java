package blackjack.players;

import blackjack.Bank;

public class Honest extends Player{
    public Honest(Bank bank) {
        super(bank);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void print() {
        System.out.println("Type: Honest\n" +
                "Name: " + getName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Points: " + getBalance() + "\n");
    }
}
