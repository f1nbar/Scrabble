package scrabble;

import java.util.Scanner;

public class Main {
	private static final int neutralDirection = 0;
	private static final int middleRow = 7;
	private static final int middleColumn = 7;

	private static int previousRow = middleRow;
	private static int previousColumn = middleColumn;
	private static int direction;

	private static boolean directionValid = true;

	private static int selectDirection(Scanner in) { // temporary until Player turn functionality added.
		boolean invalid = true;
		int direction = 0;
		while (invalid) {
			System.out.print("\nPlease select if you are placing a horizontal word (H) or a vertical word (V): ");
			char horizontal_vertical = Character.toUpperCase((in.next(".").charAt(0)));
			switch (horizontal_vertical) {
			case 'H':
				direction = Move.horizontal;
				invalid = false;
				break;
			case 'V':
				direction = Move.veritical;
				invalid = false;
				break;
			default:
				System.out.println("Invalid choice. Please choose 'H' (Horizontal) or 'V' (Vertical): ");
				break;
			}
		}
		return direction;
	}

	private static void firstMove(Scanner in, Board board, Player player_one, Frame playerFrame) {
		int row = Main.middleRow, column = Main.middleColumn;
		System.out.print("\n\nFirst Move\nPlease pick a letter: ");
		char letter = Character.toUpperCase(in.next(".").charAt(0)); // takes ONLY one character from scanner and makes
																		// it uppercase.
		while (!board.placeTile(row, column, player_one, playerFrame.getTileFromChar(letter), neutralDirection)) { // loop
																													// for
																													// invalid
																													// input
			System.out.println("Invalid move");
			firstMove(in, board, player_one, playerFrame);
		}

		playerFrame.removeLetter(playerFrame.getTileFromChar(letter));
		Move.firstMoveMade();
		System.out.println(board);
	}

	private static void concurrentMoves(Scanner in, Board board, Player player_one, Frame playerFrame) {
		int column, row;
		while (!playerFrame.isEmpty()) {
			System.out.print("Frame: " + playerFrame.toString());

			char letter = '#';

			while (letter == '#') {
				System.out.print("\nPlease pick a letter: ");
				letter = Character.toUpperCase((in.next(".").charAt(0)));

				if (playerFrame.checkLetters(new Tile(letter, 100))) {
					break;
				} else {
					letter = '#';
					System.out.println("Letter is not in frame");
				}

			}
			if (direction != Move.horizontal && direction != Move.veritical) {
				direction = selectDirection(in);
			}

			System.out.print("\n Please select coordinate\n "); // limits player to one axis
			if (direction == Move.horizontal) {
				System.out.print("Column: ");
				column = in.nextInt();
				row = Main.previousRow;
			} else {
				System.out.print("Row: ");
				row = in.nextInt();
				column = Main.previousColumn;
			}

			while (!board.placeTile(row, column, player_one, playerFrame.getTileFromChar(letter), direction)
					|| !directionValid) { // for invalid input
				System.out.println(board);
				System.out.print("Frame:\n" + playerFrame.toString());
				System.out.println("Invalid Move.");
				concurrentMoves(in, board, player_one, playerFrame);
			}

			Main.previousRow = row; // store previous move for tracking direction
			Main.previousColumn = column;
			playerFrame.removeLetter(playerFrame.getTileFromChar(letter));
			System.out.println(board);
		}
	}

	// driver code
	public static void main(String[] args) {
		// Initialize game
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

		Board board = new Board();
		System.out.println(board);
		board.displayLegend();
		System.out.print("Frame:\n" + frame.toString());

		// First Move
		Main.firstMove(input, board, player, frame);

		// Concurrent moves
		Main.concurrentMoves(input, board, player, frame);

		input.close();
		
	
	}
}
