package scrabble;

/**
 * Player class which stores and manipulates Player name and score.
 * 
 * @author Conor Knowles - 18398031
 */
public class Player {

	// Initializing variables
	private String name;
	private int score;
	private Frame playerFrame;
	private boolean turn;

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
		if (!(name.matches(nameRegex)) || name.isEmpty() || name.contentEquals(" ")) {
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

	/**
	 * Resets score to 0 and asks for new player name.
	 * 
	 * @param name String to store as player name
	 */
	public void reset(String name) {
		setName(name);
		this.score = 0;
	}

	/**
	 * Increases player's score.
	 * 
	 * @param points int to increase score by
	 */
	public void increaseScore(int points) {
		sanitizeScore(points);
		this.score += points;
	}
	
	public boolean getTurn() {
		return turn;
	}
	public void setTurn(boolean turn){
		this.turn = turn;
	}
}
