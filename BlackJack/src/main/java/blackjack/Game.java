package blackjack;

import blackjack.players.Honest;
import blackjack.players.Player;
import blackjack.players.Sharper;
import blackjack.util.Constants;
import blackjack.util.InputOutput;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bank bank; // Also is a lock.

    private final List<Thread> threads; // All players.

    private final List<Honest> honestPlayers;
    private final List<Sharper> cardsharpers;

    private boolean hasLeaks;

    public Game(int honestPlayers, int cardsharpers) {
        this.bank = new Bank();

        this.honestPlayers = new ArrayList<Honest>(honestPlayers);
        this.cardsharpers = new ArrayList<Sharper>(cardsharpers);
        this.threads = new ArrayList<Thread>(honestPlayers + cardsharpers);

        initPlayers(honestPlayers, cardsharpers);
        initThreads();
    }

    /**
     * Game start point.
     */
    public void start() {
        System.out.println("Game in process...");
        activateAllPlayers();

        try {
            Thread.sleep(Constants.GAME_TIME);
        } catch (InterruptedException e) {
            System.out.println("Can't continue game");
        } finally {
            stopAllPlayers();
            System.out.println("The game has been finished");
        }

        waitUntilEveryThreadEnds();
        getWinnerAndPrint();
    }

    /**
     * Initialize all players in appropriate lists.
     * @param honestAmount
     * @param sharpersAmount
     */
    private void initPlayers(int honestAmount, int sharpersAmount) {
        for (int i = 0; i < honestAmount; i++) {
            honestPlayers.add(new Honest(bank));
        }
        for (int i = 0; i < sharpersAmount; i++) {
            cardsharpers.add(new Sharper(bank, honestPlayers));
        }
    }

    /**
     * Initialize all players as threads.
     */
    private void initThreads() {
        for (var player : honestPlayers) {
            threads.add(new Thread(player));
        }
        for (var player : cardsharpers) {
            threads.add(new Thread(player));
        }
    }

    /**
     * All players who are threads start working.
     */
    private void activateAllPlayers() {
        for (var player : honestPlayers) {
            player.setActive(true);
        }
        for (var player : cardsharpers) {
            player.setActive(true);
        }
        for (var player : threads) {
            player.start();
        }
    }

    /**
     * All players who are treads stop working after they end their logic.
     */
    private void stopAllPlayers() {
        for (var player : honestPlayers) {
            player.setActive(false);
        }
        for (var player : cardsharpers) {
            player.setActive(false);
        }
    }

    /**
     * Print info about every player and fining winner.
     * Comparing amount that has been taken from bank and
     * compares to amount that all players have together.
     */
    private void getWinnerAndPrint() {
        Player winner = honestPlayers.get(0);
        int max = 0;

        for (var player : honestPlayers) {
            player.print();
            bank.setDiff(bank.getDiff() + player.getBalance());

            if(player.getBalance() > max) {
                max = player.getBalance();
                winner = player;
            }
        }
        for (var player : cardsharpers) {
            player.print();
            bank.setDiff(bank.getDiff() + player.getBalance());

            if(player.getBalance() > max) {
                max = player.getBalance();
                winner = player;
            }
        }

        System.out.println("WINNER:");
        winner.print();

        if(Integer.MAX_VALUE - bank.getBalance() == bank.getDiff()) {
            System.out.println("World haven't lost any points");
            hasLeaks = false;
        } else {
            System.out.println("ALARM! World have lost any points");
            hasLeaks = true;
        }

    }

    /**
     * Wait until each thread finishes working.
     */
    private void waitUntilEveryThreadEnds() {
        boolean marker = true;
        while (marker) {
            for (var thread : threads) {
                if(thread.isAlive()) {
                    marker = true;
                    break;
                }
                marker = false;
            }
        }
    }

    public boolean isHasLeaks() {
        return hasLeaks;
    }
}
