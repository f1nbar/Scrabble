package scrabble;

import java.util.Scanner;

public class Move {
	private static int movesMade;

	public String ERROR;
	public static final int NEUTRAL_DIRECTION = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;

	private Frame playerFrame;
	private Board board;

	// History arrays
	int[] previousRows = new int[7];
	int[] previousColumns = new int[7];
	Tile[] chosenTile = new Tile[7];

	int previousCounter = 0;
	int tileCounter = 0;

	public Move(Board board, Frame playerFrame) {
		this.board = board;
		this.playerFrame = playerFrame;
	}

	/* getters */
	public static int getMovesMade() {
		return movesMade;
	}

	/* input getters */
	private int getRowInput(Scanner in) {
		int row;
		System.out.print("Please enter row: ");
		row = in.nextInt();
		if (row < 0 || row > 15) {
			System.out.println("Row out of range.");
			getRowInput(in);
		}
		return row;
	}

	private int getColumnInput(Scanner in) {
		int column;
		System.out.print("Please enter column: ");
		column = in.nextInt();
		if (column < 0 || column > 15) {
			System.out.println("Column out of range.");
			getColumnInput(in);
		}
		return column;
	}

	private Tile getTileInput(Scanner in) {
		System.out.print("\n\nPlease pick a letter: ");
		char letter = Character.toUpperCase(in.next(".").charAt(0));

		try {
			playerFrame.getTileFromChar(letter);
		} catch (Exception e) {
			System.out.println("Letter not in frame, pick again.");
			getTileInput(in);
		}
		if (playerFrame.getTileFromChar(letter).getLetter() == '_') {
			letter = chooseBlank(in);

		}
		return playerFrame.getTileFromChar(letter);
	}

	private char chooseBlank(Scanner in) {
		
		System.out.println("\n\nChoose any letter to replace the blank with: ");
		Tile remove = playerFrame.getTileFromChar('_');
		playerFrame.removeLetter(remove);
		char letter = Character.toUpperCase(in.next(".").charAt(0));
		Tile add = new Tile(letter, 0);
		playerFrame.addTile(add);

		return letter;
	}

	/* move validation */
	private boolean isPlacementValid(int row, int column) {
		if (row < 0 || row > 15 || column < 0 || column > 15) {
			ERROR = "Co-ordinates are out of bounds.";
			return false;
		}
		// Connection
		if (!findConnection(row, column)) {
			return false;
		}

		if (board.getBoard()[row][column] != null) {
			ERROR = "Cannot place a tile on a space already containing a tile.";
			return false;
		}
		return true;
	}

	private boolean findConnection(int row, int column) {
		if (tileCounter != 1) {
			int boxTop = Math.max(row - 1, 0);
			int boxBottom = Math.min(row + 1, board.BOARD_SIZE - 1);
			int boxLeft = Math.max(column - 1, 0);
			int boxRight = Math.min(column + 1, board.BOARD_SIZE - 1);
			boolean foundConnection = false;

			for (int r = boxTop; r <= boxBottom && !foundConnection; r++) {
				for (int c = boxLeft; c <= boxRight && !foundConnection; c++) {
					if (board.getBoard()[r][c] != null) {
						foundConnection = true;
					}
				}
			}
			if (!foundConnection) {
				ERROR = "Cannot place a tile not connecting to any other tiles.";
			}
			return foundConnection;
		}
		return true; // automatically true if it is the first tile being placed.
	}

	/* make move driver */
	public boolean makeMove(Scanner in, UI userInterface) {
		boolean moveMade = false;

		final int THIRD_PLACEMENT = 3; // for ease of reading

		int direction = NEUTRAL_DIRECTION;
		while (!moveMade) {
			int row;
			int column;
			boolean undo = false;
			boolean validPlacement;
			System.out.print("Frame: " + playerFrame.toString());

			chosenTile[tileCounter++] = getTileInput(in);

			// Select position
			if (tileCounter < THIRD_PLACEMENT) {
				row = getRowInput(in);
				column = getColumnInput(in);
			} else {
				if (direction == HORIZONTAL) {
					column = getColumnInput(in);
					row = previousRows[previousCounter - 1];
				} else {
					row = getRowInput(in);
					column = previousColumns[previousCounter - 1];
				}
			}

			// set Direction
			if (tileCounter == 2) {
				if (previousRows[previousCounter - 1] == row) {
					direction = HORIZONTAL;
				} else {
					direction = VERTICAL;
				}
			}

			validPlacement = isPlacementValid(row, column);
			if (validPlacement) {
				board.placeTile(row, column, chosenTile[tileCounter - 1]);
				playerFrame.removeLetter(chosenTile[tileCounter - 1]);
				userInterface.repaint();
			} else {
				System.out.println(ERROR);
				chosenTile[tileCounter] = null; // remove invalid move from history
				tileCounter--;
			}

			// Break out
			System.out.println(
					"Type: 'exit' to finish your turn, or 'undo' to undo your last placement. Press any other key to continue.");
			String playerChoice = in.next();
			if (playerChoice.toLowerCase().contentEquals("exit") || playerFrame.isEmpty()) {
				moveMade = true;
				movesMade++;
			} else if (playerChoice.toLowerCase().contentEquals("undo")) {
				if (validPlacement) { // i.e if move is valid, undo current move, otherwise undo last valid move
					undoPlace(chosenTile, tileCounter, row, column, userInterface);
				} else {
					undoPlace(chosenTile, tileCounter, previousRows[previousCounter - 1],
							previousColumns[previousCounter - 1], userInterface);
				}
				System.out.println("Previous row: " + previousRows[previousCounter] + " Previous column: "
						+ previousColumns[previousCounter]);
				undo = true;
				tileCounter--;
			}
			if (moveMade && movesMade == 1 && board.getBoard()[7][7] == null) {
				movesMade--;
				ERROR = "First move has to intersect middle tile.";
				return false;
			}
			if (!undo || validPlacement) { // undo check so that the undid move is not added into memory, validPlacement
											// check to ensure that the invalid move is not added into memory.
				System.out.println("REACHED HERE");
				previousRows[previousCounter] = row;
				previousColumns[previousCounter++] = column;
			}
			System.out.println("Previous row: " + previousRows[previousCounter - 1] + " Previous column: "
					+ previousColumns[previousCounter - 1]);
		}

		return true;
	}

	/* undo move driver */
	private void undoPlace(Tile[] chosenTile, int tileCounter, int row, int column, UI userInterface) {
		int workingTile = tileCounter - 1;
		System.out.println("Undo place: " + chosenTile[workingTile].getLetter() + " from " + row + " " + column);
		playerFrame.addTile(chosenTile[workingTile]);
		board.removeTile(row, column);
		chosenTile[workingTile] = null;
		userInterface.repaint();
	}

	public void undoMove(UI userInterface) {
		while (tileCounter != 0) {
			undoPlace(chosenTile, tileCounter--, previousRows[previousCounter - 1],
					previousColumns[previousCounter - 1], userInterface);
			previousCounter--;
		}
	}
}
