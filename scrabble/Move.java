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
    }

    /* input getters */

    private int getRowInput(String input) {
        //TODO error checks
        return input.charAt(0) - 'A';
    }

    private int getColumnInput(String input) {
        //TODO error checks
        int column = Integer.parseInt(input.replaceAll("[\\D]", ""));
        return column - 1; // -1 as columns start at 1 on board but 0 in array.
    }

    private char getDirectionInput(String input) {
        //TODO error checks
        return Character.toUpperCase(input.charAt(0));
    }

    private String getWordInput(String input, Scanner in) {
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
            System.out.println("row: " + i + " column: " + staticAxis);
            if (board.getBoard()[i][staticAxis] != null)  {
                if(board.getBoard()[i][staticAxis].getLetter() == checkLetterForIntersection) {
                    System.out.println("here");
                    foundIntersection = true;
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
    private boolean isPlacementValid(int row, int column) {
        if (row < 0 || row > 15 || column < 0 || column > 15) {
            ERROR = "Co-ordinates are out of bounds.";
            return false;
        }
        //Connection
        if (!findConnection(row, column)) {
            ERROR = "Connection to tiles on board not found.";
            return false;
        }

        if (board.getBoard()[row][column] != null && !intersection) {
            ERROR = "Cannot place a tile on a space already containing a tile.";
            return false;
        }

        return true;
    }

    private boolean findConnection(int row, int column) {
        if (movesMade != 0) {
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
        direction = getDirectionInput(splitInput[1]);
        String word = getWordInput(splitInput[2], in);

        if (word.contains("_")) {
            word = chooseBlank(word, in);
        }
        if(checkForIntersection){
            intersection = isIntersectionValid(row, column, direction, word);
        }

        if (!letterInFrame) {
            ERROR = "Letter not in frame";
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            boolean validPlacement = isPlacementValid(row, column);
            if (validPlacement) {
                if(board.getBoard()[row][column] != null && intersection){

                } else {
                    chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(i));
                    board.placeTile(row, column, chosenTile[tileCounter - 1]);
                    playerFrame.removeLetter(chosenTile[tileCounter - 1]);
                }
                if (direction == 'D') {
                    row++;
                } else if (direction == 'A') {
                    column++;
                } else {
                    ERROR = "Shouldn't be possible to reach here; direction isnt across or down.";
                }
            } else {
                System.out.println(ERROR);
                return false;
            }
        }
        if (board.getBoard()[7][7] == null) {
            ERROR = "First move has to intersect middle tile.";
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
        for (int i = 0; i < previousCounter; i++) {
            System.out.println("prevrows: " + previousRows[i] + "prevcol" + previousColumns[i]);
        }
    }

    	public int calculateScore() {
		boolean isdoubleword = false;
		boolean istripleword = false;
		int temp = 0;

		int score = 0;
		int count = 0;
		if (chosenTile != null)
			for (int i = 0;i<chosenTile.length;i++) {
				
				Tile t = chosenTile[i];

				if (t != null) {
					count++;
					int concat1 = columntemp + i;
					int concat2 = rowtemp + i;
					
					String position = null;
					
					
					
					if(direction == 'A')
					position = Board.concatInt(rowtemp, concat1);
					
					if(direction == 'D')
					position = Board.concatInt(concat2, columntemp);
					
					
					if(board.getSquareValue(position) != null) {
					switch (board.getSquareValue(position)) {
					case "TW":
						istripleword = true;
						temp += t.getScore();
						System.out.println("TW");
						break;
					case "TL":
						temp = temp + (t.getScore() * 3);
						System.out.println("TL");
						break;
					case "DW":
						isdoubleword = true;
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
					} else {temp += t.getScore();}
					
				}

			}

		score += temp;

		

		if (direction == 'D') {

			if (rowtemp - 1 >= 0 && rowtemp - 1 <= 14)
				if (board.getBoard()[rowtemp - 1][columntemp] != null) {
					
					score += calculateConnectedTileScore(rowtemp - 1, columntemp, 'u');
				}

			if (rowtemp + count >= 0 && rowtemp + count <= 14)
				if (board.getBoard()[rowtemp + count][columntemp] != null) {
					score += calculateConnectedTileScore(rowtemp + count, columntemp, 'd');
				}

			int i = 0;
			while( i != 0) {

				if (rowtemp + i >= 0 && rowtemp + i <= 14 && columntemp - 1 >= 0 && columntemp - 1 <= 14)
					if (board.getBoard()[rowtemp + i][columntemp - 1] != null) {	
						score += calculateConnectedTileScore(rowtemp + i, columntemp - 1, 'l');
					}
				
				if (rowtemp + i >= 0 && rowtemp + i <= 14 && columntemp - 1 >= 0 && columntemp - 1 <= 14)
					if (board.getBoard()[rowtemp + i][columntemp + 1] != null) {	
						score += calculateConnectedTileScore(rowtemp + i, columntemp + 1, 'r');
					}
				
				

			}
		
		} else if (direction == 'A') {
//
			if (columntemp - 1 >= 0 && columntemp - 1 <= 14)
				if (board.getBoard()[rowtemp][columntemp-1] != null) {
					
					score += calculateConnectedTileScore(rowtemp, columntemp - 1, 'l');
				}

			if (columntemp + count >= 0 && columntemp + count <= 14)
				if (board.getBoard()[rowtemp][columntemp + count] != null) {
					score += calculateConnectedTileScore(rowtemp, columntemp + count, 'r');
				}

			int i = 0;
			while( i != 0) {

				if (rowtemp   - 1  >= 0 && rowtemp - 1  <= 14 && columntemp + i >= 0 && columntemp + i <= 14)
					if (board.getBoard()[rowtemp - 1][columntemp + i] != null) {	
						score += calculateConnectedTileScore(rowtemp - 1, columntemp + i, 'u');
					}
				
				if (rowtemp + 1 >= 0 && rowtemp + 1 <= 14 && columntemp + i >= 0 && columntemp + i <= 14)
					if (board.getBoard()[rowtemp + 1][columntemp + i] != null) {	
						score += calculateConnectedTileScore(rowtemp + 1, columntemp + i, 'd');
					}
				
				

			}

		}
		
		if (isdoubleword)
			score *= 2;

		if (istripleword)
			score *= 3;
		
		if(count == 7) {
			score += 50;
		}
		
		return score;
	}

	public int calculateConnectedTileScore(int rowtemp, int columntemp, char direction) { // direction: u = up,d =
																							// down,r =
		// right,l =left

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
				while (board.getBoard()[rowtemp][columntemp-i] != null) {
					score += board.getBoard()[rowtemp][columntemp-i].getScore();
					i++;
					if (columntemp - i < 0 || columntemp - i > 14)
						break;
				}
			}
			if (direction == 'r') {
				while (board.getBoard()[rowtemp][columntemp + i] != null ) {
					score += board.getBoard()[rowtemp][columntemp + i].getScore();
					i++;
					if (columntemp + i < 0 || columntemp + i > 14)
						break;
				}
			}

		}
		
		
		return score;
	}
    
}
