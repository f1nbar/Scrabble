package scrabble;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class UIFX {
    private double width = 50;
    private double height = 50;
    private Board board;
    private Player playerOne;
    private Player playerTwo;

    public UIFX(Board board, Player playerOne, Player playerTwo) {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    // empty tile methods
    private Rectangle emptyTile(int row, int column) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(getTileColor(row, column));
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    private StackPane letterTile(Tile tile) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.PERU);
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);

        HBox tileTextValue = tileText(tile);

        StackPane placedTileOnBoard = new StackPane();
        placedTileOnBoard.getChildren().addAll(rectangle, tileTextValue);
        return placedTileOnBoard;
    }

    private HBox tileText(Tile tile) {
        Text tileChar = new Text("  " + tile.getLetter());
        Text points = new Text(String.valueOf(tile.getScore()));
        Font font = Font.font("Ariel", FontWeight.SEMI_BOLD, height / 1.6);
        tileChar.setFont(font);
        points.setTranslateY(tileChar.getFont().getSize() * 1);

        HBox tileTextValue = new HBox();
        tileTextValue.getChildren().addAll(tileChar, points);
        return tileTextValue;
    }

    private Color getTileColor(int row, int column) {
        String position = Board.concatInt(row, column);
        if (board.getSquareValue(position) != null) {
            switch (board.getSquareValue(position)) {
                case "TW":
                    return Color.PINK;
                case "TL":
                    return Color.SALMON;
                case "DW":
                    return Color.BLUE;
                case "DL":
                    return Color.LIGHTBLUE;
                default:
                    return Color.RED;
            }
        } else {
            return Color.BURLYWOOD;
        }
    }

    public GridPane boardDisplay() {
        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(755, 755);

        for (int x = 0; x < board.BOARD_SIZE; x++) {
            for (int y = 0; y < board.BOARD_SIZE; y++) {
                if (board.getBoard()[x][y] != null) {
                    Tile tile = board.getBoard()[x][y];
                    StackPane tileDisplay = letterTile(tile);
                    gameBoard.add(tileDisplay, y, x);
                } else {
                    Rectangle tileDisplay = emptyTile(x, y);
                    gameBoard.add(new StackPane(tileDisplay), y, x);
                }
            }
        }
        return gameBoard;
    }

    public HBox frameDisplay() {
        HBox frameBox = new HBox();
        Frame playerFrame;
        if (playerOne.getTurn()) {
            playerFrame = playerOne.getFrame();
        } else {
            playerFrame = playerTwo.getFrame();
        }
        for (Tile tile : playerFrame.getFrame()) {
            StackPane tileObject = letterTile(tile);
            frameBox.getChildren().add(tileObject);
        }
        return frameBox;
    }
}