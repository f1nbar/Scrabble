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
		board.toString();
	}

	@Test
	public void testGetSquareValue() {

		// triple word tests
		assertEquals("TW", scrabble.Board.getSquareValue(00));
		assertEquals("TW", scrabble.Board.getSquareValue(14));
		assertEquals("TW", scrabble.Board.getSquareValue(147));
		assertEquals("TW", scrabble.Board.getSquareValue(714));
		assertEquals("TW", scrabble.Board.getSquareValue(07));

		// double word tests
		assertEquals("DW", scrabble.Board.getSquareValue(11));
		assertEquals("DW", scrabble.Board.getSquareValue(22));
		assertEquals("DW", scrabble.Board.getSquareValue(33));
		assertEquals("DW", scrabble.Board.getSquareValue(311));
		assertEquals("DW", scrabble.Board.getSquareValue(1313));

		// triple letter tests
		assertEquals("TL", scrabble.Board.getSquareValue(91));
		assertEquals("TL", scrabble.Board.getSquareValue(55));
		assertEquals("TL", scrabble.Board.getSquareValue(135));
		assertEquals("TL", scrabble.Board.getSquareValue(19));
		assertEquals("TL", scrabble.Board.getSquareValue(59));
		assertEquals("TL", scrabble.Board.getSquareValue(913));

		// double letter tests
		assertEquals("DL", scrabble.Board.getSquareValue(62));
		assertEquals("DL", scrabble.Board.getSquareValue(03));
		assertEquals("DL", scrabble.Board.getSquareValue(117));
		assertEquals("DL", scrabble.Board.getSquareValue(28));
		assertEquals("DL", scrabble.Board.getSquareValue(1114));

		// regular tiles
		assertEquals(null, scrabble.Board.getSquareValue(01));
		assertEquals(null, scrabble.Board.getSquareValue(100));
		assertEquals(null, scrabble.Board.getSquareValue(1115));
		assertEquals(null, scrabble.Board.getSquareValue(613));
		assertEquals(null, scrabble.Board.getSquareValue(38));
		
		//middle test
		assertEquals("*", scrabble.Board.getSquareValue(77));
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
