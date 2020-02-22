package scrabble;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your name: ");
		String name = input.nextLine();

		Pool pool = new Pool();
		Player player = new Player(name);
		Frame frame = new Frame();

		pool.fillPool();

		while (frame.frameSize() < 7) {
			frame.fillFrame(pool.randomTile());
		}

		System.out.println("Name: " + player.getName());
		System.out.println("Your frame is: " + frame.toString());
		
		Board board = new Board();
		board.displayBoard();
		
		int selectTile = input.nextInt();
		input.close();
		
		board.placeTile(7, 7, player, frame.getTile(selectTile));
		frame.removeLetter(frame.getTile(selectTile));
		board.displayBoard();
	}
}
