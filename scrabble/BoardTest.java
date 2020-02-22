//package scrabble;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//
//public class BoardTest {
//
//	//Board board = new Board();
//	
//
//	@Test
//	public void testResetBoard() {
//		for (int i = 0; i < 10; i++) {
//		//	board.placeTile();
//		}
//		board.resetBoard();
//		assertEquals(0, board.numTiles());
//	}
//
//	@Test
//	public void testDisplayBoard() {
//       board.displayBoard();
//  //     assertEquals("the board", board.displayBoard());
//	}
//	
//
//	@Test
//	public void testGetSquareValue() {
//		// triple word tests
//		assertEquals("TW", Board2.getSquareValue(00));
//		assertEquals("TW", Board2.getSquareValue(14));
//		assertEquals("TW", Board2.getSquareValue(147));
//		assertEquals("TW", Board2.getSquareValue(714));
//		assertEquals("TW", Board2.getSquareValue(07));
//
//		// double word tests
//		assertEquals("DW", Board2.getSquareValue(11));
//		assertEquals("DW", Board2.getSquareValue(22));
//		assertEquals("DW", Board2.getSquareValue(33));
//		assertEquals("DW", Board2.getSquareValue(311));
//		assertEquals("DW", Board2.getSquareValue(1313));
//
//		// triple letter tests
//		assertEquals("TL", Board2.getSquareValue(91));
//		assertEquals("TL", Board2.getSquareValue(55));
//		assertEquals("TL", Board2.getSquareValue(135));
//		assertEquals("TL", Board2.getSquareValue(19));
//		assertEquals("TL", Board2.getSquareValue(59));
//		assertEquals("TL", Board2.getSquareValue(913));
//
//		// double letter tests
//		assertEquals("DL", Board2.getSquareValue(62));
//		assertEquals("DL", Board2.getSquareValue(03));
//		assertEquals("DL", Board2.getSquareValue(117));
//		assertEquals("DL", Board2.getSquareValue(28));
//		assertEquals("DL", Board2.getSquareValue(1114));
//
//		// regular tiles
//		assertEquals(null, Board2.getSquareValue(01));
//		assertEquals(null, Board2.getSquareValue(100));
//		assertEquals(null, Board2.getSquareValue(1115));
//		assertEquals(null, Board2.getSquareValue(613));
//		assertEquals(null, Board2.getSquareValue(38));
//	}
//
//	@Test
//	public void testMoveLegal() {
//		//TODO
//	
//
//}
//}
