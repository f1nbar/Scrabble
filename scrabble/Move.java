package scrabble;

import java.util.Scanner;

public class Move {
    private static int movesMade;
    public static final int horizontal = 1;
    public static final int vertical = 2;

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

    private boolean isPlacementValid() {
        if (firstMove && row != 7 && column != 7) {
            return false;
        } else {
            if ((row < 0 || row > 15) || (column < 0 || column > 15)) { // bounds of board
                return false;
            }
            this.valid = true;
            return true;
        }
    }

    public void getRowInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter row: ");
        this.row = in.nextInt();
        if(row < 0 || row > 15){
            System.out.println("Row out of range.");
            getRowInput();
        }
        in.close();
    }

    public void getColumnInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter row: ");
        this.column = in.nextInt();
        if(column < 0 || column > 15){
            System.out.println("Column out of range.");
            getColumnInput();
        }
        in.close();
    }
    private static void makeMove(){}
}
