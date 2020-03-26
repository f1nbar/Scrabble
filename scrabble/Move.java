package scrabble;


import java.util.Scanner;

public class Move {
    private static int movesMade;

    public String ERROR;
    private char direction;

    private Frame playerFrame;
    private Board board;

    // History arrays for placement
    int[] previousRows = new int[7];
    int[] previousColumns = new int[7];
    int previousCounter = 0;

    // placed tiles
    Tile[] chosenTile = new Tile[7];
    int tileCounter = 0;

    // word positions
    int firstRow;
    int firstColumn;
    int lastColumn;
    int lastRow;

    // history arrays for connections
    int[] boardConnectionRow = new int[16];
    int[] boardConnectionColumn = new int[16];
    int connectionIncrement;

    private boolean letterInFrame; // says if a played letter is in frame or not, for use in intersection

    private boolean checkForIntersection; // triggers check
    private char checkLetterForIntersection; // letter to be checked
    private boolean intersection; // return value of if intersection is valid or not
    private boolean extremityIntersection; // if intersection is at extremity
    private boolean extremityIntersectionFirst; // if above is true, if the intersection is at the top extremity or the bottom

    public Move(Board board, Frame playerFrame) {
        this.board = board;
        this.playerFrame = playerFrame;
        this.letterInFrame = true; //assumption
        this.checkForIntersection = false;
        this.intersection = false;
        this.extremityIntersection = false;
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

    // take in word, make upper case and make sure that all tiles are present in frame
    private String getWordInput(String input) {
        String word = input.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            try {
                playerFrame.getTileFromChar(word.charAt(i));
            } catch (Exception e) {
                checkForIntersection = true;
                letterInFrame = false;
                checkLetterForIntersection = word.charAt(i);
            }
        }
        return word;
    }

    private boolean isIntersectionValid(int startingRow, int startingColumn, char direction, String word) {
        int changingAxis = 0;
        int staticAxis = 0;
        boolean foundIntersection = false;

        // set axis
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
                if (board.getBoard()[i][staticAxis] != null) { //TODO: remove duplicate code block
                    if (board.getBoard()[i][staticAxis].getLetter() == checkLetterForIntersection) { // check if the position of missing tile corresponds to intersecting tile inputted
                        foundIntersection = true;
                        if((i == firstRow) && (staticAxis == firstColumn)){ // check if intersection is in the first tile or last tile of the word
                            extremityIntersection = true;
                            extremityIntersectionFirst = true;
                        } else if ((i == lastRow) && (staticAxis == lastColumn)) {
                            extremityIntersection = true;
                            extremityIntersectionFirst = false;
                        }
                    }
                }
            } else {
                if (board.getBoard()[staticAxis][i] != null) {
                    if (board.getBoard()[staticAxis][i].getLetter() == checkLetterForIntersection) {
                        foundIntersection = true;
                        if((i == firstColumn) && (staticAxis == firstRow)){
                            extremityIntersection = true;
                            extremityIntersectionFirst = true;
                        } else if ((i == lastColumn) && (staticAxis == lastRow)) {
                            extremityIntersection = true;
                            extremityIntersectionFirst = false;
                        }
                    }
                }
            }
        }
        return foundIntersection;
    }

    // changes blank display
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


    // gets last potential coordinates for word placement
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

    /* move validation */
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
        if (movesMade != 0 && !extremityIntersection) {

            // ensure that board bounds aren't exceeded
            int boxTop = Math.max(firstRow - 1, 0);
            int boxBottom = Math.min(lastRow + 1, board.BOARD_SIZE - 1);
            int boxLeft = Math.max(firstColumn - 1, 0);
            int boxRight = Math.min(lastColumn + 1, board.BOARD_SIZE - 1);
            boolean foundConnection = false;

            for (int r = boxTop; r <= boxBottom; r++) {
                for (int c = boxLeft; c <= boxRight; c++) {
                    if (board.getBoard()[r][c] != null) {
                        if (!(r == boxTop && c == boxLeft) && !(r == boxBottom && c == boxRight)) { // ignore corners
                            foundConnection = true;
                            // add connections to history arrays for score calculating
                            boardConnectionRow[++connectionIncrement] = r;
                            boardConnectionColumn[connectionIncrement] = c;
                        }
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

        this.firstRow = row;
        this.firstColumn = column;
        this. direction = getDirectionInput(splitInput[1]);

        String word = getWordInput(splitInput[2]);
        setLastCoords(word);

        // updates blank
        while (word.contains("_")) {
            word = chooseBlank(word, in);
        }
        // checks to see if player is making an intersecting move
        if (checkForIntersection) {
            intersection = isIntersectionValid(row, column, direction, word);
        }
        if(!intersection) {
            if(!letterInFrame) {
                ERROR = "Letter not in frame";
                return false;
            }
        }

        boolean foundConnection = findConnection();
        for (int i = 0; i < word.length(); i++) {
            boolean validPlacement = isPlacementValid(row, column);
            if (validPlacement && foundConnection) {
                if (!(board.getBoard()[row][column] != null && intersection)) {
                    chosenTile[tileCounter++] = playerFrame.getTileFromChar(word.charAt(i)); // add tile to chosenTile array for undoing move and score calculation
                    board.placeTile(row, column, chosenTile[tileCounter - 1]);
                    playerFrame.removeLetter(chosenTile[tileCounter - 1]);
                }

                previousRows[previousCounter] = row; // track previous placements
                previousColumns[previousCounter++] = column;

                if (direction == 'D') {
                    row++;
                } else if (direction == 'A') {
                    column++;
                } else {
                    ERROR = "Shouldn't be possible to reach here; direction isn't across or down.";
                }
            } else {
                // move not valid
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
        int multiplier = 0;

        int score = 0;
        int count = 0;
        if (chosenTile != null)
            for (int i = 0; i < chosenTile.length; i++) {

                Tile t = chosenTile[i];

                // add points for tile
                if (t != null) {
                    count++;
                    int concatColumn = firstColumn + i;
                    int concatRow = firstRow + i;

                    String position = null;

                    if (direction == 'A')
                        position = Board.concatInt(firstRow, concatColumn);

                    if (direction == 'D')
                        position = Board.concatInt(concatRow, firstColumn);

                    if (board.getSquareValue(position) != null) {
                        switch (board.getSquareValue(position)) {
                            case "TW":
                                isTripleWord = true;
                                multiplier += t.getScore();
                                break;
                            case "TL":
                                multiplier = multiplier + (t.getScore() * 3);
                                break;
                            case "DW":
                                isDoubleWord = true;
                                multiplier += t.getScore();
                                break;
                            case "DL":
                                multiplier = multiplier + (t.getScore() * 2);
                                break;
                            default:
                                multiplier += t.getScore();

                        }
                    } else {
                        multiplier += t.getScore();
                    }

                }

            }

        score += multiplier;

        if (movesMade > 1) {
            //check if connection is only once on first or last position
            boolean extremityConnection = false;
            if (connectionIncrement == 1 && (((boardConnectionRow[connectionIncrement] + 1) == firstRow) || ((boardConnectionRow[connectionIncrement] - 1) == firstRow) || ((boardConnectionRow[connectionIncrement] + 1) == lastRow) || ((boardConnectionRow[connectionIncrement] - 1) == lastRow))) {
                extremityConnection = true;
            }
            if (connectionIncrement == 1 && (((boardConnectionColumn[connectionIncrement] + 1) == firstColumn) || ((boardConnectionColumn[connectionIncrement] - 1) == firstColumn) || ((boardConnectionColumn[connectionIncrement] + 1) == lastColumn) || ((boardConnectionColumn[connectionIncrement] - 1) == lastColumn))) {
                extremityConnection = true;
            }

            // adds up words made by connections with existing tiles; note if the connections are only at the first or last tile of the word, in different directions, then a new word isnt created so the points of the connection are not added.
            if (!extremityConnection && connectionIncrement >= 1) {
                for (int i = 1; i <= connectionIncrement; i++) {
                    if (direction == 'D') {
                        if (board.getBoard()[boardConnectionRow[i]][boardConnectionColumn[i] + 1] != null) {
                            score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'r');
                        }

                        if (board.getBoard()[boardConnectionRow[i]][boardConnectionColumn[i] - 1] != null) {
                            score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'l');
                        }
                    } else if (direction == 'A') {
                        if (board.getBoard()[boardConnectionRow[i] + 1][boardConnectionColumn[i]] != null) {
                            score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'd');
                        }
                        if (board.getBoard()[boardConnectionRow[i] - 1][boardConnectionColumn[i] - 1] != null) {
                            score += calculateConnectedTileScore(boardConnectionRow[i], boardConnectionColumn[i], 'u');
                        }
                    }
                }
            }

            // adding on intersection letter points if the intersection comes at the start or the end of the word
            if(extremityIntersection){
                if(extremityIntersectionFirst){
                    score += board.getBoard()[firstRow][firstColumn].getScore();
                } else {
                    score += board.getBoard()[lastRow][lastColumn].getScore();
                }
            }
        }

        // adding word multipliers
        if (isDoubleWord)
            score *= 2;

        if (isTripleWord)
            score *= 3;

        if (count == 7) {
            score += 50;
        }

        return score;
    }

    public int calculateConnectedTileScore(int row, int column, char direction) { // direction: u = up etc.
        int score = 0;

        if (board.getBoard()[row][column] != null) {
            int i = 0;
            if (direction == 'u') {
                while (board.getBoard()[row - i][column] != null) {
                    score += board.getBoard()[row - i][column].getScore();
                    i++;
                    if (row - i < 0 || row - i > 14)
                        break;
                }
            }
            if (direction == 'd') {
                while (board.getBoard()[row + i][column] != null) {
                    score += board.getBoard()[row + i][column].getScore();
                    i++;
                    if (row + i < 0 || row + i > 14)
                        break;
                }
            }
            if (direction == 'l') {
                while (board.getBoard()[row][column - i] != null) {
                    score += board.getBoard()[row][column - i].getScore();
                    i++;
                    if (column - i < 0 || column - i > 14)
                        break;
                }
            }
            if (direction == 'r') {
                while (board.getBoard()[row][column + i] != null) {
                    score += board.getBoard()[row][column + i].getScore();
                    i++;
                    if (column + i < 0 || column + i > 14)
                        break;
                }
            }
        }
        return score;
    }
}
