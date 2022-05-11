package hw.tetris.server;

import hw.tetris.client.GameClient;
import hw.tetris.game.Figure;
import hw.tetris.game.TetrisApplication;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServerApplication extends Application {

    private Thread serverThread;

    private Stage primaryStage;
    private int playersAmount;
    List<Thread> gameClients = new ArrayList();
    List<TetrisApplication> apps = new ArrayList<>();

    // Generating main container.
    private Parent createPrimaryView() {
        Pane mainPane = new Pane();
        mainPane.setPrefSize(350, 150);

        Label playersLabel = new Label();
        playersLabel.setText("Выберите кол-во игроков:");
        playersLabel.setLayoutX(105);
        playersLabel.setLayoutY(40);

        Label nameLabel = new Label();
        nameLabel.setText("Введите имя игрока 1:");
        nameLabel.setVisible(false);
        nameLabel.setLayoutX(110);
        nameLabel.setLayoutY(20);

        TextField nameField = new TextField();
        nameField.setVisible(false);
        nameField.setLayoutX(100);
        nameField.setLayoutY(40);
        nameField.setMaxHeight(25);
        nameField.setMinHeight(25);
        nameField.setMaxWidth(150);
        nameField.setMinWidth(150);

        Button joinBtn = new Button();
        Button oneBtn = new Button();
        Button twoBtn = new Button();
        Button restartBtn = new Button();
        Button stopServerBtn = new Button();
        Button exitBtn = new Button();

        joinBtn.setId("join");
        joinBtn.setVisible(false);
        joinBtn.setText("Join the Game");
        joinBtn.setLayoutX(125);
        joinBtn.setLayoutY(75);
        joinBtn.setMaxSize(100, 50);
        joinBtn.setMinSize(100, 50);
        joinBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (nameField.getCharacters().isEmpty()) {
                    return;
                }
                nameLabel.setText("Введите имя игрока 2:");

                TetrisApplication tetrisApplication = new TetrisApplication();
                tetrisApplication.setPlayerName(nameField.getText());
                nameField.clear();

                Stage playerStage = new Stage();
                playerStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        for (int i = 0; i < 2; i++) {
                            if (apps.get(i) == tetrisApplication) {
                                gameClients.get(i).interrupt();
                                gameClients.remove(i);
                                apps.remove(i);
                                break;
                            }
                        }
                    }
                });

                try {
                    tetrisApplication.start(playerStage);
                    apps.add(tetrisApplication);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Thread gc = new Thread(() -> {
                    try {
                        GameClient gameClient = new GameClient("localhost", 5000, tetrisApplication);
                        gameClient.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                gc.start();
                gameClients.add(gc);

                if (gameClients.size() == playersAmount) {
                    if (apps.size() > 1) {
                        apps.get(0).setOpponentName(apps.get(1).getPlayerName());
                        apps.get(1).setOpponentName(apps.get(0).getPlayerName());
                    }
                    for (var app : apps) {
                        app.setPrimaryView();
                    }
                    nameLabel.setVisible(false);
                    nameField.setVisible(false);
                    joinBtn.setVisible(false);
                    restartBtn.setVisible(true);
                    stopServerBtn.setVisible(true);
                }
            }
        });

        oneBtn.setId("one");
        oneBtn.setText("1");
        oneBtn.setLayoutX(50);
        oneBtn.setLayoutY(75);
        oneBtn.setMaxSize(100, 50);
        oneBtn.setMinSize(100, 50);
        oneBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playersAmount = 1;
                playersLabel.setVisible(false);
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                joinBtn.setVisible(true);
                oneBtn.setVisible(false);
                twoBtn.setVisible(false);
            }
        });

        twoBtn.setId("two");
        twoBtn.setText("2");
        twoBtn.setLayoutX(200);
        twoBtn.setLayoutY(75);
        twoBtn.setMaxSize(100, 50);
        twoBtn.setMinSize(100, 50);
        twoBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playersAmount = 2;
                playersLabel.setVisible(false);
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                joinBtn.setVisible(true);
                twoBtn.setVisible(false);
                oneBtn.setVisible(false);
            }
        });

        restartBtn.setId("restart");
        restartBtn.setText("restart\ngame");
        restartBtn.setVisible(false);
        restartBtn.setLayoutX(50);
        restartBtn.setLayoutY(75);
        restartBtn.setMaxSize(100, 50);
        restartBtn.setMinSize(100, 50);
        restartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitBtn.setVisible(false);
                stopServerBtn.setVisible(false);
                restartBtn.setVisible(false);
                playersLabel.setVisible(true);
                oneBtn.setVisible(true);
                twoBtn.setVisible(true);

                nameLabel.setText("Введите имя игрока 1:");
                endGames();
                while (serverThread.isAlive()) {
                    // wait until server turns off.
                }
                startServer();
                // TODO: stop all instances of players.
            }
        });

        stopServerBtn.setId("stop");
        stopServerBtn.setText("stop\nserver");
        stopServerBtn.setVisible(false);
        stopServerBtn.setLayoutX(200);
        stopServerBtn.setLayoutY(75);
        stopServerBtn.setMaxSize(100, 50);
        stopServerBtn.setMinSize(100, 50);
        stopServerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopServerBtn.setVisible(false);
//                restartBtn.setVisible(false);
//                playersLabel.setVisible(true);
                joinBtn.setVisible(false);
                exitBtn.setVisible(true);
//                twoBtn.setVisible(true);
//                oneBtn.setVisible(true);

                endGames();
            }
        });

        exitBtn.setId("exit");
        exitBtn.setText("exit from\n the game");
        exitBtn.setVisible(false);
        exitBtn.setLayoutX(200);
        exitBtn.setLayoutY(75);
        exitBtn.setMaxSize(100, 50);
        exitBtn.setMinSize(100, 50);
        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopServer();
                primaryStage.close();
            }
        });

        mainPane.getChildren().addAll(joinBtn, oneBtn, twoBtn, playersLabel,
                restartBtn, stopServerBtn, exitBtn, nameField, nameLabel);
        return mainPane;
    }

    private void endGames() {
        try {
            for (var app : apps) {
                app.endGame();
                app.stop();
            }
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameClients.clear();
        apps.clear();

        stopServer();
    }

    private void stopServer() {
        for (var client : gameClients) {
            client.interrupt();
        }
        GameServer.isOn = false;
    }

    private void startServer() {
        GameServer.isOn = true;
        serverThread = new Thread(GameServer::startServer);
        serverThread.start();
    }

    public void start(Stage primaryStage) throws IOException {
        startServer();

        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createPrimaryView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
