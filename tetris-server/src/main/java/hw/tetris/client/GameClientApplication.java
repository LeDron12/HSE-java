package hw.tetris.client;

import hw.tetris.game.TetrisApplication;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameClientApplication extends Application {

//    private TetrisApplication tetrisApplication;
    private Stage primaryStage;

    private Parent createPrimaryView() {
        Pane mainPane = new Pane();
        mainPane.setPrefSize(200, 100);

        return mainPane;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createPrimaryView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
