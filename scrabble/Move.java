package scrabble;

public class Move {
	static int movesMade;

	private Tile tile;
	private int row, column;
	private boolean firstMove;
	private Frame playerFrame;
	
	public boolean valid;

	public Move(int row, int column,  boolean firstMove, Frame playerFrame, Tile tile) {
		if(isPlacementValid()) {
		Move.movesMade++;
		this.row = row;
		this.column = column;
		this.firstMove = firstMove;
		this.playerFrame = playerFrame;
		} else {
			System.out.println("Invalid move.");
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
		return this.firstMove;
	}
	public static int getMovesMade() {
		return movesMade;
	}

	// TODO: add boolean per turn that locks player into horizontal/vertical
	// placement of tiles. Cannot implement at this sprint as player turns is out of
	// scope for the assignment. Boolean stored in turn method of scrabble class and
	// passed to this method.

	// Not currently included - better to be contained in Board class: check whether
	// tile is touching a previously placed tile and if there is already a tile
	// placed in the position of the board.
	private boolean isPlacementValid() {
		if (firstMove && row != 7 && column != 7) {
			return false;
		} else if (firstMove) {
			return false;
		} else {
			if ((row < 0 || row > 15) || (column < 0 || column > 15)) { // bounds of board
				return false;
			}
			if (!playerFrame.checkLetters(tile)) {
				return false; 
			}
			this.valid = true;
			return true;
		}
	}
	
	
}
