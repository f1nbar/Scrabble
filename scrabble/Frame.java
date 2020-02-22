package scrabble;

import java.util.LinkedList;

/**
 * Frame class which stores and allows manipulation of Player's frames.
 * 
 * @author Peter O'Donnell
 * 
 *
 */
public class Frame {

	public transient int FRAME_SIZE = 7;

	private LinkedList<Tile> frame = new LinkedList<Tile>(); // linked list to store tile objects

	/**
	 * Removes letter from frame
	 * 
	 * @param c,tile object to be removed
	 */
	public void removeLetter(Tile... c) { // This function removes the tiles from the frame
		for (int i = 0; i < c.length; i++) {
			frame.remove(c[i]);
		}
	}

	/**
	 * checks if letter is in frame or not
	 * 
	 * @param c,tile object to be checked
	 * @return true,if letter is in frame
	 * @return false, if letter is not in frame
	 */

	public boolean checkLetters(Tile... c) { // This checks if the letters are avalaible or not returning a suitable
												// boolean
		for (int i = 0; i < c.length; i++) {
			if (frame.indexOf(c[i]) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if frame is empty or not
	 * 
	 * @return true, if frame is empty
	 */

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
				result = "" + frame.get(i).getLetter();
			} else {
				result = result + ", " + frame.get(i).getLetter();
			}
		}
		result = ("[ " + result + " ]");
		return result;
	}

	/**
	 * fills frame with tile objects
	 * 
	 * @param t, Tile objects
	 * 
	 * @return true, if the fillFrame is successful
	 */

	public boolean fillFrame(Tile... t) {

		if (t.length <= FRAME_SIZE - frame.size()) {
			for (int i = 0; i < t.length; i++) {
				frame.add(t[i]);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * gets tile from frame using index
	 * 
	 * @param i,index of tile
	 * 
	 * @return Tile, returns tile at index i
	 */

	public Tile getTile(int i) { // returns letter in index i
		if (i >= 0 && i < frame.size()) {
			return frame.get(i);
		} else {
			throw new IllegalArgumentException("INDEX OUT OF BOUNDS:getTile");
		}
	}

	public Tile getTileFromChar(char letter) {
		for (int i = 0; i < frame.size(); i++) {
			if (frame.get(i).getLetter() == letter) {
				return frame.get(i);
			}
		}
		throw new IllegalArgumentException("Letter not found: getTileFromChar");
	}

	/**
	 * returns frames size
	 * 
	 * @return int,frame.size
	 */

	public int frameSize() { // returns size of frame
		return frame.size();
	}

	/**
	 * refills frame until reaches max capacity
	 * 
	 * @param p,Pool object where tiles are accessed from
	 */

	public void refill(Pool p) {
		while (frame.size() < 7) {
			this.fillFrame(p.randomTile());
		}
	}

}
