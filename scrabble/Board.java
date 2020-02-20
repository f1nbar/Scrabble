package scrabble;

public class Board {

	static String scrabbleBoard[][] = new String [15][15]; //temp 2d array for board drawing implementation
	
	
	public void fillBoard() {

		for (int i = 0; i < scrabbleBoard.length; i++) {
			for (int j = 0; j < scrabbleBoard.length; j++) {
				scrabbleBoard[i][j] = "  ";
			}
		}
        //Triple Words
		scrabbleBoard[0][0] = "TW";
		scrabbleBoard[0][14] = "TW";
		scrabbleBoard[14][14] = "TW";
		scrabbleBoard[14][0] = "TW";
		scrabbleBoard[0][7] = "TW";
		scrabbleBoard[7][0] = "TW";
		
		scrabbleBoard[7][7] = "*"; //Star in centre
		
		//Double Letters
		scrabbleBoard[0][3] = "DL";
		scrabbleBoard[0][11] = "DL";
		scrabbleBoard[14][3] = "DL";
		scrabbleBoard[14][11] = "DL";
		scrabbleBoard[3][14] = "DL";
		scrabbleBoard[11][14] = "DL";
		scrabbleBoard[3][0] = "DL";
		scrabbleBoard[11][0] = "DL";
	

		


		
		
	
	}


	public void resetBoard() {
       //TODO Call function to fill board
	}

	public void displayBoard(String board[][]) {	
    
		System.out.println("   _________________________________________________________________________________________   ");				
      for (int i = 14; i>= 0; i--) {

		for(int y = 0; y<15; y++)
		{
		System.out.print("  |" );   
		System.out.print(board[i][y]); 
		System.out.print(" ");
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
		board.fillBoard();
		board.displayBoard(scrabbleBoard);


}
}
