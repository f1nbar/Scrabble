package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PoolTest {

	Player player = new Player("Test");
	Pool testPool = new Pool();
	Tile testTileX = new Tile("X", 10);

	// Testing what's accepted as a name.
	@Test
	public void validInputName() {
		player.setName("Conor");
		assertEquals("Conor", player.getName());
		player.setName("Conor Knowles");
		assertEquals("Conor Knowles", player.getName());
	}

	@Test
	public void testNameNumbers() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor1");
		});
	}

	@Test
	public void testNameSymbols() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor$");
		});
	}

	@Test
	public void testNameThree() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor James Knowles");
		});
	}

	@Test
	public void testNameSpaces() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("   ");
		});
	}

	@Test
	public void testNameEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("");
		});
	}

	// Score tests
	@Test
	public void testScoreIncrement() {
		player.increaseScore(10);
		assertEquals(10, player.getScore());
		player.increaseScore(15);
		assertEquals(25, player.getScore());
		player.increaseScore(0);
		assertEquals(25, player.getScore());
	}

	@Test
	public void testScoreNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.increaseScore(-5);
		});
	}

	@Test
	public void testScoreLarge() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.increaseScore(10000);
		});
	}

	// Reset method test
	@Test
	public void testReset() {
		player.setName("Conor");
		player.increaseScore(20);
		assertEquals("Conor", player.getName());
		assertEquals(20, player.getScore());

		player.reset("Test");
		assertEquals("Test", player.getName());
		assertEquals(0, player.getScore());
	}

	@Test
	public void poolFilled() throws Exception { // calls pool fill method followed by checking the number of tiles

		testPool.fillPool();
		assertEquals(100, testPool.checkNumTiles()); // checks if pool contains the expected 100 tiles, throws
														// excep
	}

	@Test
	public void poolNotEmpty() throws Exception { // verifies that the pool empty method is functioning correctly when
													// the pool isn't empty, throws exception if not

		testPool.fillPool();
		Tile testTileX = new Tile("X", 10); // creates test tile object
		// fills pool
		assertEquals(true, testPool.checkEmptyPool()); // should return false as pool contains 100 tiles

	}

	@Test
	public void poolEmpty() throws Exception { // verifies that the pool empty method is functioning correctly when the
												// pool
		// is empty, throws exception if not
		assertEquals(false, testPool.checkEmptyPool()); // should return false as pool contains 100 tiles
	}

	@Test
	public void valueCheck() throws Exception { // should return the value of the test tile initialized in this testing
												// class
		assertEquals(10, testPool.checkValue(testTileX)); // value of the test tile should be 10
	}

	@Test
	public void removeTiles() throws Exception { // checks to see if tiles can be removed from the pool using the random
													// tile method
		testPool.fillPool();
		testPool.randomTile(); // after filling the pool and running the random tile method once there should
								// be 99 tiles remaining in the pool
		assertEquals(99, testPool.checkNumTiles());

	}

	@Test
	public void poolReset() throws Exception { // checks if pool is reset correctly by filling the pool, removing 10
												// tiles,
		testPool.fillPool();
		for (int i = 0; i < 10; i++) { // for loop iterates 10 times, calling the random tile method each time in
										// order to remove 10 tiles
			testPool.randomTile();
		}
		testPool.resetPool(); // reset pool method should restore pool to original size
		assertEquals(100, testPool.checkNumTiles()); // checks if there are 100 tiles in the pool after the reset,
														// throws exception if not
	}

	@Test
	public void removeAllTiles() throws Exception { // removes all tiles in the pool by running the random tile method
													// simulate all the tiles being taken out by hand
		testPool.fillPool();
		for (int i = 0; i < 100; i++) {
			testPool.randomTile();
		}
		assertEquals(false, testPool.checkEmptyPool()); // pool should be empty, throws exception if not

	}

	@Test
	public void getAndSetTile() throws Exception { // tests the tile class getters and setters, sets letter to F and
													// score to 47 then calls getters using assertEqual to verify them
		testTileX.setLetter("F");
		testTileX.setScore(47);
		assertEquals(47, testTileX.getScore());

	}

}
