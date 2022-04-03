package hw.tetris;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    public Tile(Color color) {
        int rectSize = TetrisApplication.TETRIS_PANE_SIDE_LEN / TetrisApplication.PANE_SIDE_TILES;
        Rectangle rectangle = new Rectangle(rectSize, rectSize);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(rectangle);
    }
}
