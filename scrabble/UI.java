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

public class UI extends JPanel {

	private Board board;
	private Player player1;
	private Player player2;
	private Tile[][] letterBoard;
	private JFrame f = new JFrame("Scrabble");
	private boolean helpMenuState = false;

	private Scanner in;
	
	private int BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X = 4;  	//The Difference between The X Cordinate of the Piece and the X cordinate of the String on the piece 

	private int BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y = 26;    //The Difference between The y Cordinate of the Piece and they cordinate of the String on the piece 

	private int NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_X = 21;		//The Difference between The X Cordinate of the Piece and the X cordinate of the String on the piece 	

	private int NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_Y = 29;		//The Difference between The y Cordinate of the Piece and they cordinate of the String on the piece 


	public UI(Board board, Player player1, Player player2, Scanner in) {

		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.in = in;
		this.letterBoard = board.getBoard();
		

	}
	
	
	int HELP_BOARD_POSITION_X = 140;
	int HELP_BOARD_POSITION_Y = 130;
	int HELP_BOARD_LENGTH = 400;
	int HELP_BOARD_WIDTH = 300;
	
	public void paintHelpBoard(Graphics g) {
		int FONT_MARGIN = 50;
		
		g.setColor(Color.magenta);
		g.fillRect(HELP_BOARD_POSITION_X,HELP_BOARD_POSITION_Y	, HELP_BOARD_WIDTH,HELP_BOARD_LENGTH );
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(HELP_BOARD_POSITION_X+PIECE_GAP, HELP_BOARD_POSITION_Y +PIECE_GAP, HELP_BOARD_WIDTH-(PIECE_GAP*2), HELP_BOARD_LENGTH-(PIECE_GAP*2));
		g.setColor(Color.black);
		g.setFont(BIG_LETTER_FONT);
		g.drawString("Help Menu", HELP_BOARD_POSITION_X + 100, HELP_BOARD_POSITION_Y + 20);

	
	}

	public void intialize_screen() {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.BLACK);
		f.setSize(625, 700);
		f.setVisible(true);
		f.add(this);
		f.setResizable(false);

	}

	int PIECE_SIZE = 34;
	int PIECE_GAP = 2;
	int FRAME_DEPTH = 575; // Distance Frames are down the screen
	Font SMALL_LETTER_FONT = new Font("San seif", Font.BOLD, 10);
	Font NORMAL_LETTER_FONT = new Font("San seif",Font.BOLD,15);
	Font BIG_LETTER_FONT = new Font("San seif", Font.BOLD, 22);
	Image starImage = new ImageIcon(ClassLoader.getSystemResource("scrabble\\Star.png")).getImage();
	Image gooseImage = new ImageIcon(ClassLoader.getSystemResource("scrabble\\Goose.png")).getImage();
	int FRAME_DISTANCE_FROM_LEFT_BORDER = 15;

	public void paint(Graphics g) {
		super.paint(g);
		paintBoard(g);
		paintFrames(g);
		paintInformationBoard(g);
		if(helpMenuState)
		paintHelpBoard(g);
	}
	
	private void paintTile(Graphics g,int x,int y,Tile tile) {		//given the x and y cords of a tile on the board it can print the tile onto the screen
	
		g.setColor(Color.yellow);
		g.fillRect(x,y,PIECE_SIZE, PIECE_SIZE);
		g.setColor(Color.black);
		g.setFont(BIG_LETTER_FONT);
		if(tile.getLetter() != 'I') {
		g.drawString(Character.toString(tile.getLetter()),x +BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X,y +BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y);
		} else {
			g.drawString(Character.toString(tile.getLetter()),x+ 5 +BIG_LETTER_FONT_PIECE_TEXT_OFFSET_X,y +BIG_LETTER_FONT_PIECE_TEXT_OFFSET_Y);//exception for I spacing is different
		}
		g.setFont(SMALL_LETTER_FONT);
		g.drawString(Integer.toString(tile.getScore()),x + NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_X,y +NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_Y);
	}	
	
	private void paintInformationBoard(Graphics g) {
		
		int TURN_SIGNAL_WIDTH = 100;
		int TURN_SIGNAL_HEIGHT = 30;
	
		
		g.setColor(Color.BLACK);
		g.setFont(NORMAL_LETTER_FONT);
		g.drawString(player1.getName() + "'s Frame", FRAME_DISTANCE_FROM_LEFT_BORDER, FRAME_DEPTH - 5);
		g.drawString(player2.getName() + "'s Frame", FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
				+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE, FRAME_DEPTH - 5);
		g.drawString("Score: " + player1.getScore(), FRAME_DISTANCE_FROM_LEFT_BORDER, FRAME_DEPTH + (PIECE_GAP * 2) + PIECE_SIZE + 15);
		g.drawString("Score: " + player2.getScore(), FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 2) + PIECE_GAP
				+ (PIECE_GAP + PIECE_SIZE) * player1.getFrame().FRAME_SIZE, FRAME_DEPTH + (PIECE_GAP * 2) + PIECE_SIZE + 15);
		if(player1.getTurn()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect(FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP +PIECE_SIZE) * 4),FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10,  TURN_SIGNAL_WIDTH, TURN_SIGNAL_HEIGHT);
		
		if(player2.getTurn()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect((FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP + PIECE_SIZE) * 4))+((PIECE_GAP + PIECE_SIZE) * 7) + PIECE_GAP * 2,FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10,  TURN_SIGNAL_WIDTH, TURN_SIGNAL_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Turn", FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP +PIECE_SIZE) * 4) + TURN_SIGNAL_WIDTH/3,FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10 + (TURN_SIGNAL_HEIGHT/2) + (NORMAL_LETTER_FONT.getSize()/2) );
		g.drawString("Turn", FRAME_DISTANCE_FROM_LEFT_BORDER + ((PIECE_GAP +PIECE_SIZE) * 4)+ PIECE_GAP + ((PIECE_GAP +PIECE_SIZE) * 7)  + TURN_SIGNAL_WIDTH/3,FRAME_DEPTH + (PIECE_GAP * 2) + (PIECE_SIZE) + 10 + (TURN_SIGNAL_HEIGHT/2) + (NORMAL_LETTER_FONT.getSize()/2) );
		g.drawImage(gooseImage,548,525,this);
	
		g.setColor(Color.DARK_GRAY);
		if(player1.getTurn()) {
			
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
			
			paintTile(g, FRAME_DISTANCE_FROM_LEFT_BORDER + (PIECE_GAP * 4) 
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
				String position = Board.concatInt(x, y);
				if (letterBoard[x][y] != null) {
					paintTile(g, PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), letterBoard[x][y]);
				} else if (board.getSquareValue(position) != null) {
					switch (board.getSquareValue(position)) {
					case "TW":
						g.setColor(Color.red);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 5 + ((PIECE_GAP + PIECE_SIZE) * x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6 + ((PIECE_GAP + PIECE_SIZE) * x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "TL":
						g.setColor(Color.getHSBColor(0.575f, 0.85f, 0.72f));
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 5 + ((PIECE_GAP + PIECE_SIZE) * x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 5 + ((PIECE_GAP + PIECE_SIZE) * x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DW":
						g.setColor(Color.pink);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6 + ((PIECE_GAP + PIECE_SIZE) * x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DL":
						g.setColor(Color.cyan);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x) ,
								17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 5 + ((PIECE_GAP + PIECE_SIZE) * x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "*":
						g.setColor(Color.pink);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.drawImage(starImage,PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x) ,PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE , this);
						break;
					default:
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
					}
				} else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y),
							PIECE_SIZE, PIECE_SIZE);
				}

			}
		}

	}
	
	public void systemInput(Scanner input) throws InterruptedException {
		
		String command = input.next();
		
		if(command.contains("QUIT")) {
			System.exit(0);
		}
		
		if(command.contains("PASS")) {
			//TODO PLAYER PASSES TURN 
		}
		
		if(command.contains("HELP")) {
			helpMenuState = true;
			repaint();
			TimeUnit.SECONDS.sleep(30);
			helpMenuState = false;
			repaint();
		}
		
		
	}

}
