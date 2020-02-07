package scrabble;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your name: ");
		String name = input.next();
		input.close();
		
		Pool pool = new Pool();
		Player player = new Player(name);
		Frame frame = new Frame();
		
		pool.fillPool();

		while (frame.frameSize() < 7) {
			frame.fillFrame(pool.randomTile());
		}
		
		System.out.println("Your frame is: " + frame.toString());
		
		
	}
}
