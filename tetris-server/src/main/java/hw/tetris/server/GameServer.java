package hw.tetris.server;

import hw.tetris.client.Player;
import hw.tetris.game.Figure;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import com.google.gson.*;
import hw.tetris.game.TetrisApplication;
import javafx.stage.Stage;

public class GameServer extends Thread {

    static class ConnectionHandler extends Thread {

        private final ServerSocket serverSocket;

        ConnectionHandler(ServerSocket serverSocket) {
            super("ConnectionHandler");
            this.serverSocket = serverSocket;
        }

        public void run() {
            try {
                while (true) {
                    Socket connected = serverSocket.accept();
                    new GameServer(connected).start();
                }
            } catch (Exception ex) {
                System.out.println(Thread.currentThread().getName() + ": got exception: " + ex);
            }
        }
    }

    private static final Set<GameServer> TEST_SERVERS = new HashSet<>();
    private final Socket connectedSocket;
    public static volatile boolean isOn = true;

    public static int[] figureSeeds = new int[112];

    GameServer(Socket connected) {
        super("TestServer: thread(" + connected.getPort() + ")");
        this.connectedSocket = connected;
        registerTestServer(this);
    }

    private static synchronized void registerTestServer(GameServer gameServer) {
        TEST_SERVERS.add(gameServer);
    }

    private static synchronized void shutdownTestServers() {
        Iterator<GameServer> iterator = TEST_SERVERS.iterator();
        while(iterator.hasNext()) {
            GameServer gameServer = iterator.next();
            iterator.remove();
            stopTestServer(gameServer);
        }
    }
    private static synchronized void stopTestServer(GameServer gameServer) {
        try {
            gameServer.interrupt();
            gameServer.connectedSocket.close();
        } catch (IOException ioException) {
            System.out.println(Thread.currentThread().getName() + ": get exception: " + ioException);
        }
    }

    public void run() {
        try {
            InputStream inputStream = connectedSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = connectedSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            while (!isInterrupted()) {
                String clientString = bufferedReader.readLine();
                if (clientString == null) {
                    break;
                }
                printWriter.println(clientString);
            }

            printWriter.close();
            bufferedReader.close();
            stopTestServer(this);
        } catch (IOException ioException) {
            System.out.println(Thread.currentThread().getName() + ": got exception: " + ioException);
        }
    }

    public static void startServer() {
        Random rnd = new Random();
        for (int i = 0; i < 112; i++) {
            figureSeeds[i] = rnd.nextInt(31);
        }
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            ConnectionHandler connectionHandler = new ConnectionHandler(serverSocket);
            connectionHandler.start();

            Scanner scanner = new Scanner(System.in);
            while (isOn) {}

            serverSocket.close();
            shutdownTestServers();
            try {
                connectionHandler.join();
                System.out.println("connectionHandler finished.");
            } catch (InterruptedException interruptedException) {
                System.out.println(Thread.currentThread().getName() + ": main() got exception: " + interruptedException);
            }
        } catch (IOException ioException) {
            System.out.println(Thread.currentThread().getName() + ": main() got exception: " + ioException);
        }
    }

    public static void main(String[] args) {
       GameServer.startServer();
    }
}
