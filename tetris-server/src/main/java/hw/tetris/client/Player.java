package hw.tetris.client;

import com.google.gson.annotations.JsonAdapter;

public class Player {
    public String name;
    public boolean isFinished;

    public Player(String name) {
        this.name = name;
        isFinished = false;
    }

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
