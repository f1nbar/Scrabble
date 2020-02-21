package scrabble;

import java.util.HashMap;

public class Board {

	private static char letterBoard [][] = new char[15][15];

	private static HashMap<Integer, String> tilePoints;

	public Board(){
		initBoardScores();
	}
	private void initBoardScores() {
        Board.tilePoints = new HashMap<Integer, String>();
        //Triple word
        tilePoints.put(00, "TW");
        tilePoints.put(70, "TW");
        tilePoints.put(07, "TW");
        tilePoints.put(14, "TW");
        tilePoints.put(140, "TW");
        tilePoints.put(147, "TW");
        tilePoints.put(714, "TW");
        tilePoints.put(1414, "TW");

        //Double letter
        tilePoints.put(51, "TL");
        tilePoints.put(91, "TL");
        tilePoints.put(15, "TL");
        tilePoints.put(55, "TL");
        tilePoints.put(95, "TL");
        tilePoints.put(135, "TL");
        tilePoints.put(19, "TL");
        tilePoints.put(59, "TL");
        tilePoints.put(99, "TL");
        tilePoints.put(139, "TL");
        tilePoints.put(513, "TL");
        tilePoints.put(913, "TL");

        //Double word
        tilePoints.put(11, "DW");
        tilePoints.put(22, "DW");
        tilePoints.put(33, "DW");
        tilePoints.put(44, "DW");
        tilePoints.put(113, "DW");
        tilePoints.put(212, "DW");
        tilePoints.put(311, "DW");
        tilePoints.put(410, "DW");
        tilePoints.put(131, "DW");
        tilePoints.put(122, "DW");
        tilePoints.put(113, "DW");
        tilePoints.put(104, "DW");
        tilePoints.put(1010, "DW");
        tilePoints.put(1111, "DW");
        tilePoints.put(1212, "DW");
        tilePoints.put(1313, "DW");

        //Double letter
        tilePoints.put(30, "DL");
        tilePoints.put(110, "DL");
        tilePoints.put(62, "DL");
        tilePoints.put(82, "DL");
        tilePoints.put(03, "DL");
        tilePoints.put(73, "DL");
        tilePoints.put(143, "DL");
        tilePoints.put(26, "DL");
        tilePoints.put(66, "DL");
        tilePoints.put(86, "DL");
        tilePoints.put(126, "DL");
        tilePoints.put(37, "DL");
        tilePoints.put(117, "DL");
        tilePoints.put(28, "DL");
        tilePoints.put(68, "DL");
        tilePoints.put(88, "DL");
        tilePoints.put(128, "DL");
        tilePoints.put(011, "DL");
        tilePoints.put(711, "DL");
        tilePoints.put(1411, "DL");
        tilePoints.put(612, "DL");
        tilePoints.put(812, "DL");
        tilePoints.put(314, "DL");
        tilePoints.put(1114, "DL");
    }

    public static String getSquareValue(int location) {
        if (Board.tilePoints.containsKey(location)) {
            return Board.tilePoints.get(location);
        } else {
            return null;
        }
    }	
	


	public void resetBoard() {
       initBoardScores();
	}

	public void displayBoard() {	
    
		System.out.println("                                           SCRABBLE                                            ");
		System.out.println("   _________________________________________________________________________________________   ");				
      for (int i = 14; i>= 0; i--) {

		for(int y = 0; y<15; y++)
		{
		int position = concatInt(i, y);  //concats i and y to one int "position" to use as key for hashmap 
		System.out.print("  |" );   
		if(letterBoard[i][y] != 0) {
			System.out.print(letterBoard[i][y]);
		}
		else if(tilePoints.get(position) == null) {
			System.out.print("  ");
		}
		else {
		System.out.print(tilePoints.get(position)); 
		}
		System.out.print(" "); 
		if(y == 14) {
			System.out.print("  |"); 
		}   
		}
		System.out.println();
        System.out.println("  |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|  ");
		
       }		
	}

	public void placeTile(int x,int y, Tile tile) {
        
	}

	public int numTiles() {
		int num = 0;

		return num;
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		board.displayBoard();
	}
	
	public int concatInt(int a,int b) { //concats integers rather than adding them
		String s = "" + a + b;
		int pos = Integer.parseInt(s);
		return pos;
	}


	
}
