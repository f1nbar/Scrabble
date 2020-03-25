package scrabble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.application.Application;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class ScabbleTest {
  
	
  String testInput;
  Scanner scan = new Scanner(testInput);
  Scrabble scrabble = new Scrabble(scan);
  Board board = new Board();
  Frame playerFrame = new Frame();
  Move move = new Move(board, playerFrame);
  
  
  
}
