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
    private boolean letterInFrame;

    public Move(Board board, Frame playerFrame) {
        this.board = board;
        this.playerFrame = playerFrame;
        checkIntersect = false;
        letterInFrame = true; //assumption
    }

    /* input getters */

    private int getRowInput(String input) {
        //TODO error checks
        return input.charAt(0) - 'A';
    }

    private int getColumnInput(String input) {
        //TODO error checks
        int column = Integer.parseInt(input.replaceAll("[\\D]", ""));
        return column -1; // -1 as columns start at 1 on board but 0 in array.
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
                letterInFrame = false;
            }
        }
        return word;
    }

   private String chooseBlank(String word, Scanner in) {
		
		System.out.print("\n\nChoose any letter to replace the blank with: ");
		Tile remove = playerFrame.getTileFromChar('_');
		playerFrame.removeLetter(remove);
		char letter = Character.toUpperCase(in.next().trim().charAt(0));
		if(!(Character.isLetter(letter))){
			System.out.print("Enter a valid character in the alphabet!");
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
            System.out.println(ERROR);
            return false;
        }
        //Connection
        if (movesMade != 0 && !findConnection(row, column)) {
            ERROR = "Connection to tiles on board not found.";
            System.out.println(ERROR);
            return false;
        }

        if (board.getBoard()[row][column] != null) {
            if(checkIntersect && (checkIntersectLetter != board.getBoard()[row][column].getLetter())){
                ERROR = "Invalid tiles for inputted word.";
                System.out.println(ERROR);
                return false;
            } else if(checkIntersect){
                intersectLocation[0] = row;
                intersectLocation[1] = column;
                return true;
            }
            ERROR = "Cannot place a tile on a space already containing a tile.";
            System.out.println(ERROR);
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

            System.out.println("Connection");
            for (int r = boxTop; r <= boxBottom && !foundConnection; r++) {
                System.out.println("Connection2");
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

        if(!letterInFrame){
            ERROR = "Letter not in frame";
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            boolean validPlacement = isPlacementValid(row, column);
            if (validPlacement) {
                chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(i));
                board.placeTile(row, column, chosenTile[tileCounter - 1]);
                playerFrame.removeLetter(chosenTile[tileCounter - 1]);
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
        for(int i = 0; i < previousCounter; i++) {
            System.out.println("prevrows: " + previousRows[i] + "prevcol" + previousColumns[i]);
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
