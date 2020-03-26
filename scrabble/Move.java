package scrabble;


import java.util.Scanner;

public class Move {
    private static int movesMade;

    public String ERROR;
    private char direction;

    private Frame playerFrame;
    private Board board;

    // History arrays
    int[] previousRows = new int[7];
    int[] previousColumns = new int[7];
    Tile[] chosenTile = new Tile[7];

    int previousCounter = 0;
    int tileCounter = 0;

    // word positions
    int firstRow;
    int firstColumn;
    int lastColumn;
    int lastRow;

    int[] boardConnectionRow = new int[16];
    int[] boardConnectionColumn = new int[16];
    int connectionIncrement;

    private boolean letterInFrame;
    private boolean checkForIntersection; // triggers check
    private char checkLetterForIntersection;
    private boolean intersection; // return value of if intersection is valid or not

    public Move(Board board, Frame playerFrame) {
        this.board = board;
        this.playerFrame = playerFrame;
        this.letterInFrame = true; //assumption
        this.checkForIntersection = false;
        this.intersection = false;
        this.connectionIncrement = 0;
    }

    /* input getters */
    private int getRowInput(String input) {
        return input.charAt(0) - 'A';
    }

    private int getColumnInput(String input) {
        int column = Integer.parseInt(input.replaceAll("[\\D]", ""));
        return column - 1; // -1 as columns start at 1 on board but 0 in array.
    }

    private char getDirectionInput(String input) {
        return Character.toUpperCase(input.charAt(0));
    }

    private String getWordInput(String input) {
        String word = input.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            try {
                playerFrame.getTileFromChar(word.charAt(i));
            } catch (Exception e) {
                System.out.println("Letter: '" + word.charAt(i) + "' not in frame, pick again.");
                checkForIntersection = true;
                checkLetterForIntersection = word.charAt(i);
            }
        }
        return word;
    }

    private boolean isIntersectionValid(int startingRow, int startingColumn, char direction, String word) {
        int changingAxis = 0;
        int staticAxis = 0;
        boolean foundIntersection = false;

        if (direction == 'D') {
            changingAxis = startingRow;
            staticAxis = startingColumn;
        } else if (direction == 'A') {
            changingAxis = startingColumn;
            staticAxis = startingRow;
        } else {
            ERROR = "Shouldn't be possible to reach here: direction isn't a or d. Move.java Line 74";
        }
        for (int i = changingAxis; i < changingAxis + word.length(); i++) {
            if (direction == 'D') {
                if (board.getBoard()[i][staticAxis] != null) {
                    if (board.getBoard()[i][staticAxis].getLetter() == checkLetterForIntersection) {
                        foundIntersection = true;
                    }
                }
            } else {

                if (board.getBoard()[staticAxis][i] != null) {
                    if (board.getBoard()[staticAxis][i].getLetter() == checkLetterForIntersection) {
                        foundIntersection = true;
                    }
                }
            }
        }
        return foundIntersection;
    }

    private String chooseBlank(String word, Scanner in) {
        System.out.print("Choose any letter to replace the blank with: ");
        Tile remove = playerFrame.getTileFromChar('_');
        playerFrame.removeLetter(remove);
        char letter = Character.toUpperCase(in.next().trim().charAt(0));
        if (!(Character.isLetter(letter))) {
            System.out.print("\nEnter a valid character in the alphabet!");
            chooseBlank(word, in);
        }
        Tile add = new Tile(letter, 0);
        playerFrame.addTile(add);

        return word.replace('_', letter);
    }


    /* move validation */
    private void setLastCoords(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (direction == 'D') {
                lastColumn = firstColumn;
                if (i == word.length() - 1) {
                    lastRow = firstRow + i;
                }
            } else if (direction == 'A') {
                lastRow = firstRow;
                if (i == word.length() - 1) {
                    lastColumn = firstColumn + i;
                }
            }
        }
    }

    private boolean isPlacementValid(int row, int column) {

        if (direction == 'D' && lastRow > 15) {
            ERROR = "Cannot place a word going over the edge of the board";
            return false;
        }

        if (direction == 'A' && lastColumn > 15) {
            ERROR = "Cannot place a word going over the edge of the board";
            return false;
        }
        if (row < 0 || row > 15 || column < 0 || column > 15) {
            ERROR = "Co-ordinates are out of bounds.";
            return false;
        }
        if (board.getBoard()[row][column] != null && !intersection) {
            ERROR = "Cannot place a tile on a space already containing a tile.";
            return false;
        }


        return true;
    }

    private boolean findConnection() {
        if (movesMade != 0) {

            int boxTop = Math.max(firstRow - 1, 0);
            int boxBottom = Math.min(lastRow + 1, board.BOARD_SIZE - 1);
            int boxLeft = Math.max(firstColumn - 1, 0);
            int boxRight = Math.min(lastColumn + 1, board.BOARD_SIZE - 1);
            boolean foundConnection = false;

            for (int r = boxTop; r <= boxBottom && !foundConnection; r++) {
                for (int c = boxLeft; c <= boxRight && !foundConnection; c++) {
                    if (board.getBoard()[r][c] != null) {
                        boardConnectionRow[connectionIncrement] = r;
                        boardConnectionColumn[connectionIncrement++] = c;
                        foundConnection = true;
                    }
                }
            }
            if (!(foundConnection)) {
                ERROR = "Cannot place a tile not connecting to any other tiles.";
            }
            return foundConnection;
        }
        return true; // automatically true if it is the first tile being placed.
    }

    /* make move driver */
    public boolean makeMove(Scanner in, String inputString) {
        String[] splitInput = inputString.split(" ");
        int row = getRowInput(splitInput[0]);
        int column = getColumnInput(splitInput[0]);
        firstRow = row;
        firstColumn = column;
        direction = getDirectionInput(splitInput[1]);
        String word = getWordInput(splitInput[2]);
        setLastCoords(word);

        while (word.contains("_")) {
            word = chooseBlank(word, in);
        }
        if (checkForIntersection) {
            intersection = isIntersectionValid(row, column, direction, word);
        }

        if (!letterInFrame) {
            ERROR = "Letter not in frame";
            return false;
        }
        boolean foundConnection = findConnection();
        for (int i = 0; i < word.length(); i++) {
            boolean validPlacement = isPlacementValid(row, column);
            if (validPlacement && foundConnection) {
                if (!(board.getBoard()[row][column] != null && intersection)) {
                    chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(i));
                    board.placeTile(row, column, chosenTile[tileCounter - 1]);
                    playerFrame.removeLetter(chosenTile[tileCounter - 1]);
                }

                previousRows[previousCounter] = row;
                previousColumns[previousCounter++] = column;

                if (direction == 'D') {
                    row++;
                } else if (direction == 'A') {
                    column++;
                } else {
                    ERROR = "Shouldn't be possible to reach here; direction isn't across or down.";
                }
            } else {
                System.out.println(ERROR);
                undoMove();
                return false;
            }
        }
        if (board.getBoard()[7][7] == null) {
            ERROR = "First move has to intersect middle tile.";
            undoMove();
            return false;
        }
        movesMade++;
        return true;
    }

    /* undo move driver */
    private void undoPlace(Tile[] chosenTile, int tileCounter, int row, int column) {
        int workingTile = tileCounter - 1;
        System.out.println("Undo place: " + chosenTile[workingTile].getLetter() + " from " + row + " " + column);
        playerFrame.addTile(chosenTile[workingTile]);
        board.removeTile(row, column);
        chosenTile[workingTile] = null;
    }

    public void undoMove() {
        while (tileCounter != 0) {
            undoPlace(chosenTile, tileCounter--, previousRows[previousCounter - 1], previousColumns[previousCounter - 1]);
            previousCounter--;
        }
    }

 public int calculateScore() {
		boolean isDoubleWord = false;
		boolean isTripleWord = false;
		int temp = 0;

		int score = 0;
		int count = 0;
		if (chosenTile != null)
			for (int i = 0; i < chosenTile.length; i++) {

				Tile t = chosenTile[i];

				if (t != null) {
					count++;
					int concat1 = firstColumn + i;
					int concat2 = firstRow + i;

					String position = null;

					if (direction == 'A')
						position = Board.concatInt(firstRow, concat1);

					if (direction == 'D')
						position = Board.concatInt(concat2, firstColumn);

					if (board.getSquareValue(position) != null) {
						switch (board.getSquareValue(position)) {
						case "TW":
							isTripleWord = true;
							temp += t.getScore();
							System.out.println("TW");
							break;
						case "TL":
							temp = temp + (t.getScore() * 3);
							System.out.println("TL");
							break;
						case "DW":
							isDoubleWord = true;
							temp += t.getScore();
							System.out.println("DW");
							break;
						case "DL":
							temp = temp + (t.getScore() * 2);
							System.out.println("DL");
							break;
						default:
							temp += t.getScore();

						}
					} else {
						temp += t.getScore();
					}

				}

			}

		score += temp;
		System.out.println("PLACED TILE SCORE: " + score);

        if (direction == 'D') {

            if (firstRow - 1 >= 0 && firstRow - 1 <= 14)
                if (board.getBoard()[firstRow - 1][firstColumn] != null) {

                    score += calculateConnectedTileScore(firstRow - 1, firstColumn, 'u');
                }

            if (firstRow + count >= 0 && firstRow + count <= 14)
                if (board.getBoard()[firstRow + count][firstColumn] != null) {
                    score += calculateConnectedTileScore(firstRow + count, firstColumn, 'd');
                }

            int i = 0;
            while (i != count) {
                if (firstRow + i >= 0 && firstRow + i <= 14 && firstColumn - 1 >= 0 && firstColumn - 1 <= 14)
                    if (board.getBoard()[firstRow + i][firstColumn - 1] != null) {
                        score += calculateConnectedTileScore(firstRow + i, firstColumn - 1, 'l');
                    }

                if (firstRow + i >= 0 && firstRow + i <= 14 && firstColumn - 1 >= 0 && firstColumn - 1 <= 14)
                    if (board.getBoard()[firstRow + i][firstColumn + 1] != null) {
                        score += calculateConnectedTileScore(firstRow + i, firstColumn + 1, 'r');
                    }
                i++;
            }
        } else if (direction == 'A') {
            if (firstColumn - 1 >= 0 && firstColumn - 1 <= 14)
                if (board.getBoard()[firstRow][firstColumn - 1] != null) {

                    score += calculateConnectedTileScore(firstRow, firstColumn - 1, 'l');
                }

            if (firstColumn + count >= 0 && firstColumn + count <= 14)
                if (board.getBoard()[firstRow][firstColumn + count] != null) {
                    score += calculateConnectedTileScore(firstRow, firstColumn + count, 'r');
                }

            int i = 0;
            while (i != count) {
                if (firstRow - 1 >= 0 && firstRow - 1 <= 14 && firstColumn + i >= 0 && firstColumn + i <= 14)
                    if (board.getBoard()[firstRow - 1][firstColumn + i] != null) {
                        score += calculateConnectedTileScore(firstRow - 1, firstColumn + i, 'u');
                    }

                if (firstRow + 1 >= 0 && firstRow + 1 <= 14 && firstColumn + i >= 0 && firstColumn + i <= 14)
                    if (board.getBoard()[firstRow + 1][firstColumn + i] != null) {
                        score += calculateConnectedTileScore(firstRow + 1, firstColumn + i, 'd');
                    }
                
                i++;
            }
           

        }

//		findConnection(firstWord);
		

			
//			if (direction == 'D')
//				for (int i = 1; i < connectionIncrement; i++) {
//
//					if (firstColumn == boardConnectionColumn[i] && firstRow > boardConnectionRow[i]) {
//						score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'u');
//						System.out.println("DUp:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstColumn > boardConnectionColumn[i]) {
//						score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'l');
//						System.out.println("Dleft:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstColumn < boardConnectionColumn[i]) {
//						score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'r');
//						System.out.println("Dright:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstColumn == boardConnectionColumn[i] && firstRow < boardConnectionRow[i]) {
//						score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'd');
//						System.out.println("Ddown:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//				}
//			else if(direction == 'A') {
//				for (int i = 0; i < connectionIncrement; i++) {
//
//					if (firstRow == boardConnectionRow[i] && firstColumn < boardConnectionColumn[i]) {
//						score += calculateConnectedTileScore(boardConnectionColumn[i], boardConnectionRow[i], 'r');
//						System.out.println("Aright:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstRow > boardConnectionRow[i]) {
//						score += calculateConnectedTileScore(boardConnectionColumn[i], boardConnectionRow[i], 'd');
//						System.out.println("Adown:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstRow < boardConnectionRow[i]) {
//						score += calculateConnectedTileScore(boardConnectionColumn[i], boardConnectionRow[i], 'u');
//						System.out.println("AUp:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//
//					if (firstRow == boardConnectionRow[i] && firstColumn > boardConnectionColumn[i]) {
//						score += calculateConnectedTileScore(boardConnectionColumn[i], boardConnectionRow[i], 'l');
//						System.out.println("Aleft:" + boardConnectionRow[i] + boardConnectionColumn[i]);
//					}
//				}
//
//			}
		

		if (isDoubleWord)
			score *= 2;

		if (isTripleWord)
			score *= 3;

		if (count == 7) {
			score += 50;
		}
		return score;
	}

	public int calculateConnectedTileScore(int rowtemp, int columntemp, char direction) { // direction: u = up,d =
		int score = 0;

		if (board.getBoard()[rowtemp][columntemp] != null) {
			int i = 0;
			if (direction == 'u') {
				while (board.getBoard()[rowtemp - i][columntemp] != null) {
					score += board.getBoard()[rowtemp - i][columntemp].getScore();
					System.out.println(score);
					i++;
					if (rowtemp - i < 0 || rowtemp - i > 14)
						break;
				}
			}
			if (direction == 'd') {
				while (board.getBoard()[rowtemp + i][columntemp] != null) {
					score += board.getBoard()[rowtemp + i][columntemp].getScore();
					i++;
					if (rowtemp + i < 0 || rowtemp + i > 14)
						break;
				}
			}
			if (direction == 'l') {
				while (board.getBoard()[rowtemp][columntemp - i] != null) {
					score += board.getBoard()[rowtemp][columntemp - i].getScore();
					i++;
					if (columntemp - i < 0 || columntemp - i > 14)
						break;
				}
			}
			if (direction == 'r') {
				while (board.getBoard()[rowtemp][columntemp + i] != null) {
					score += board.getBoard()[rowtemp][columntemp + i].getScore();
					i++;
					if (columntemp + i < 0 || columntemp + i > 14)
						break;
				}
			}
		}
		
		System.out.println(score);
		return score;
	}

}
