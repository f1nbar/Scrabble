package scrabble;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

public class ScabbleTest {
  
	
  String testInput;
  Scanner scan = new Scanner(testInput);
  Scrabble scrabble = new Scrabble(scan);
  Board board = new Board();
  Frame playerFrame = new Frame();
  Move move = new Move(board, playerFrame);
  UI ui = new UI();
  
  

  

}
