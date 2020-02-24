package scrabble;

public class Move {
	private static int movesMade;
	public static final int horizontal = 1;
	public static final int veritical = 2;

	private Tile tile;
	private int row, column, direction;
	private static boolean firstMove = true;
	private Frame playerFrame;
	
	public boolean valid;

	public Move(int row, int column,  boolean firstMove, int direction, Frame playerFrame, Tile tile) {
		this.row = row;
		this.column = column;
		this.direction = direction;
		if(isPlacementValid()) {
		Move.movesMade++;
		this.playerFrame = playerFrame;
		} else {
			this.valid = false;
		}
	}
	
	//getters
	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
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
}
