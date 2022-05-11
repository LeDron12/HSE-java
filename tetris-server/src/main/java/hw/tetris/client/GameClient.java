package hw.tetris.client;

import com.google.gson.Gson;
import hw.tetris.game.TetrisApplication;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final PrintWriter printWriter;

    private TetrisApplication tetrisApplication;
    private Stage stage;

    public GameClient(String serverHost, int serverPort, TetrisApplication tetrisApplication) throws Exception {
        socket = new Socket(serverHost, serverPort);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        this.tetrisApplication = tetrisApplication;
        this.stage = stage;
    }

    public void run() throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        String s;

        while (tetrisApplication.isOn) {
            printWriter.println(tetrisApplication.getPlaced() - 1);
            Thread.sleep(1000);
        }

        printWriter.close();
        socket.close();
    }
}
