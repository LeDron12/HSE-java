package blackjack;

import blackjack.players.Honest;
import blackjack.players.Player;
import blackjack.players.Sharper;
import blackjack.util.Constants;
import blackjack.util.InputOutput;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Bank bank; // also is lock

    private final List<Thread> threads;

    private final List<Honest> honestPlayers;
    private final List<Sharper> cardsharpers;

    public Game(int honestPlayers, int cardsharpers) {
        this.bank = new Bank();

        this.honestPlayers = new ArrayList<Honest>(honestPlayers);
        this.cardsharpers = new ArrayList<Sharper>(cardsharpers);
        this.threads = new ArrayList<Thread>(honestPlayers + cardsharpers);
        initPlayers(honestPlayers, cardsharpers);
        initThreads();
    }

    public void start() {
        activateAllPlayers();

        try {
//            Thread.sleep(Constants.GAME_TIME);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Can't continue game");
        } finally {
            stopAllPlayers();
            System.out.println("The game has been finished");
        }

        waitUntilEveryThreadEnds();
        getWinnerAndPrint();
    }

    private void initPlayers(int honestAmount, int sharpersAmount) {
        for (int i = 0; i < honestAmount; i++) {
            honestPlayers.add(new Honest(bank));
        }
        for (int i = 0; i < sharpersAmount; i++) {
            cardsharpers.add(new Sharper(bank, honestPlayers));
        }
    }

    private void initThreads() {
        for (var player : honestPlayers) {
            threads.add(new Thread(player));
        }
        for (var player : cardsharpers) {
            threads.add(new Thread(player));
        }
    }

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

    public void stopAllPlayers() {
        for (var player : honestPlayers) {
            player.setActive(false);
        }
        for (var player : cardsharpers) {
            player.setActive(false);
        }
    }

    private void getWinnerAndPrint() {
        Player winner = null;
        int max = 0;

        for (var player : honestPlayers) {
            player.print();
            if(player.getBalance() > max) {
                max = player.getBalance();
                winner = player;
            }
        }
        for (var player : cardsharpers) {
            player.print();
            if(player.getBalance() > max) {
                max = player.getBalance();
                winner = player;
            }
        }

        System.out.println("WINNER:");
        // can't be null
        winner.print();
    }

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
}
