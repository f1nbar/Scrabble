package scrabble;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Frame class which stores and allows manipulation of Player's frames.
 * 
 * @author Peter O'Donnell
 * 
 *
 */
public class Frame {

	public static int FRAME_SIZE = 7;

	private LinkedList<Tile> frame = new LinkedList<>(); // linked list to store tile objects

	/**
	 * Removes letter from frame
	 * 
	 * @param c,tile object to be removed
	 */
	public void removeLetter(Tile... c) { // This function removes the tiles from the frame
		for (Tile tile : c) {

			if (checkLetters(tile))
				frame.remove(tile);
		}
	}

	/**
	 * checks if letter is in frame or not
	 * 
	 * @param c,tile object to be checked
	 * @return true,if letter is in frame
	 */

	public boolean checkLetters(Tile c) { // This checks if the letters are avalaible or not returning a suitable
												// boolean
		for (Tile tile : frame) {
			if (tile.getLetter() == c.getLetter()) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * checks if frame is empty or not
	 * 
	 * @return true, if frame is empty
	 */

	public boolean isEmpty() { // returns true if frame is empty
		return frame.isEmpty();
	}

	@Override
	public String toString() { // returns the string
		StringBuilder result = null;
		for (Tile tile : frame) {
			if (result == null) {
				result = new StringBuilder("" + tile.getLetter());
			} else {
				result.append(", ").append(tile.getLetter());
			}
		}
		result = new StringBuilder(("[ " + result + " ]"));
		return result.toString();
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
			Collections.addAll(frame, t);
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
		for (Tile tile : frame) {
			if (tile.getLetter() == letter) {
				return tile;
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
		while (frame.size() < 7 && !p.emptyPool()) {
			this.fillFrame(p.randomTile());
		}
		if(p.emptyPool()){
			System.out.println("Pool is empty.");
		}
	}

	public void addTile(Tile tile){
		if(frame.size() < 7) {
			frame.add(tile);
		}
	}

	public LinkedList<Tile> getFrame(){
		return frame;
	}
}
