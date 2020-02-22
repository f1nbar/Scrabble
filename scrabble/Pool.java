package scrabble;

import java.util.*;

import org.junit.runners.Parameterized.Parameter;

/**
 * Pool class which simulates the bag containing all 100 Scrabble tiles
 * 
 * @author Finbar Ó Deaghaidh - 18410234
 *
 */

public class Pool {

	// create a Hashmap for pool, using the tile letter as a key and number of tiles
	// as the value

	static HashMap<Tile, Integer> letters = new HashMap<Tile, Integer>();

	int sum;
	// creating tile objects with letter and points per tile
	private static Tile A = new Tile('A', 1);
	private static Tile B = new Tile('B', 3);
	private static Tile C = new Tile('C', 3);
	private static Tile D = new Tile('D', 2);
	private static Tile E = new Tile('E', 1);
	private static Tile F = new Tile('F', 4);
	private static Tile G = new Tile('G', 2);
	private static Tile H = new Tile('H', 2);
	private static Tile I = new Tile('I', 1);
	private static Tile J = new Tile('J', 8);
	private static Tile K = new Tile('K', 5);
	private static Tile L = new Tile('L', 4);
	private static Tile M = new Tile('M', 3);
	private static Tile N = new Tile('N', 6);
	private static Tile O = new Tile('O', 1);
	private static Tile P = new Tile('P', 3);
	private static Tile Q = new Tile('Q', 10);
	private static Tile R = new Tile('R', 1);
	private static Tile S = new Tile('S', 1);
	private static Tile T = new Tile('T', 1);
	private static Tile U = new Tile('U', 1);
	private static Tile V = new Tile('V', 4);
	private static Tile W = new Tile('W', 4);
	private static Tile X = new Tile('X', 8);
	private static Tile Y = new Tile('Y', 4);
	private static Tile Z = new Tile('Z', 10);
	private static Tile Blank = new Tile('_', 0);

	/**
	 * Fills the letters Hashmap with Object tile as the key and their quantity as
	 * the value
	 */
	public void fillPool() {

		letters.put(A, 9);
		letters.put(B, 2);
		letters.put(C, 2);
		letters.put(D, 4);
		letters.put(E, 12);
		letters.put(F, 2);
		letters.put(G, 3);
		letters.put(H, 2);
		letters.put(I, 9);
		letters.put(J, 1);
		letters.put(K, 1);
		letters.put(L, 4);
		letters.put(M, 2);
		letters.put(N, 6);
		letters.put(O, 8);
		letters.put(P, 2);
		letters.put(Q, 1);
		letters.put(R, 6);
		letters.put(S, 4);
		letters.put(T, 6);
		letters.put(U, 4);
		letters.put(V, 2);
		letters.put(W, 2);
		letters.put(X, 1);
		letters.put(Y, 2);
		letters.put(Z, 1);
		letters.put(Blank, 2);

	}

	/**
	 * Checks if the pool has any tiles or not
	 * 
	 * @return false if the pool is empty
	 * @return true if pool isn't empty
	 */
	public boolean checkEmptyPool() {
		if (checkNumTiles() == 0) {
			return false;
		}
		// TODO think about what should happen when pool is emptied
		else {
			return true;
		}
	}

	/**
	 * Reinitialize Hashmap and then fill it, thus resetting the pool
	 */
	public void resetPool() {
		HashMap<Tile, Integer> letters = new HashMap<Tile, Integer>();
		fillPool();
	}

	/**
	 * @param check, tile object to be checked
	 * @return Score of the tile
	 */
	public int checkValue(Tile check) {
		return check.getScore();
	}

	/**
	 * 
	 * @param check, tile object to be checked
	 * @return Tile letter
	 */
	public char checkLetter(Tile check) {
		return check.getLetter();
	}

	/**
	 * Checks the number of tiles in the pool Loop that sums all int values in
	 * hashmap letters
	 * 
	 * @return sum
	 */
	public int checkNumTiles() {
		sum = 0;
		for (int value : letters.values()) {
			sum += value;
		}
		return sum;
	}

	/**
	 * Randomly picks a tile from the hashmap letters
	 * 
	 * @return selected, tile object
	 */
	public Tile randomTile() {
		// converts the letters hashmap in order to randomly select by the key, in this
		// case the letters
		List<Tile> keys = new ArrayList<Tile>(letters.keySet());
		// creating a tile to temporarily store the randomly selected tile and randomly
		// chooses a Tile from the list keys
		Tile selected = keys.get(new Random().nextInt(keys.size()));
		// int to store the amount of the tile selected in the pool and decreases by one
		// as the tile is taken out by a player
		int decrement = letters.get(selected) - 1;
		// removes all cases of the tile and puts back in the ones not taken (n -1)
		letters.remove(selected);
		if (decrement > 0) {
			letters.put(selected, decrement);
		}
		return selected;
	}

}
