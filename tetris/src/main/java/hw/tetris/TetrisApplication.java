package hw.tetris;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;

public class TetrisApplication extends Application {

    private final int PRIMARY_BORDERPANE_WIDTH = 1300;
    private final int PRIMARY_BORDERPANE_HEIGHT = 800;

    public static final int PANE_SIDE_TILES = 9;
    public static final int TETRIS_PANE_SIDE_LEN = 630;

    public static final Color FREE_TILE_COLOR = Color.SNOW;
    public static final Color BUSY_TILE_COLOR = Color.LIGHTCORAL;
    public static final Color SIDE_COLOR = Color.LIGHTSTEELBLUE;

    private Stage primaryStage;
    private long timeSpent;
    public static int placed;

    private final HashMap<String, String> buttons = new HashMap<>();

    // Generating main container.
    private Parent createPrimaryView() {
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(generateMainPane());
        setSides(borderPane);

        borderPane.setPrefSize(PRIMARY_BORDERPANE_WIDTH, PRIMARY_BORDERPANE_HEIGHT);

        return borderPane;
    }

    // Setting up main area.
    private Pane generateMainPane() {
        Pane mainPane = new Pane();
        mainPane.setPrefSize(PRIMARY_BORDERPANE_WIDTH - 200, TETRIS_PANE_SIDE_LEN);

        Button genFigureBtn = new Button();
        genFigureBtn.setId("gen");
        genFigureBtn.setVisible(false);
        genFigureBtn.setText("Gen. Figure");
        genFigureBtn.setLayoutX(TETRIS_PANE_SIDE_LEN + 200);
        genFigureBtn.setLayoutY(TETRIS_PANE_SIDE_LEN - 100);
        genFigureBtn.setMaxSize(100, 50);
        genFigureBtn.setMinSize(100, 50);
        genFigureBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPane.getChildren().add(new Figure().getFigure());
                genFigureBtn.setDisable(true);
            }
        });

        setTiles(mainPane);
        mainPane.getChildren().add(genFigureBtn);
        buttons.put(genFigureBtn.getId(), "gen");

        return mainPane;
    }

    // Generating nef figure.
    private EventHandler<? super MouseEvent> onGenFigureButtonClick(Pane pane) {
        pane.getChildren().add(new Figure().getFigure());
        return null;
    }

    // Generating side frontend.
    private Pane generateSidePane(int width, int height, Color color) {
        Pane pane = new Pane();
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        pane.getChildren().add(rect);

        return pane;
    }

    // Setting up buttons and labels.
    private void configureTopPane(BorderPane borderPane) {
        Label time = new Label("time spent: 0");
        time.setFont(new Font(24));
        time.setLayoutX(250);
        time.setLayoutY(40);
        // Game length timer.
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ++timeSpent;
                time.setText("time spent: " + timeSpent / 65);
            }
        };

        placed = 1;
        Label score = new Label("score: 0");
        score.setFont(new Font(24));
        score.setLayoutX(500);
        score.setLayoutY(40);
        score.setId("score");

        // Start game button settings.
        Button startBtn = genTopBtn(true, "start", "start game", PRIMARY_BORDERPANE_WIDTH - 480);
        startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switchGenFigBtn((Pane) borderPane.getCenter(), true);

                // Hiding unnecessary elements.
                Pane topPane = (Pane) borderPane.getTop();
                for (var child : topPane.getChildren()) {
                    if (buttons.containsKey(child.getId())) {
                        if (buttons.get(child.getId()).equals("end")) {
                            child.setVisible(true);
                        }
                    }
                }

                // Starting timer.
                timeSpent = 0;
                animationTimer.start();

                startBtn.setVisible(false);
            }
        });

        // End game button settings.
        Button endBtn = genTopBtn(false, "end", "end game", PRIMARY_BORDERPANE_WIDTH - 290);
        endBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Ending game duties...
                Pane mainPane = (Pane) borderPane.getCenter();
                switchGenFigBtn(mainPane, false);
                freezeBoard(mainPane);

                // Showing up hidden elements after game has ended.
                Pane topPane = (Pane) borderPane.getTop();
                for (var child : topPane.getChildren()) {
                    if (buttons.containsKey(child.getId())) {
                        String id = buttons.get(child.getId());
                        if (id.equals("restart") || id.equals("exit")) {
                            child.setVisible(true);
                        }
                    }
                }

                animationTimer.stop();

                endBtn.setVisible(false);
            }
        });

        // Restart game button settings.
        Button reStartBtn = genTopBtn(false, "restart", "restart game", PRIMARY_BORDERPANE_WIDTH - 480);
        reStartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Refreshing game board.
                Pane mainPane = (Pane) borderPane.getCenter();
                switchGenFigBtn(mainPane, true);
                clearBoard(mainPane);

                // Showing hidden buttons.
                Pane topPane = (Pane) borderPane.getTop();
                for (var child : topPane.getChildren()) {
                    if (buttons.containsKey(child.getId())) {
                        if (buttons.get(child.getId()).equals("end")) {
                            child.setVisible(true);
                        } else {
                            child.setVisible(false);
                        }
                    }

                    // Enabling generation button.
                    if (child.getId() != null && child.getId().equals("gen")) {
                        Button genBtn = (Button) child;
                        genBtn.setDisable(false);
                    }

                    score.setText("score: 0");
                    placed = 1;
                }

                timeSpent = 0;
                animationTimer.start();

                reStartBtn.setVisible(false);
            }
        });

        // Exit from the game button settings.
        Button exitBtn = genTopBtn(false, "exit", "exit game", PRIMARY_BORDERPANE_WIDTH - 290);
        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

        Pane topPane = (Pane) borderPane.getTop();
        topPane.getChildren().addAll(startBtn, endBtn, reStartBtn, exitBtn, time, score);
        buttons.put(startBtn.getId(), "start");
        buttons.put(endBtn.getId(), "end");
        buttons.put(reStartBtn.getId(), "restart");
        buttons.put(exitBtn.getId(), "exit");
    }

    // Freezing all figures on the board.
    private void freezeBoard(Pane pane) {
        for (var child : pane.getChildren()) {
            if (child instanceof Path) {
                child.setDisable(true);
            }
        }
    }

    // Removing all figures from the board.
    private void clearBoard(Pane pane) {
        for (var child : pane.getChildren()) {
            if (child instanceof Path) {
                child.setVisible(false);
            }
        }
    }

    // Pattern of generating buttons (for cleaner code).
    private Button genTopBtn(boolean setVisible, String id, String name, int startX) {
        Button btn = new Button();
        btn.setId(id);
        btn.setVisible(setVisible);
        btn.setText(name);
        btn.setLayoutX(startX);
        btn.setLayoutY(25);
        btn.setMaxSize(100, 50);
        btn.setMinSize(100, 50);

        return btn;
    }

    // Detecting and switching visibility of gen. button.
    private void switchGenFigBtn(Pane pane, boolean enable) {
        for (var child : pane.getChildren()) {
            if (buttons.containsKey(child.getId())) {
                child.setVisible(enable);
            }
        }
    }

    // Generating and adding side frontend panels to main board.
    private void setSides(BorderPane borderPane) {
        Pane leftPane = generateSidePane(100, TETRIS_PANE_SIDE_LEN, SIDE_COLOR);
        borderPane.setLeft(leftPane);

        Pane ringtPane = generateSidePane(100, TETRIS_PANE_SIDE_LEN, SIDE_COLOR);
        borderPane.setRight(ringtPane);

        Pane topPane = generateSidePane(PRIMARY_BORDERPANE_WIDTH, 100, SIDE_COLOR);
        borderPane.setTop(topPane);
        configureTopPane(borderPane);

        Pane bottomPane = generateSidePane(PRIMARY_BORDERPANE_WIDTH, PRIMARY_BORDERPANE_HEIGHT - TETRIS_PANE_SIDE_LEN - 103, SIDE_COLOR);
        borderPane.setBottom(bottomPane);
    }

    // Drawing tiles (game board).
    private void setTiles(Pane pane) {
        int tileSize = TETRIS_PANE_SIDE_LEN / PANE_SIDE_TILES;

        for (int i = 0; i < PANE_SIDE_TILES; i++) {
            for (int j = 0; j < PANE_SIDE_TILES; j++) {
                Tile tile = new Tile(FREE_TILE_COLOR);
                tile.setTranslateX(tileSize * i);
                tile.setTranslateY(tileSize * j);

                pane.getChildren().add(tile);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createPrimaryView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}