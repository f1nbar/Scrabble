package scrabble;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JPanel {

	private Board board;
	private Player player1;
	private Player player2;
	private Tile[][] letterBoard;
	private HashMap<Integer, String> tilePoints;
	private JFrame f = new JFrame("Scrabble");

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

	public void intialize_screen() {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.BLACK);
		f.setSize(543, 700);
		f.setVisible(true);
		f.add(this);
		f.setResizable(false);

	}

	int PIECE_SIZE = 33;
	int PIECE_GAP = 2;
	int FRAME_DEPTH = 560; // Distance Frames are down the screen
	Font NORMAL_LETTER_FONT = new Font("San seif", Font.BOLD, 10);
	Font BIG_LETTER_FONT = new Font("San seif", Font.BOLD, 22);
	int FRAME_DISTANCE_FROM_LEFT_BORDER = 10;

	public void paint(Graphics g) {
		super.paint(g);
		paintBoard(g);
		paintFrames(g);

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
		g.setFont(NORMAL_LETTER_FONT);
		g.drawString(Integer.toString(tile.getScore()),x + NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_X,y +NORMAL_LETTER_FONT_PIECE_TEXT_OFFSET_Y);
	}

	private void paintFrames(Graphics g) {

		g.setColor(Color.magenta);
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

		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		g.setFont(NORMAL_LETTER_FONT);

		for (int x = 0; x < 15; x++) { // x is x axis and y is y axis of the scrabble board
			for (int y = 0; y < 15; y++) {
				int position = Board.concatInt(x, y);
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
						g.drawString("Double", PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 5 + ((PIECE_GAP + PIECE_SIZE) * x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "*":
						g.setColor(Color.pink);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						break;
					default:
						g.setColor(Color.gray);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x),
								PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
					}
				} else {
					g.setColor(Color.gray);
					g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y),
							PIECE_SIZE, PIECE_SIZE);
				}

			}
		}

	}

}
