package scrabble;

import java.util.HashMap;

public class Board {

    private Tile[][] letterBoard;

    private static HashMap<String, String> tilePoints;

    private boolean firstMove;

    private int numTiles = 0;

    public final int BOARD_SIZE = 15;


    public Board() {
        letterBoard = new Tile[15][15];
        firstMove = true;
        initBoardScores();
    }

    private void initBoardScores() {
        numTiles = 0;
        Board.tilePoints = new HashMap<>();
        // Triple word
        tilePoints.put("00", "TW");
        tilePoints.put("70", "TW");
        tilePoints.put("07", "TW");
        tilePoints.put("014", "TW");
        tilePoints.put("140", "TW");
        tilePoints.put("147", "TW");
        tilePoints.put("714", "TW");
        tilePoints.put("1414", "TW");

        // Double letter
        tilePoints.put("51", "TL");
        tilePoints.put("91", "TL");
        tilePoints.put("15", "TL");
        tilePoints.put("55", "TL");
        tilePoints.put("95", "TL");
        tilePoints.put("135", "TL");
        tilePoints.put("19", "TL");
        tilePoints.put("59", "TL");
        tilePoints.put("99", "TL");
        tilePoints.put("13", "TL");
        tilePoints.put("513", "TL");
        tilePoints.put("913", "TL");

        // Double word
        //tilePoints.put(11, "DW"); ERROR shouldnt be here
        tilePoints.put("22", "DW");
        tilePoints.put("33", "DW");
        tilePoints.put("44", "DW");
        tilePoints.put("113", "DW");
        tilePoints.put("212", "DW");
        tilePoints.put("311", "DW");
        tilePoints.put("410", "DW");
        tilePoints.put("131", "DW");
        tilePoints.put("122", "DW");
        tilePoints.put("104", "DW");
        tilePoints.put("1010", "DW");
        tilePoints.put("1111", "DW");
        tilePoints.put("1212", "DW");
        tilePoints.put("1313", "DW");

        // Double letter
        tilePoints.put("11", "DL");  //error testing
        tilePoints.put("30", "DL");
        tilePoints.put("62", "DL");
        tilePoints.put("82", "DL");
        tilePoints.put("03", "DL");
        tilePoints.put("73", "DL");
        tilePoints.put("143", "DL");
        tilePoints.put("26", "DL");
        tilePoints.put("66", "DL");
        tilePoints.put("86", "DL");
        tilePoints.put("126", "DL");
        tilePoints.put("37", "DL");
        tilePoints.put("117", "DL");
        tilePoints.put("28", "DL");
        tilePoints.put("68", "DL");
        tilePoints.put("88", "DL");
        tilePoints.put("128", "DL");
        tilePoints.put("011", "DL");
        tilePoints.put("711", "DL");
        tilePoints.put("1411", "DL");
        tilePoints.put("612", "DL");
        tilePoints.put("812", "DL");
        tilePoints.put("314", "DL");
        tilePoints.put("1114", "DL");

        // Middle
        tilePoints.put("77", "*");
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

    public static void displayLegend() {
        System.out
                .print("Triple Word: '#'\tDouble Word: '$'\tTriple Letter: '+'\tDouble Letter: '^'\tMiddle: '*''\n\n");
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
        return "" + a + b;
    }

    public Tile[][] getBoard() {
        return letterBoard;
    }
}

