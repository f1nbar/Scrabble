package scrabble;

import java.util.LinkedList;

/**
 * Frame class which stores and allows manipulation of Player's frames.
 * 
 * @author Peter O'Donnell
 *
 */
public class Frame {

	public transient  int FRAME_SIZE = 7;

	LinkedList<Tile> frame = new LinkedList<Tile>(); // linked list to store tile objects

	public void removeLetter(Tile... c) { // This function removes the tiles from the frame
		for (int i = 0; i < c.length; i++) {
			frame.remove(c[i]);
		}
	}

	public boolean checkLetters(Tile... c) { // This checks if the letters are avalaible or not returning a suitable
												// boolean
		for (int i = 0; i < c.length; i++) {
			if (frame.indexOf(c[i]) == -1) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() { // returns true if frame is empty
		if (frame.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() { // returns the string
		String result = null;
		for (int i = 0; i < frame.size(); i++) {
			if (result == null) {
				result = frame.get(i).getLetter();
			} else {
				result = result + frame.get(i).getLetter();
			}
		}
		return result;
	}

	public boolean fillFrame(Tile... t) { // fills the frame with variable amount of arguments

		if (t.length <= FRAME_SIZE - frame.size()) {
			for (int i = 0; i < t.length; i++) {
				frame.add(t[i]);
			}
			return true;
		} else {
			return false;
		}
	}

	public Tile getTile(int i) { // returns letter in index i
		if (i >= 0 && i < frame.size()) {
			return frame.get(i);
		} else {
			throw new IllegalArgumentException("INDEX OUT OF BOUNDS:getTile");
		}
	}

}
