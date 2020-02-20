package scrabble;

public class Board {

	static String scrabbleBoard[][] = new String [15][15]; //temp 2d array for board drawing implementation
	
	


	public void resetBoard() {
       initBoardScores();
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


	
}
