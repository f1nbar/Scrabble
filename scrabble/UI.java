package scrabble;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JPanel  {

	private Board board;
	private Player player1;
	private Player player2;
	private Tile[][] letterBoard;
	private JFrame f = new JFrame("Scrabble");
	private boolean helpMenuState = false;

	private Scanner in;

	private int BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X = 4; // The Difference between The X Cordinate of the Piece and the
															// X cordinate of the String on the piece

	private int BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y = 26; // The Difference between The y Cordinate of the Piece and
															// they cordinate of the String on the piece

	private int NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_X = 21; // The Difference between The X Cordinate of the Piece and
																// the X cordinate of the String on the piece

	private int NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_Y = 29; // The Difference between The y Cordinate of the Piece and
																// they cordinate of the String on the piece
	
	int PIECE_SIZE = 34;										//Dimensions of Square Piece
	int PIECE_GAP = 2;											//Gap between Pieces
	int FRAME_DEPTH = 603; // Distance Frames are down the screen	//Y value of Player's Frames
	Font SMALL_LETTER_FONT = new Font("san serif", Font.BOLD, 10);
	Font NORMAL_LETTER_FONT = new Font("san serif", Font.BOLD, 15);
	Font BIG_LETTER_FONT = new Font("san serif", Font.BOLD, 22);
	Image starImage = new ImageIcon(ClassLoader.getSystemResource("scrabble\\Star.png")).getImage();	//Resources
	Image gooseImage = new ImageIcon(ClassLoader.getSystemResource("scrabble\\Goose.png")).getImage();
	int FRAME_DISTANCE_FROM_LEFT_BORDER = 15;					
	int BOARD_X_CORD = 28;										//Coordinates of Scrabble Board 
	int BOARD_Y_CORD = 28;
	int HELP_BOARD_POSITION_X = 140;							//Help Board Coordinates 
	int HELP_BOARD_POSITION_Y = 130;
	int HELP_BOARD_LENGTH = 400;								//Help Board Dimensions
	int HELP_BOARD_WIDTH = 300;

	
	
	public UI(Board board, Player player1, Player player2, Scanner in) {

		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.in = in;
		this.letterBoard = board.getBoard();
		intializeScreen();

	}


	public void paintHelpBoard(Graphics g) {
		int FONT_MARGIN = 50;

		g.setColor(Color.magenta);
		g.fillRect(HELP_BOARD_POSITION_X, HELP_BOARD_POSITION_Y, HELP_BOARD_WIDTH, HELP_BOARD_LENGTH);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(HELP_BOARD_POSITION_X + PIECE_GAP, HELP_BOARD_POSITION_Y + PIECE_GAP,
				HELP_BOARD_WIDTH - (PIECE_GAP * 2), HELP_BOARD_LENGTH - (PIECE_GAP * 2));
		g.setColor(Color.black);
		g.setFont(BIG_LETTER_FONT);
		g.drawString("Help Menu", HELP_BOARD_POSITION_X + 100, HELP_BOARD_POSITION_Y + 20);

	}

	public void intializeScreen() {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.BLACK);
		f.setSize(653, 728);
		f.setVisible(true);
		f.add(this);
		f.setResizable(false);

	}


	public void paint(Graphics g) {
		super.paint(g);
		paintBoard(g);
		paintFrames(g);
		paintInformationBoard(g);
		if (helpMenuState)				//Only paints Help Board when the Help Function is Called
			paintHelpBoard(g);
	}

	private void paintTile(Graphics g, int x, int y, Tile tile) { // given the x and y cords of a tile on the board it
																	// can print the tile onto the screen

		g.setColor(Color.yellow);
		g.fillRect(x, y, PIECE_SIZE, PIECE_SIZE);
		g.setColor(Color.black);
		g.setFont(BIG_LETTER_FONT);
		if (tile.getLetter() != 'I') {
			g.drawString(Character.toString(tile.getLetter()), x + BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X,		// This accounts for the spacing of I as it is very far left compared ot the other letters
					y + BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y);
		} else {
			g.drawString(Character.toString(tile.getLetter()), x + 5 + BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X,
					y + BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y);// exception for I spacing is different
		}
		g.setFont(SMALL_LETTER_FONT);
		g.drawString(Integer.toString(tile.getScore()), x + NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_X,
				y + NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_Y);
	}

	private void paintInformationBoard(Graphics g) {

		int TURN_SIGNAL_WIDTH = 100;
		int TURN_SIGNAL_HEIGHT = 30;
		
		
		// paints Players names above frame
		g.setColor(Color.BLACK);
		g.setFont(NORMAL_LETTER_FONT);
		g.drawString(player1.getName() + "'s Frame", FRAME_DISTANCE_FROM_LEFT_BORDER, FRAME_DEPTH - 5);
		g.drawString(player2.getName() + "'s Frame", FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
				+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE, FRAME_DEPTH - 5);
		//paints players core underneath frame
		g.drawString("Score: " + player1.getScore(), FRAME_DISTANCE_FROM_LEFT_BORDER,
				FRAME_DEPTH + (PIECE_GAP * 2) + PIECE_SIZE + 15);
		g.drawString("Score: " + player2.getScore(),
				FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
						+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE,
				FRAME_DEPTH + (PIECE_GAP * 2) + PIECE_SIZE + 15);
		//paints turn signals either green(if its their turn) or red (not their turn);
		if (player1.getTurn()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect(FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP + PIECE_SIZE) * 4),
				FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10, TURN_SIGNAL_WIDTH, TURN_SIGNAL_HEIGHT);

		if (player2.getTurn()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect(
				(FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP + PIECE_SIZE) * 4)) + ((PIECE_GAP + PIECE_SIZE) * 7)
						+ PIECE_GAP * 2,
				FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10, TURN_SIGNAL_WIDTH, TURN_SIGNAL_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Turn", FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP + PIECE_SIZE) * 4) + TURN_SIGNAL_WIDTH / 3,
				FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10 + (TURN_SIGNAL_HEIGHT / 2)
						+ (NORMAL_LETTER_FONT.getSize() / 2));
		g.drawString("Turn",
				FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP + PIECE_SIZE) * 4) + PIECE_GAP
						+ ((PIECE_GAP + PIECE_SIZE) * 7) + TURN_SIGNAL_WIDTH / 3,
				FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10 + (TURN_SIGNAL_HEIGHT / 2)
						+ (NORMAL_LETTER_FONT.getSize() / 2));
		//paints goose
		g.drawImage(gooseImage, 566, 553, this);

		
		//paints cover to hide other players pieces
		g.setColor(Color.DARK_GRAY);
		if (!player1.getTurn()) {

			g.fillRect(FRAME_DISTANCE_FROM_LEFT_BORDER, FRAME_DEPTH,
					PIECE_GAP + (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE, (PIECE_GAP * 2) + PIECE_SIZE);

		} else {
			g.fillRect(
					FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
							+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE,
					FRAME_DEPTH, PIECE_GAP + (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE,
					(PIECE_GAP * 2) + PIECE_SIZE);
		}

	}

	private void paintFrames(Graphics g) {

		g.setColor(Color.CYAN);
		g.fillRect(FRAME_DISTANCE_FROM_LEFT_BORDER, FRAME_DEPTH,
				PIECE_GAP + (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE, (PIECE_GAP * 2) + PIECE_SIZE);
		g.fillRect(
				FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
						+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE,
				FRAME_DEPTH, PIECE_GAP + (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE,
				(PIECE_GAP * 2) + PIECE_SIZE);

		for (int i = 0; i < player1.getFrame().frameSize(); i++) {

			paintTile(g, (PIECE_GAP + FRAME_DISTANCE_FROM_LEFT_BORDER) + ((PIECE_GAP + PIECE_SIZE) * i),
					FRAME_DEPTH + PIECE_GAP, player1.getFrame().getTile(i));

		}

		for (int i = 0; i < player2.getFrame().frameSize(); i++) {

			paintTile(g,
					FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 4)
							+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE + ((PIECE_GAP + PIECE_SIZE) * i),
					FRAME_DEPTH + PIECE_GAP, player2.getFrame().getTile(i));

		}

	}

	private void paintBoard(Graphics g) {

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1000, 1000);
		g.setFont(SMALL_LETTER_FONT);

		for (int x = 0; x < 15; x++) { // x is x axis and y is y axis of the scrabble board
			for (int y = 0; y < 15; y++) {
				String position = Board.concatInt(y, x);
				if (letterBoard[y][x] != null) {
					paintTile(g, BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
							BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), letterBoard[y][x]);
				} else if (board.getSquareValue(position) != null) {
					switch (board.getSquareValue(position)) {
					case "TW":
						g.setColor(Color.red);
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 5 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								25 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "TL":
						g.setColor(Color.getHSBColor(0.575f, 0.85f, 0.72f));
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 4 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 4 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								25 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DW":
						g.setColor(Color.pink);
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								25 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DL":
						g.setColor(Color.cyan);
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 4 + BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								25 + BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "*":
						g.setColor(Color.pink);
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.drawImage(starImage, BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE, this);
						break;
					default:
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
								BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
					}
				} else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(BOARD_X_CORD + ((PIECE_GAP + PIECE_SIZE) * x),
							BOARD_Y_CORD + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
				}

			}
		}

		//paints numbers for coordinates
		for (int i = 0; i < letterBoard.length; i++) {
			g.setFont(BIG_LETTER_FONT);
			g.setColor(Color.white);
			if (i <= 9) {
				g.drawString(Integer.toString(i), 8, ((PIECE_GAP + PIECE_SIZE) * i) + 50);		// spacing for double digit letters is different so this compensates for it
			} else {
				g.drawString(Integer.toString(i), 0, ((PIECE_GAP + PIECE_SIZE) * i) + 50);
			}
			if (i <= 9) {
				g.drawString(Integer.toString(i), ((PIECE_GAP + PIECE_SIZE) * i) + BOARD_X_CORD + 10, BOARD_Y_CORD - 2);	// spacing for double digit letters is different so this compensates for it
			} else {
				g.drawString(Integer.toString(i), ((PIECE_GAP + PIECE_SIZE) * i) + BOARD_X_CORD + 3, BOARD_Y_CORD - 2);
			}
			g.setFont(SMALL_LETTER_FONT);
		}

	}

	//Takes input from user
	public void systemInput(Scanner input) throws InterruptedException {

		String command = input.next();

		if (command.contains("QUIT")) {
			System.exit(0);
		}

		if (command.contains("PASS")) {
			// TODO PLAYER PASSES TURN
		}

		if (command.contains("HELP")) {
			helpMenuState = true;
			repaint();
			TimeUnit.SECONDS.sleep(30);
			helpMenuState = false;
			repaint();
		}

	}

}
