package hw.tetris.game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;

public class Figure {
    private final Shape figure;

    private final static Random rnd = new Random();
    // Figure types as arrays.
    private final static int[][] types = {{1, 1, 0, 1, 0, 0, 1, 0, 0}, {1, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1, 0, 1, 1, 0}, {1, 1, 1, 0, 0, 1, 0, 0, 0}, {1, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 1, 0, 0, 0}, {1, 0, 0, 1, 0, 0, 1, 1, 0}, {1, 1, 1, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 1, 0}, {0, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 1, 0, 1, 1, 0, 1, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 1, 1, 1, 1}, {1, 0, 0, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1, 0, 0, 1}, {0, 1, 0, 0, 1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1, 0, 0, 1, 0}, {1, 0, 0, 1, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 1, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 1, 0, 0, 1, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 0, 0, 0, 0}, {1, 1, 0, 0, 1, 0, 0, 0, 0}, {0, 1, 0, 1, 1, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, 0}, {1, 0, 0, 1, 1, 0, 1, 0, 0}, {1, 1, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 0, 1, 0}, {0, 1, 0, 1, 1, 1, 0, 0, 0}}; //new int[31][9];

    private static final int startXShift = TetrisApplication.TETRIS_PANE_SIDE_LEN + 150, startYShift = 115;

    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private int placed;

    public Figure(int placed, int ceed) {
        this(Figure.genRandFigure(ceed), placed);
    }

    public Figure(ArrayList<Shape> subFigures, int placed) {
        this.placed = placed;

        // Generating one complex figure.
        Shape shape = subFigures.get(0);
        for (var fig : subFigures) {
            shape = Shape.union(shape, fig);
        }

        shape.setFill(TetrisApplication.BUSY_TILE_COLOR);
        shape.setStroke(Color.BLACK);

        figure = shape;

        figure.setOnMousePressed(onMousePressedEventHandler);
        figure.setOnMouseDragged(onMouseDraggedEventHandler);
        figure.setOnMouseClicked(onMouseClickedEventHandler);
    }

    // Generating array of pieces to union them in one figure.
    private static ArrayList<Shape> genRandFigure(int ceed) {
        ArrayList<Shape> figures = new ArrayList<>();

        var type = types[ceed];
        int cellSideLen = (TetrisApplication.TETRIS_PANE_SIDE_LEN / TetrisApplication.PANE_SIDE_TILES);
        for (int i = 0; i < type.length; i++) {
            if (type[i] == 1) {
                Rectangle rect = new Rectangle(cellSideLen, cellSideLen);

                rect.setFill(TetrisApplication.BUSY_TILE_COLOR);
                rect.setStroke(Color.BLACK);

                // Setting coords of piece.
                rect.setLayoutX(startXShift + (i % 3) * cellSideLen);
                rect.setLayoutY(startYShift + (i / 3) * cellSideLen);

                figures.add(rect);
            }
        }

        return figures;
    }

    // Detecting start position of cursor.
    EventHandler<MouseEvent> onMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();
                    orgTranslateX = ((Shape) (event.getSource())).getTranslateX();
                    orgTranslateY = ((Shape) (event.getSource())).getTranslateY();

                    event.consume();
                }
            };

    // Dragging figure.
    EventHandler<MouseEvent> onMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Shape) (event.getSource())).setTranslateX(newTranslateX);
                    ((Shape) (event.getSource())).setTranslateY(newTranslateY);

                    event.consume();
                }
            };

    // Placing figure on board.
    EventHandler<MouseEvent> onMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Shape shape = (Shape) event.getSource();

                    double currX = shape.getTranslateX() + startXShift;
                    double currY = shape.getTranslateY() + startYShift;

                    // returns -100 if figure is dragged not that close to any cell
                    double toNearestX = getNearestCell(currX);
                    double toNearestY = getNearestCell(currY);

                    if (toNearestX != -100 && toNearestY != -100) {
                        // Finding bounds of our figure.
                        double leftXbound = shape.getBoundsInParent().getMinX();
                        double rightXbound = shape.getBoundsInParent().getMaxX();
                        double botYbound = shape.getBoundsInParent().getMinY();
                        double topYbound = shape.getBoundsInParent().getMaxY();

                        // Placing figure if it is situated correctly.
                        if (-12 < leftXbound && TetrisApplication.TETRIS_PANE_SIDE_LEN + 12 > rightXbound &&
                                -12 < botYbound && TetrisApplication.TETRIS_PANE_SIDE_LEN + 12 > topYbound) {
                            shape.setTranslateX(shape.getTranslateX() - toNearestX);
                            shape.setTranslateY(shape.getTranslateY() - toNearestY);

                            placeFigure(shape);
                        }
                    }

                    event.consume();
                }
            };

    // Placing figure
    private void placeFigure(Shape shape) {
        shape.setDisable(true);

        // Enabling gen button.
        Pane mainPane = (Pane) shape.getParent();
        for (var child : mainPane.getChildren()) {
            if (child.getId() != null && child.getId().equals("gen")) {
                Button genBtn = (Button) child;
                genBtn.setDisable(false);
            }
        }

        // Increasing the score.
        Pane topPane = (Pane) (((BorderPane) (shape.getParent().getParent())).getTop());
        for(var child : topPane.getChildren()) {
            if (child.getId() != null && child.getId().equals("score")) {
                Label score = (Label) child;
                score.setText("score: " + placed);
            }
        }
    }

    // Finding nearest cell coord.
    private double getNearestCell(double coord) {
        for (int i = 0; i < TetrisApplication.TETRIS_PANE_SIDE_LEN;
             i += TetrisApplication.TETRIS_PANE_SIDE_LEN / TetrisApplication.PANE_SIDE_TILES) {
            if (Math.abs(coord - i) <= 10) {
                return coord - i;
            }
        }
        return -100;
    }

    public Shape getFigure() {
        return figure;
    }
}
