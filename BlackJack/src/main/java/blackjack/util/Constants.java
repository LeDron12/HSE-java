package blackjack.util;

import blackjack.players.PlayersNames;
import blackjack.players.PlayersSurnames;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final int GAME_TIME = 10000;

    public static final List<PlayersNames> playersNames =  Collections.unmodifiableList(Arrays.asList(PlayersNames.values()));
    public static final List<PlayersSurnames> playersSurnames =  Collections.unmodifiableList(Arrays.asList(PlayersSurnames.values()));

    public static final double STEAL_PROBABILITY = 0.4;

    public static final int GET_FROM_BANK_MIN = 1;
    public static final int GET_FROM_BANK_MAX = 10;

    public static final int GET_FROM_BANK_SLEEP_TIME_MIN = 100;
    public static final int GET_FROM_BANK_SLEEP_TIME_MAX = 200;

    public static final int STEAL_AMOUNT_MIN = 0;
    public static final int STEAL_AMOUNT_MAX = 8;

    public static final int STEAL_SLEEP_TIME_MIN = 180;
    public static final int STEAL_SLEEP_TIME_MAX = 300;
}
