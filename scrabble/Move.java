package scrabble;

import java.util.Scanner;

public class Move {
    private static int movesMade;

    public String ERROR;
    public static final int NEUTRAL_DIRECTION = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private static int tilesPlaced = 0;

    private Tile tile;
    private int row, column, direction;
    private static boolean firstMove = true;
    private Frame playerFrame;
    private Board board;

    public boolean valid;
    public Move(Board board){
        this.board = board;
    }

    //getters
    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Tile getTile() {
        return this.tile;
    }

    public boolean getFirstMove() {
        return Move.firstMove;
    }

    public static int getMovesMade() {
        return movesMade;
    }

    public static void firstMoveMade() {
        Move.firstMove = false;
    }

    // Not currently included - better to be contained in Board class: check whether
    // tile is touching a previously placed tile and if there is already a tile
    // placed in the position of the board.

    private int getRowInput(Scanner in) {
        int row;
        System.out.print("Please enter row: ");
        row = in.nextInt();
        if(row < 0 || row > 15){
            System.out.println("Row out of range.");
            getRowInput(in);
        }
        return row;
    }

    private int getColumnInput(Scanner in) {
        int column;
        System.out.print("Please enter column: ");
        column = in.nextInt();
        if(column < 0 || column > 15){
            System.out.println("Column out of range.");
            getColumnInput(in);
        }
        return column;
    }

    private Tile getTileInput(Scanner in) {
        System.out.print("\n\nFirst Move\nPlease pick a letter: ");
        char letter = Character.toUpperCase(in.next(".").charAt(0));

        try {
            playerFrame.getTileFromChar(letter);
        } catch (Exception e){
            System.out.println("Letter not in frame, pick again.");
            getTileInput(in);
        }
        return playerFrame.getTileFromChar(letter);
    }

    private boolean isPlacementValid(int row, int column) {
        boolean isLegal = true;
        //Connection
        if (isLegal && tilesPlaced != 0) {
            int boxTop = Math.max(row - 1, 0);
            int boxBottom = Math.min(row + 1, board.BOARD_SIZE - 1);
            int boxLeft = Math.max(column - 1, 0);
            int boxRight = Math.min(column + 1, board.BOARD_SIZE - 1);
            boolean foundConnection = false;

            for (int r = boxTop; r <= boxBottom && !foundConnection; r++) {
                for (int c = boxLeft; c <= boxRight && !foundConnection; c++) {
                    if (board.getLetterBoard()[r][c] != null) {
                        foundConnection = true;
                    }
                }
            }
            if (!foundConnection) {
                isLegal = false;
            }
        }
        return isLegal;
    }

    private boolean makeMove(){
        boolean moveMade = false;
        int tilesPlaced = 0;

        final int THIRD_PLACEMENT = 3; // for ease of reading
        final int UNSET = -1;

        int prevRow =  UNSET;
        int prevColumn = UNSET;

        Board prevBoard = board;

        int direction = NEUTRAL_DIRECTION;
        Scanner in =  new Scanner(System.in);

        while(!moveMade){
            int row = UNSET;
            int column = UNSET;
            System.out.print("Frame: " + playerFrame.toString());

            Tile chosenTile = getTileInput(in);

            // Select position
            if(tilesPlaced < THIRD_PLACEMENT) {
                row = getRowInput(in);
                column = getColumnInput(in);
            } else {
                if(direction == HORIZONTAL){
                    column = getColumnInput(in);
                }
                else
                {
                    row = getRowInput(in);
                }
            }

            // set Direction
            if(prevRow != UNSET && prevColumn != UNSET){
                if(prevRow == row){
                    direction = HORIZONTAL;
                }
                else {
                    direction = VERTICAL;
                }
            }

            if(isPlacementValid(row, column)){
                prevBoard = board;
                board.placeTile(row, column, chosenTile);
            }

            // Break out
            System.out.println("Type: 'exit' to finish your turn, or 'undo' to undo your last placement.");
            String playerChoice = in.next();
            if(playerChoice.toLowerCase().contentEquals("exit") || playerFrame.isEmpty()) {
                moveMade = true;
                movesMade++;
            } else if(playerChoice.toLowerCase().contentEquals("undo")){
                tilesPlaced--;
                board = prevBoard;
                // TODO: add back tile to frame playerFrame.
            }
            if(moveMade && movesMade == 0 && board.getBoard()[7][7] == null){
                ERROR = "First move has to intersect middle tile.";
                return false;
            }
            prevRow = row;
            prevColumn = column;
        }
        in.close();
        return true;
    }
}
