package scrabble;

/**
 * Tile class which creates Tile objects for the game Scrabble
 * @author Finbar Ó Deaghaidh - 18410234
 *
 */

public class Tile {
	
	private String letter;
	private int score;
	
public Tile(String letter, int score) {
		
		this.setLetter(letter);
		this.setScore(score);
	}

public int getScore() {
	return score;
}

public void setScore(int score) {
	this.score = score;
}

public String getLetter() {
	return letter;
}

public void setLetter(String letter) {
	this.letter = letter;
}



}
