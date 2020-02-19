package scrabble;

public class Board {

	char scrabbleBoard[][] = new char [15][15]; //temp 2d array for board drawing implementation
	
	

	public void resetBoard() {

	}

	public void displayBoard() {
		
       //test code for displaying board
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
			   scrabbleBoard[i][j] = 'x';
				
			}
			
		}

	
		System.out.println("   _________________________________________________________________________________________   ");				
      for (int i = 14; i>= 0; i--) {

		for(int y = 0; y<15; y++)
		{
		System.out.print("  |" );   
		System.out.print(scrabbleBoard[i][y]);
		System.out.print("  ");
		}   
		System.out.println();
        System.out.println("  |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|  ");
		
       }		
	}

	public void placeTile() {

	}

	public int numTiles() {
		int num = 0;

		return num;
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.displayBoard();


}
}
