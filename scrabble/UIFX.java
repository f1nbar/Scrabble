package scrabble;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.Scanner;

public class UIFX {
    private double width = 30;
    private double height = 30;

    private Scrabble game;
    private Scanner input;
    private Rectangle blank;
    private Font font;

    public UIFX() {
        blank = new Rectangle(width, height);
        blank.setFill(Color.ANTIQUEWHITE);
        blank.setStrokeWidth(4);
        blank.setStroke(Color.ANTIQUEWHITE);

        font = Font.font("Ariel", FontWeight.BOLD, height / 1.6);

        input = new Scanner(System.in);
        game = new Scrabble(input);
    }

    // empty tile methods
    private Rectangle emptyTile(int row, int column) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(getTileColor(row, column));
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    private StackPane frameTile(Tile tile) {
        Rectangle rectangle = new Rectangle(width*2, height*2);
        rectangle.setFill(Color.PERU);
        rectangle.setStrokeWidth(3.9);
        rectangle.setStroke(Color.BLACK);

        HBox tileTextValue = makeFrameTileText(tile);

        StackPane placedTileOnBoard = new StackPane();
        placedTileOnBoard.getChildren().addAll(rectangle, tileTextValue);
        return placedTileOnBoard;
    }

    private StackPane boardTile(Tile tile) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.PERU);
        rectangle.setStrokeWidth(4);
        rectangle.setStroke(Color.BLACK);

        Text tileChar = new Text("" + tile.getLetter());
        Font semiFont = Font.font("Ariel", FontWeight.BOLD, height / 1.5);
        tileChar.setFont(semiFont);
        tileChar.setTextAlignment(TextAlignment.CENTER);
        StackPane placedTileOnBoard = new StackPane();
        placedTileOnBoard.getChildren().addAll(rectangle, tileChar);
        return placedTileOnBoard;
    }

    private HBox makeFrameTileText(Tile tile) {
        Text tileChar = new Text("" + tile.getLetter());
        Text points = new Text(String.valueOf(tile.getScore()));
        Font semiFont = Font.font("Ariel", FontWeight.SEMI_BOLD, height);
        tileChar.setFont(semiFont);
        points.setFont(semiFont);
        points.setFont(Font.font("Ariel", FontWeight.SEMI_BOLD, height / 2));
        points.setTranslateY(tileChar.getFont().getSize() / 1.5);
        points.setTextAlignment(TextAlignment.RIGHT);

        HBox tileTextValue = new HBox();
        tileTextValue.setAlignment(Pos.CENTER);
        tileTextValue.getChildren().addAll(tileChar, points);
        return tileTextValue;
    }

    private Color getTileColor(int row, int column) {
        String position = Board.concatInt(row, column);
        if (game.getBoard().getSquareValue(position) != null) {
            switch (game.getBoard().getSquareValue(position)) {
                case "TW":
                    return Color.PINK;
                case "TL":
                    return Color.SALMON;
                case "DW":
                    return Color.CORNFLOWERBLUE;
                case "DL":
                    return Color.LIGHTBLUE;
                default:
                    return Color.RED;
            }
        } else {
            return Color.BURLYWOOD;
        }
    }

    public GridPane makeBoardDisplay() {
        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(755, 755);
        for (int x = 0; x <= game.getBoard().BOARD_SIZE; x++) {
            for (int y = 0; y <= game.getBoard().BOARD_SIZE; y++) {
                if (x == 0 && y == 0) {
                    gameBoard.add(blank, y, x);
                } else if (x == 0) {
                    Text guide = new Text(y + "");
                    StackPane placedTileOnBoard = new StackPane();
                    placedTileOnBoard.getChildren().addAll(blank, guide);
                    gameBoard.add(placedTileOnBoard, y, x);
                } else if (y == 0) {
                    Text guide = new Text(Character.toString((char) (64 + x)));
                    StackPane placedTileOnBoard = new StackPane();
                    placedTileOnBoard.getChildren().addAll(blank, guide);
                    gameBoard.add(placedTileOnBoard, y, x);
                } else if (game.getBoard().getBoard()[x - 1][y - 1] != null) {
                    Tile tile = game.getBoard().getBoard()[x - 1][y - 1];
                    StackPane tileDisplay = boardTile(tile);

                    gameBoard.add(tileDisplay, y, x);
                } else {
                    Rectangle tileDisplay = emptyTile(x - 1, y - 1);
                    gameBoard.add(new StackPane(tileDisplay), y, x);
                }
            }
        }
        gameBoard.setAlignment(Pos.CENTER);
        return gameBoard;

    }

    public HBox makeScoreDisplay() {
        HBox scoreBox = new HBox(20);
        Text playerOneScore = new Text(game.getPlayerOne().getName() + ": " + game.getPlayerOne().getScore());
        Text playerTwoScore = new Text(game.getPlayerTwo().getName() + ": " + game.getPlayerTwo().getScore());

        playerOneScore.setFont(font);
        playerTwoScore.setFont(font);
        scoreBox.getChildren().addAll(playerOneScore, playerTwoScore);
        return scoreBox;
    }

    public VBox makeFrameDisplay() {
        HBox frameBox = new HBox();
        VBox frame = new VBox();
        Text frameTitle;
        Frame playerFrame;
        if (!game.getPlayerOne().getTurn()) {
            playerFrame = game.getPlayerOne().getFrame();
            frameTitle = new Text(game.getPlayerOne().getName() + "'s frame:");
        } else {
            playerFrame = game.getPlayerTwo().getFrame();
            frameTitle = new Text(game.getPlayerTwo().getName() + "'s frame:");
        }
        frameBox.getChildren().clear();
        for (Tile tile : playerFrame.getFrame()) {
            StackPane tileObject = frameTile(tile);
            frameBox.getChildren().add(tileObject);
        }
        frameBox.setAlignment(Pos.CENTER);
        frameTitle.setFont(font);
        frame.getChildren().addAll(frameTitle, frameBox);
        return frame;
    }

    public void initializePlayers() {
        game.setPlayerOne(game.initialisePlayer(game.getPool(), this.input));
        game.setPlayerTwo(game.initialisePlayer(game.getPool(), this.input));
        printHelpMessage();
    }

    private void printHelpMessage() {
        System.out.println("Valid commands are:\n1:\tQUIT\t\t\t\t\t\t\t\t Exit the game.\n2:\tPASS:\t\t\t\t\t\t\t\t Ends your turn without making a move.\n3:\tEXCHANGE <tile>:\t\t\t\t\t Exchanges a tile from your hand with one in the pool, then ends your turn.\n4:\t<ROW COLUMN> <DIRECTION> <WORD>:\t Makes a move, direction can be either 'a' (across) or 'd' (down). Format must be as shown in the example: 'A1 D HELLO' (note. not case-sensitive).\n5:\tHELP:\t\t\t\t\t\t\t\t Displays help message.");
    }

    public void processCLI() {
        if (!game.getPlayerOne().getTurn()) {
            System.out.println("\n" + game.getPlayerOne().getName() + "'s turn");
            System.out.println("Frame: " + game.getPlayerOne().getFrame().toString());
        } else {
            System.out.println("\n" + game.getPlayerTwo().getName() + "'s turn");
            System.out.println("Frame: " + game.getPlayerTwo().getFrame().toString());
        }
        System.out.print("Enter command: ");
        String commandInput = input.nextLine().trim().toUpperCase();
        switch (commandInput) {
            case "QUIT":
                game.setIsOver(true);
                System.out.println("Quitting...");
                break;
            case "HELP":
                printHelpMessage();
                break;
            default:
                if (!game.getPlayerOne().getTurn()) {
                    game.playerTurn(game.getPlayerOne(), game.getBoard(), input, game.getPool(), commandInput);
                    game.getPlayerOne().setTurn(true);
                } else {
                    game.playerTurn(game.getPlayerTwo(), game.getBoard(), input, game.getPool(), commandInput);
                    game.getPlayerOne().setTurn(false);
                }
        }
    }
}