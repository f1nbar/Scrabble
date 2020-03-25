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

    private boolean checkIntersect; // for checking when if an inputted word does not contain a letter, the word it intersects does.
    private char checkIntersectLetter;
    private int[] intersectLocation = new int[2];


    public Move(Board board, Frame playerFrame) {
        this.board = board;
        this.playerFrame = playerFrame;
        checkIntersect = false;
    }

    /* input getters */
    private String getMoveInput(Scanner in) {
        String input = in.nextLine();
        System.out.println("M44: input " + input);
        return input;
    }

    private int getRowInput(String input) {
    	System.out.print("Row input: " + input.charAt(0) );
        int row =  input.charAt(0) - 'A';
        if(row < 0 || row > 15) {
        	System.out.print("\nRow is not valid, please enter a letter between A and O: \n");
        	Scanner in = new Scanner(System.in);
            char newRow = in.nextLine().charAt(0);
            String replaceRow = input.replace(input.charAt(0),newRow);
            in.close();
            getRowInput(replaceRow);
        }      
        return row;
    }

    private int getColumnInput(String input) {
    	 int column;
    	 if(input.charAt(2) != ' ') {
    		column = Character.getNumericValue(input.charAt(1) + input.charAt(2)) - 1;
         	 }
    	 else {
    	 column = Character.getNumericValue(input.charAt(1)) - 1;
    	 }
         if(column < 0 || column > 15) {
         	System.out.print("\nColumn is not valid, please enter a number between 1 and 15: \n");
         	Scanner in = new Scanner(System.in);
            int newColumn = in.nextInt();
        	 String replaceColumn = input.replace(Integer.toString(column - 1),Integer.toString(newColumn + 1));
        	 System.out.print("replacing: " +Integer.toString(column - 1));
        	 System.out.print("replacing with : " + Integer.toString(newColumn + 1));
            in.close();
            getColumnInput(replaceColumn);    
         }
        return column;
    }

    private char getDirectionInput(String input) {
    	char direction;
    	if(input.charAt(3) == ' ') {
    		direction = Character.toUpperCase(input.charAt(4));
    	}
    	else {
        direction = Character.toUpperCase(input.charAt(3));
    	}
        if(direction != 'A' && direction != 'D' ) {
        	System.out.print("Direction not valid, please enter either A for accross or D for down: \n");
        	Scanner in = new Scanner(System.in);
            char newDirection = Character.toUpperCase(in.nextLine().charAt(0)); 
            String replaceDirection = input.replace(direction, newDirection);
            in.close();
            getDirectionInput(replaceDirection);
        }
        return direction;
    }


    private String getWordInput(String input, Scanner in) {
        String word = input.substring(5).toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            try {
                playerFrame.getTileFromChar(word.charAt(i));
            } catch (Exception e) {
                System.out.println("Letter not in frame, pick again.");
                String newWord = getMoveInput(in);
                String replaceWord = input.replaceAll(word, newWord);
                getWordInput(replaceWord, in);
                checkIntersect = true;
                checkIntersectLetter = word.charAt(i);
            }
        }
        return word;
    }
    
   private String chooseBlank(String word) {
		
	    Scanner in = new Scanner(System.in);
		System.out.print("\n\nChoose any letter to replace the blank with: ");
		Tile remove = playerFrame.getTileFromChar('_');
		playerFrame.removeLetter(remove);
		char letter = Character.toUpperCase(in.next(".").charAt(0));
		if(!(Character.isLetter(letter))){
			System.out.print("Enter a valid character in the alphabet!");
			chooseBlank(word);
		}
		Tile add = new Tile(letter, 0);
		playerFrame.addTile(add);
		String replaced = word.replace('_', letter);
		in.close();

		return replaced;
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

        if (board.getBoard()[row][column] != null) {
            if(checkIntersect && (checkIntersectLetter != board.getBoard()[row][column].getLetter())){
                ERROR = "Invalid tiles for inputted word.";
                return false;
            } else if(checkIntersect){
                intersectLocation[0] = row;
                intersectLocation[1] = column;
                return true;
            }
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
            boolean foundConnection = true;

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
        boolean moveMade = false;

        while (!moveMade) {
            boolean validPlacement;

            int row = getRowInput(inputString);
            int column = getColumnInput(inputString);
            System.out.println("Row: " + row + " column: " + column);
            direction = getDirectionInput(inputString);
            System.out.println("Direction: " + direction);
            String word = getWordInput(inputString, in);
            if(word.contains("_")) {
            	word = chooseBlank(word);
            }
            for (int j = 0; j < word.length(); j++) {
                validPlacement = isPlacementValid(row, column);
                if (validPlacement && !checkIntersect) {
                    chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(j));
                    board.placeTile(row, column, chosenTile[tileCounter - 1]);
                    playerFrame.removeLetter(chosenTile[tileCounter - 1]);
                    if (direction == 'D') {
                        row++;
                    } else {
                        column++;
                    }
                    System.out.println("Row: " + row + " column: " + column);
                } else if(validPlacement && checkIntersect) {
                    System.out.println("HERE");
                    System.out.println("Row: " + row + " intersect row: " + intersectLocation[0] + " column: " + column + " intersect column: " + intersectLocation[1]); //TODO fix error where when intersecting word is placed the letters after intersection are not placed
                    if(row != intersectLocation[0] && column != intersectLocation[1]) {
                        System.out.println("HERE2");
                        chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(j));
                        board.placeTile(row, column, chosenTile[tileCounter - 1]);
                        playerFrame.removeLetter(chosenTile[tileCounter - 1]);
                        if (direction == 'D') {
                            row++;
                        } else {
                            column++;
                        }
                        //System.out.println("Row: " + row + " column: " + column);
                    }
                } else {
                    System.out.println(ERROR);
                    chosenTile[tileCounter] = null; // remove invalid move from history
                    tileCounter--;
                }
            }

            // Break out
            moveMade = true;
            movesMade++;
            if (movesMade == 1 && board.getBoard()[7][7] == null) {
                movesMade--;
                ERROR = "First move has to intersect middle tile.";
                return false;
            }
            /*if (!undo || validPlacement) { // undo check so that the undid move is not added into memory, validPlacement check to ensure that the invalid move is not added into memory.
                previousRows[previousCounter] = row;
                previousColumns[previousCounter++] = column;
            }*/
        }
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

    /* Scoring 
    public int calculateScore() {
        int score = 0;
        for (Tile t : chosenTile) {
            score += t.getScore();
        }
        //for()
        //TODO connecting tiles score
        //TODO multipliers
        return 1;
    }*/
}
