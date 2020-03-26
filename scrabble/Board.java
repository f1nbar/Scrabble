package scrabble;

import java.util.HashMap;

public class Board {

    private Tile[][] letterBoard;

    private static HashMap<String, String> tilePoints;

    private int numTiles = 0;

    public final int BOARD_SIZE = 15;


    public Board() {
        letterBoard = new Tile[15][15];
        initBoardScores();
    }

    private void initBoardScores() {
        numTiles = 0;
        Board.tilePoints = new HashMap<>();
        // Triple word
        tilePoints.put("0 0", "TW");
        tilePoints.put("7 0", "TW");
        tilePoints.put("0 7", "TW");
        tilePoints.put("0 14", "TW");
        tilePoints.put("14 0", "TW");
        tilePoints.put("14 7", "TW");
        tilePoints.put("7 14", "TW");
        tilePoints.put("14 14", "TW");

        // Triple Letter
        tilePoints.put("5 1", "TL");
        tilePoints.put("9 1", "TL");
        tilePoints.put("1 5", "TL");
        tilePoints.put("5 5", "TL");
        tilePoints.put("9 5", "TL");
        tilePoints.put("13 5", "TL");
        tilePoints.put("1 9", "TL");
        tilePoints.put("5 9", "TL");
        tilePoints.put("9 9", "TL");
        tilePoints.put("13 9", "TL");
        tilePoints.put("5 13", "TL");
        tilePoints.put("9 13", "TL");

        // Double word
        tilePoints.put("1 1", "DW");
        tilePoints.put("2 2", "DW");
        tilePoints.put("3 3", "DW");
        tilePoints.put("4 4", "DW");
        tilePoints.put("1 13", "DW");
        tilePoints.put("11 3", "DW");
        tilePoints.put("2 12", "DW");
        tilePoints.put("3 11", "DW");
        tilePoints.put("4 10", "DW");
        tilePoints.put("13 1", "DW");
        tilePoints.put("12 2", "DW");
        tilePoints.put("10 4", "DW");
        tilePoints.put("10 10", "DW");
        tilePoints.put("11 11", "DW");
        tilePoints.put("12 12", "DW");
        tilePoints.put("13 13", "DW");

        // Double letter
        tilePoints.put("11 0", "DL");  //error testing
        tilePoints.put("3 0", "DL");
        tilePoints.put("6 2", "DL");
        tilePoints.put("8 2", "DL");
        tilePoints.put("0 3", "DL");
        tilePoints.put("7 3", "DL");
        tilePoints.put("14 3", "DL");
        tilePoints.put("2 6", "DL");
        tilePoints.put("6 6", "DL");
        tilePoints.put("8 6", "DL");
        tilePoints.put("12 6", "DL");
        tilePoints.put("3 7", "DL");
        tilePoints.put("11 7", "DL");
        tilePoints.put("2 8", "DL");
        tilePoints.put("6 8", "DL");
        tilePoints.put("8 8", "DL");
        tilePoints.put("12 8", "DL");
        tilePoints.put("0 11", "DL");
        tilePoints.put("7 11", "DL");
        tilePoints.put("14 11", "DL");
        tilePoints.put("6 12", "DL");
        tilePoints.put("8 12", "DL");
        tilePoints.put("3 14", "DL");
        tilePoints.put("11 14", "DL");

        // Middle
        tilePoints.put("7 7", "*");
    }

    public String getSquareValue(String location) {
        return tilePoints.getOrDefault(location, null);
    }

    public void resetBoard() {
        initBoardScores();
    }


    @Override
    public String toString() {
        StringBuilder boardDisplay = new StringBuilder();
        boardDisplay.append(
                " \n\n           0       1       2       3       4       5       6       7       8       9       10      11      12      13      14\n\n");

        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                boardDisplay.append("  ").append(i).append("        ");
            } else {
                boardDisplay.append(" ").append(i).append("        ");
            }
            for (int j = 0; j < 15; j++) {
                String position = concatInt(i, j);
                if (letterBoard[i][j] != null) {
                    boardDisplay.append(letterBoard[i][j].getLetter()).append("       ");
                } else if (getSquareValue(position) != null) {
                    switch (getSquareValue(position)) {
                        case "TW":
                            boardDisplay.append("#       "); // seven spaces
                            break;
                        case "TL":
                            boardDisplay.append("+       "); // seven spaces
                            break;
                        case "DW":
                            boardDisplay.append("$       "); // seven spaces
                            break;
                        case "DL":
                            boardDisplay.append("^       "); // seven spaces
                            break;
                        case "*":
                            boardDisplay.append("*       "); // seven spaces
                            break;
                        default:
                            boardDisplay.append("-       "); // seven spaces
                    }
                } else {
                    boardDisplay.append("-       "); // seven spaces
                }
            }
            boardDisplay.append("\n");
        }
        return (boardDisplay.toString());
    }

    public void placeTile(int row, int column, Tile chosenTile) {
        letterBoard[row][column] = chosenTile;
        numTiles++;
    }

    public void removeTile(int row, int column) {
        letterBoard[row][column] = null;
        numTiles--;
    }

    public int numTiles() {
        return numTiles;
    }

    public static String concatInt(int a, int b) { // concatenate integers rather than adding them
        return a + " " + b;
    }

    public Tile[][] getBoard() {
        return letterBoard;
    }
}

