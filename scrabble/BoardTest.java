package scrabble;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class BoardTest {
	
	Board board = new Board();
	
	@Test
	public void testResetBoard() {
		for (int i = 0; i < 10; i++) {
			board.placeTile();
		} 
		board.resetBoard();
		assertEquals(0, board.numTiles());
	}
	
	@Test
	public void testDisplayBoard() {
		         
	}
	

}
