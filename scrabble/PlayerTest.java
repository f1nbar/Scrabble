package scrabble;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PlayerTest {

	/*-----Player.java Tests-----*/
	Player player = new Player("Test");
	Pool testPool = new Pool();
	Tile testTileX = new Tile('X', 10);

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
	public void testNameSymbols() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor$");
		});
	}

	@Test
	public void testNameThree() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor James Knowles");
		});
	}

	@Test
	public void testNameSpaces() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName(" ");
		});
	}

	@Test
	public void testNameMultipleSpaces() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("    ");
		});
	}

	@Test
	public void testSecondNameNumbers() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor 123");
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

	/*-----Frame.java Tests-----*/
	@Test
	public void testIsEmptyTrue() {
		Frame frame = new Frame();
		assertEquals(true, frame.isEmpty());
	}

	@Test
	public void testIsEmptyFalse() {
		Frame frame = new Frame();
		frame.fillFrame(new Tile('X', 10));
		assertEquals(false, frame.isEmpty());
	}

	@Test
	public void testToStringFullarray() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		frame.fillFrame(tileOne, tileTwo, tileThree);
		assertEquals("X,Y,Z", frame.toString());
	}

	@Test
	public void testToStringEmptyArray() {
		Frame frame = new Frame();
		assertEquals(null, frame.toString());
	}

	@Test
	public void testFillArrayOneLetter() {
		Frame frame = new Frame();
		Tile tile = new Tile('X', 10);
		frame.fillFrame(tile);
		assertEquals('X', frame.toString());
	}

	@Test
	public void testFillArrayThreeLetters() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		frame.fillFrame(tileOne, tileTwo, tileThree);
		assertEquals("X,Y,Z", frame.toString());
	}

	/*
	 * Tests which check whether removing letters work correctly.
	 */
	@Test
	public void testRemoveLetterOneLetter() {
		Frame frame = new Frame();
		Tile tile = new Tile('X', 10);
		frame.fillFrame(tile);
		frame.removeLetter(tile);
		assertEquals(true, frame.isEmpty());
	}

	@Test
	public void testRemoveLetterThreeLetters() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		frame.fillFrame(tileOne, tileTwo, tileThree);
		frame.removeLetter(tileThree, tileOne, tileTwo);
		assertEquals(true, frame.isEmpty());
	}

	@Test
	public void testrefill() {
		Pool p = new Pool();
		Frame frame = new Frame();
		frame.refill(p);
	}

	/*
	 * Tests which check whether adding letters work correctly.
	 */
	@Test
	public void testCheckLetterOneLetterTrue() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		frame.fillFrame(tileOne);
		assertEquals(true, frame.checkLetters(tileOne));
	}

	@Test
	public void testCheckLetterThreeLettersTrue() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		frame.fillFrame(tileOne, tileTwo, tileThree);
		assertEquals(true, frame.checkLetters(tileOne, tileTwo, tileThree));
	}

	@Test
	public void testCheckLetterOneLetterFalse() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		frame.fillFrame(tileOne);
		assertEquals(false, frame.checkLetters(tileTwo));
	}

	@Test
	public void testCheckLetterThreeLettersFalse() {
		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		Tile tile4 = new Tile('O', 46);
		frame.fillFrame(tileOne, tileTwo, tileThree);
		assertEquals(false, frame.checkLetters(tileOne, tileTwo, tile4));
	}

	@Test
	public void testGetTile() {

		Frame frame = new Frame();
		Tile tileOne = new Tile('X', 10);
		Tile tileTwo = new Tile('Y', 6);
		Tile tileThree = new Tile('Z', 3);
		frame.fillFrame(tileOne, tileTwo, tileThree);

		assertEquals(tileOne, frame.getTile(0));
		assertEquals(tileTwo, frame.getTile(1));
		assertEquals(tileThree, frame.getTile(2));
	}

	/*-----Pool Test-----*/
	@Test
	public void poolFilled() throws Exception {
		testPool.fillPool();
		assertEquals(100, testPool.checkNumTiles());
	}

	@Test
	public void poolNotEmpty() throws Exception {
		testPool.fillPool();
		assertEquals(true, testPool.checkEmptyPool());
	}

	@Test
	public void poolEmpty() throws Exception {
		assertEquals(false, testPool.checkEmptyPool());
	}

	@Test
	public void valueCheck() throws Exception {
		assertEquals(10, testPool.checkValue(testTileX));
	}

	@Test
	public void removeTiles() throws Exception {
		testPool.fillPool();
		testPool.randomTile();
		assertEquals(99, testPool.checkNumTiles());
	}

	@Test
	public void poolReset() throws Exception {
		testPool.fillPool();
		for (int i = 0; i < 10; i++) {
			testPool.randomTile();
		}
		testPool.resetPool();
		assertEquals(100, testPool.checkNumTiles());
	}

	@Test
	public void removeAllTiles() throws Exception {
		testPool.fillPool();
		for (int i = 0; i < 100; i++) {
			testPool.randomTile();
		}
		assertEquals(false, testPool.checkEmptyPool());
	}

	@Test
	public void getAndSetTile() throws Exception {
		testTileX.setLetter('F');
		testTileX.setScore(47);
		assertEquals(47, testTileX.getScore());
	}

}