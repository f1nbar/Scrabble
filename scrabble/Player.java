package scrabble;

public class Player {

    // Initializing variables
    private String name;
    private int score;
    private Frame playerFrame;
    public static int turn;
    public static final int playerOneTurn = 0;
    public static final int playerTwoTurn = 1;

    private final String nameRegex = "[a-zA-Z]*(\\s[a-zA-Z]*)?"; // checks to see if the string contains a word made of
    // only alphabet characters for a maximum of two
    // words. Will return true if the input is valid.

    // Constructor
    public Player(String name) {
        sanitizeName(name);

        this.name = name;
        this.score = 0;
        playerFrame = new Frame();
    }

    // getters and setters
    public void setName(String name) {
        sanitizeName(name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public Frame getFrame() {
        return playerFrame;
    }

    // Sanitizers
    private void sanitizeName(String name) {
        if (!name.equals("1") && !name.equals("2") && (!(name.matches(nameRegex)) || name.isEmpty() || name.contentEquals(" "))) {
            throw new IllegalArgumentException("Incorrect format for name, can only contain letters A-Z a-z.");
        }
    }

    private void sanitizeScore(int increment) {
        if (increment < 0) {
            throw new IllegalArgumentException("Cannot increase score by a negative value.");
        }
        if (this.score + increment >= 830) {
            throw new IllegalArgumentException("Total score cannot be outside feasible point range.");
        }
    }

    public void reset(String name) {
        setName(name);
        this.score = 0;
    }

    public void increaseScore(int points) {
        sanitizeScore(points);
        this.score += points;
    }
    public void decreaseScore(int points) {
        sanitizeScore(points);
        this.score -= points;
    }

    public static void changeTurn() {
        if (turn == playerOneTurn) {
            turn = playerTwoTurn;
        } else {
            turn = playerOneTurn;
        }
    }
}
