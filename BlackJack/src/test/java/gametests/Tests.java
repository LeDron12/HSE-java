package gametests;

import blackjack.Bank;
import blackjack.Game;
import blackjack.players.Honest;
import blackjack.players.Player;
import blackjack.players.Sharper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Tests {

    @Test
    public void checkPrint() {
        Player player = new Player(new Bank());
        Honest honest = new Honest(new Bank());
        Sharper sharper = new Sharper(new Bank(), new ArrayList<Honest>());
        player.print();
        honest.print();
        sharper.print();
    }

    @Test
    public void testGettinPoints() {
        Bank bank = new Bank();
        Honest honest = new Honest(bank);
        honest.getPoints();
        Assertions.assertEquals(honest.getBalance(), Integer.MAX_VALUE - bank.getBalance());
    }

    @Test
    public void checkBankGetMoney() {
        Bank bank = new Bank();
        var x = bank.getMoney();
        Assertions.assertEquals(x, Integer.MAX_VALUE - bank.getBalance());
    }

    @Test
    public void testGameStartFiveTen() {
        Game game = new Game(5, 10);
        game.start();
        Assertions.assertEquals(game.isHasLeaks(), false);
    }

    @Test
    public void testGameStartEightThree() {
        Game game = new Game(8, 3);
        game.start();
        Assertions.assertEquals(game.isHasLeaks(), false);
    }

    @Test
    public void testGameStartOneTwo() {
        Game game = new Game(1, 2);
        game.start();
        Assertions.assertEquals(game.isHasLeaks(), false);
    }
}
