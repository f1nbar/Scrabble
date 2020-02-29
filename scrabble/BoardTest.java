package scrabble;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoardTest {
 
	Board Board = new Board();

	@Test
	public void testResetBoard() {

		Board board = new Board();
		Player player = new Player("Test");
		board.placeTile(7, 7, player, new Tile('S', 100), 2);
		board.placeTile(7, 8, player, new Tile('S', 100), 2);
		board.resetBoard();
		System.out.println(board.numTiles());
		assertEquals(0, board.numTiles());
	}

	@Test
	public void testDisplayBoard() {
		Board board = new Board();
		System.out.println(board.toString());
		Player player = new Player("Test");
		board.placeTile(7, 7, player, new Tile('S', 100), 2);
		System.out.println(board.toString());		
	}

	@Test
	public void testGetSquareValue() {

		Board board = new Board();
		
		// triple word tests
		assertEquals("TW", board.getSquareValue(00));
		assertEquals("TW", board.getSquareValue(14));
		assertEquals("TW", board.getSquareValue(147));
		assertEquals("TW", board.getSquareValue(714));
		assertEquals("TW", board.getSquareValue(07));

		// double word tests
		assertEquals("DW", board.getSquareValue(11));
		assertEquals("DW", board.getSquareValue(22));
		assertEquals("DW", board.getSquareValue(33));
		assertEquals("DW", board.getSquareValue(311));
		assertEquals("DW", board.getSquareValue(1313));

		// triple letter tests
		assertEquals("TL", board.getSquareValue(91));
		assertEquals("TL", board.getSquareValue(55));
		assertEquals("TL", board.getSquareValue(135));
		assertEquals("TL", board.getSquareValue(19));
		assertEquals("TL", board.getSquareValue(59));
		assertEquals("TL", board.getSquareValue(913));

		// double letter tests
		assertEquals("DL", board.getSquareValue(62));
		assertEquals("DL", board.getSquareValue(03));
		assertEquals("DL", board.getSquareValue(117));
		assertEquals("DL", board.getSquareValue(28));
		assertEquals("DL", board.getSquareValue(1114));

		// regular tiles
		assertEquals(null, board.getSquareValue(01));
		assertEquals(null, board.getSquareValue(100));
		assertEquals(null, board.getSquareValue(1115));
		assertEquals(null, board.getSquareValue(613));
		assertEquals(null, board.getSquareValue(38));
		
		//middle test
		assertEquals("*", board.getSquareValue(77));
	}

	@Test
	public void testMoveLegal() {

	}
	
	@Test
	public void testPlaceTile() {

		Board board = new Board();
		Frame frame = new Frame();
		Player player = new Player("Test");
		assertEquals(0, board.numTiles());
		board.placeTile(7, 7, player, new Tile('S', 100), 1);
		System.out.println(board.numTiles());
		assertEquals(1, board.numTiles());
		

	}



}
