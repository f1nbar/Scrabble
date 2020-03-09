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
	private int[][] LETTER_MULTIPLER;
	private int[][] WORD_MULTIPLER;

	private Scanner in;

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

	}
	
	
	int PIECE_SIZE = 33;
	int PIECE_GAP = 2;

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);


		
		
		Font font1 = new Font("San seif", Font.BOLD, 10);
		Font font2 = new Font("San seif", Font.BOLD,22);
		g.setFont(font1);
		
		
		for (int x = 0; x < 15; x++) {   // x is x axis and y is y axis
			for (int y = 0; y < 15; y++) {
				int position = Board.concatInt(x, y);
				if (letterBoard[x][y] != null) {
					g.setColor(Color.yellow);
					g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
					g.setColor(Color.black);
					g.setFont(font2);
					g.drawString(Character.toString(letterBoard[x][y].getLetter()),8 + ((PIECE_GAP + PIECE_SIZE) * x), 27 + ((PIECE_GAP + PIECE_SIZE) * y));
					g.setFont(font1);
					g.drawString(Integer.toString(letterBoard[x][y].getScore()),22 + ((PIECE_GAP + PIECE_SIZE) * x), 27 + ((PIECE_GAP + PIECE_SIZE) * y));
				} else if (board.getSquareValue(position) != null) {
					switch (board.getSquareValue(position)) {
					case "TW":
						g.setColor(Color.red);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 5+((PIECE_GAP + PIECE_SIZE)*x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6+((PIECE_GAP + PIECE_SIZE)*x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "TL":
						g.setColor(Color.getHSBColor(0.575f, 0.85f, 0.72f));
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Triple", 5+((PIECE_GAP + PIECE_SIZE)*x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 5+((PIECE_GAP + PIECE_SIZE)*x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DW":
						g.setColor(Color.pink);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", PIECE_GAP+((PIECE_GAP + PIECE_SIZE)*x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Word", 6+((PIECE_GAP + PIECE_SIZE)*x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "DL":
						g.setColor(Color.cyan);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						g.setColor(Color.black);
						g.drawString("Double", PIECE_GAP+((PIECE_GAP + PIECE_SIZE)*x), 17 + ((PIECE_GAP + PIECE_SIZE) * y));
						g.drawString("Letter", 5+((PIECE_GAP + PIECE_SIZE)*x), 25 + ((PIECE_GAP + PIECE_SIZE) * y));
						break;
					case "*":
						g.setColor(Color.pink);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
						break;
					default:
						g.setColor(Color.gray);
						g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
					}
				} else {
					g.setColor(Color.gray);
					g.fillRect(PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * x), PIECE_GAP + ((PIECE_GAP + PIECE_SIZE) * y), PIECE_SIZE, PIECE_SIZE);
				}
				
//				//Printing Players frames
//				g.fillRect(, y, width, height);
				
			}
		}

	}
}
