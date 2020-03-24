package scrabble;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UIFX implements Initializable {
    private double width = 50;
    private double height = 50;
    private ObservableList<Tile[]> observableBoardArray;
    @FXML
    public GridPane boardDisplay;

    public VBox frameDisplay;

    private Scrabble game;
    private Scanner input;
    private Rectangle blank;
    private boolean playersInitialized;

    public UIFX() {
        blank = new Rectangle(width, height);
        blank.setFill(Color.WHITE);
        blank.setStrokeWidth(4);
        blank.setStroke(Color.WHITE);
        playersInitialized = false;

        input = new Scanner(System.in);
        game = new Scrabble(input);
        this.boardDisplay = makeBoardDisplay();
        this.frameDisplay = makeFrameDisplay();
    }

    public GridPane getBoardDisplay() {
        return this.boardDisplay;
    }

    public VBox getFrameDisplay() {
        return this.frameDisplay;
    }

    // empty tile methods
    @FXML
    private Rectangle emptyTile(int row, int column) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(getTileColor(row, column));
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    @FXML
    private StackPane letterTile(Tile tile) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.PERU);
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);

        HBox tileTextValue = makeTileText(tile);

        StackPane placedTileOnBoard = new StackPane();
        placedTileOnBoard.getChildren().addAll(rectangle, tileTextValue);
        return placedTileOnBoard;
    }

    @FXML
    private HBox makeTileText(Tile tile) {
        Text tileChar = new Text("  " + tile.getLetter());
        Text points = new Text(String.valueOf(tile.getScore()));
        Font font = Font.font("Ariel", FontWeight.SEMI_BOLD, height / 1.6);
        tileChar.setFont(font);
        points.setTranslateY(tileChar.getFont().getSize() * 1);

        HBox tileTextValue = new HBox();
        tileTextValue.getChildren().addAll(tileChar, points);
        return tileTextValue;
    }

    @FXML
    private Color getTileColor(int row, int column) {
        String position = Board.concatInt(row, column);
        if (game.getBoard().getSquareValue(position) != null) {
            switch (game.getBoard().getSquareValue(position)) {
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

    @FXML
    public GridPane makeBoardDisplay() {
        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(755, 755);
        for (int x = 0; x <= game.getBoard().BOARD_SIZE; x++) {
            for (int y = 0; y <= game.getBoard().BOARD_SIZE; y++) {
                if (x == 0 && y == 0) {
                    Rectangle blank = new Rectangle(width, height);
                    blank.setFill(Color.WHITE);
                    blank.setStrokeWidth(4);
                    blank.setStroke(Color.WHITE);
                    gameBoard.add(blank, y, x);
                } else if (x == 0) {
                    Text guide = new Text(y + "");
                    StackPane placedTileOnBoard = new StackPane();
                    placedTileOnBoard.getChildren().addAll(blank, guide);
                    gameBoard.add(placedTileOnBoard, y, x);
                } else if (y == 0) {
                    Rectangle blank = new Rectangle(width, height);
                    blank.setFill(Color.WHITE);
                    blank.setStrokeWidth(4);
                    blank.setStroke(Color.WHITE);
                    Text guide = new Text(Character.toString((char) (64 + x)));
                    StackPane placedTileOnBoard = new StackPane();
                    placedTileOnBoard.getChildren().addAll(blank, guide);
                    gameBoard.add(placedTileOnBoard, y, x);
                } else if (game.getBoard().getBoard()[x - 1][y - 1] != null) {
                    Tile tile = game.getBoard().getBoard()[x - 1][y - 1];
                    StackPane tileDisplay = letterTile(tile);

                    gameBoard.add(tileDisplay, y, x);
                } else {
                    Rectangle tileDisplay = emptyTile(x - 1, y - 1);
                    gameBoard.add(new StackPane(tileDisplay), y, x);
                }
            }
        }
        return gameBoard;

    }

    public VBox makeFrameDisplay() {
        HBox frameBox = new HBox();
        VBox frame = new VBox();
        Text frameTitle;
        Frame playerFrame;
        if (playersInitialized) {
            if (!game.getPlayerOne().getTurn()) {
                playerFrame = game.getPlayerOne().getFrame();
                frameTitle = new Text(game.getPlayerOne().getName() + "'s frame:");
            } else {
                playerFrame = game.getPlayerOne().getFrame();
                frameTitle = new Text(game.getPlayerTwo().getName() + "'s frame:");
            }
            frameBox.getChildren().clear();
            for (Tile tile : playerFrame.getFrame()) {
                StackPane tileObject = letterTile(tile);
                frameBox.getChildren().add(tileObject);
            }
        } else {
            for (int i = 0; i < Frame.FRAME_SIZE; i++) {
                Rectangle emptyFramePlaceholder = new Rectangle(width * 7, height);
                emptyFramePlaceholder.setFill(Color.BLACK);
                frameBox.getChildren().add(emptyFramePlaceholder);
            }
            frameTitle = new Text("Frame:");
        }
        frame.getChildren().addAll(frameTitle, frameBox);
        return frame;
    }


    public void processCLI() {
        if(!playersInitialized) {
            game.setPlayerOne(game.initialisePlayer(game.getPool(), this.input));
            game.setPlayerTwo(game.initialisePlayer(game.getPool(), this.input));
            this.playersInitialized = true;
        }
        System.out.print("Enter command: ");
        String commandInput = input.next().trim().toUpperCase();
        switch (commandInput) {
            case "QUIT":
                game.setIsOver(true);
                System.out.println("Quitting...");
                break;
            default:
                if (game.getPlayerOne().getTurn()) {
                    game.playerTurn(game.getPlayerOne(), game.getBoard(), input, game.getPool());
                    game.getPlayerOne().setTurn(false);
                } else {
                    game.playerTurn(game.getPlayerTwo(), game.getBoard(), input, game.getPool());
                    game.getPlayerOne().setTurn(true);
                }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.boardDisplay = makeBoardDisplay();
    }
}