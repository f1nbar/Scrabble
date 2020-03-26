package scrabble;

public class Tile {

    private char letter;
    private int score;

    public Tile(char letter, int score) {
        this.setLetter(letter);
        this.setScore(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

}
