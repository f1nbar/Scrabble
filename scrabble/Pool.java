package scrabble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Pool class which simulates the bag containing all 100 Scrabble tiles
 * 
 * @author Finbar Ó Deaghaidh - 18410234
 *
 */

public class Pool {

	// create a Hashmap for pool, using the tile letter as a key and number of tiles
	// as the value

//	static HashMap<Tile, Integer> letters = new HashMap<>();

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

//	private static Tile[] letters = { A, A, A, A, A, A, A, A, A, B, B, C, C, D, D, D, D, E, E, E, E, E, E, E, E, E, E,
//			E, E, F, F, G, G, G, H, H, I, I, I, I, I, I, I, I, I, J, K, L, L, L, L, M, M, N, N, N, N, N, N, O, O, O, O,
//			O, O, O, O, P, P, Q, R, R, R, R, R, R, S, S, S, S, T, T, T, T, T, T, U, U, U, U, V, V, W, W, X, Y, Y, Z,
//			Blank, Blank };
	
	private static LinkedList<Tile> letters = new LinkedList<Tile>();

	public Pool() {
		this.fillPool();
	}

	/**
	 * Fills the letters Hashmap with Object tile as the key and their quantity as
	 * the value
	 */
	public void fillPool() {
		
		for(int i = 0;i<100;i++) {
			
		if(i < 9)
		letters.add(A);
		else if(i < 11)
			letters.add(B);
		else if(i < 13)
			letters.add(C);
		else if(i < 17)
			letters.add(D);
		else if(i < 29)
			letters.add(E);
		else if(i < 31)
			letters.add(F);
		else if(i < 34)
			letters.add(G);
		else if(i < 36)
			letters.add(H);
		else if(i < 45)
			letters.add(I);
		else if(i < 46)
			letters.add(J);
		else if(i < 47)
			letters.add(K);
		else if(i < 51)
			letters.add(L);
		else if(i < 53)
			letters.add(M);
		else if(i < 59)
			letters.add(N);
		else if(i < 67)
			letters.add(O);
		else if(i < 69)
			letters.add(P);
		else if(i < 70)
			letters.add(Q);
		else if(i < 76)
			letters.add(R);
		else if(i < 80)
			letters.add(S);
		else if(i < 86)
			letters.add(T);
		else if(i < 90)
			letters.add(U);
		else if(i < 92)
			letters.add(V);
		else if(i < 94)
			letters.add(W);
		else if(i < 95)
			letters.add(X);
		else if(i < 97)
			letters.add(Y);
		else if(i < 98)
			letters.add(Z);
		else if(i < 100)
			letters.add(Blank);
		
		
		}
		
		Random ran = new Random();
		
		Tile temp;
		
		for(int i = 0;i<letters.size();i++) {
			int randomIndex = ran.nextInt(letters.size());
			temp =letters.set(i, letters.get(randomIndex));
			letters.set(randomIndex,temp);
			
		}
		

	}
	
	

	/**
	 * Checks if the pool has any tiles or not
	 * 
	 * @return true if the pool is empty
	 */
	public boolean emptyPool() {
		// TODO think about what should happen when pool is emptied
		return checkNumTiles() == 0;
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
		return letters.size();
	}

	/**
	 * Randomly picks a tile from the hashmap letters
	 * 
	 * @return selected, tile object
	 */
	public Tile randomTile() {
		
		return  letters.removeFirst();      
		

	}

}
