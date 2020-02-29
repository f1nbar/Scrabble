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

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);

//		for (int i = 0; i < letterBoard.length; i++) {
//			for (int j = 0; j < letterBoard.length; j++) {
//
//
//				int position = Board.concatInt(i, j);
//				if (letterBoard[i][j] != null) {
//					
//
//				} else if (board.getSquareValue(position) != null) {
//					switch (board.getSquareValue(position)) {
//					case "TW":
//						g.setColor(Color.red);
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//						break;
//					case "TL":
//						g.setColor(Color.getHSBColor(0.575f, 0.85f, 0.72f));
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//						break;
//					case "DW":
//						g.setColor(Color.pink);
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//						break;
//					case "DL":
//						g.setColor(Color.cyan);
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//						break;
//					case "*":
//						g.setColor(Color.pink);
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//						break;
//					default:
//						g.setColor(Color.gray);
//						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
//					}
//
//				}
//
//			}
//
//		}
		
		
		Font font1 = new Font("San seif", Font.BOLD, 10);
		Font font2 = new Font("San seif", Font.BOLD,22);
		g.setFont(font1);
		
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				int position = Board.concatInt(i, j);
				if (letterBoard[i][j] != null) {
					g.setColor(Color.yellow);
					g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
					g.setColor(Color.black);
					g.setFont(font2);
					g.drawString(Character.toString(letterBoard[i][j].getLetter()),8 + (35 * i), 27 + (35 * j));
					g.setFont(font1);
					g.drawString(Integer.toString(letterBoard[i][j].getScore()),22 + (35 * i), 27 + (35 * j));
				} else if (board.getSquareValue(position) != null) {
					switch (board.getSquareValue(position)) {
					case "TW":
						g.setColor(Color.red);
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
						g.setColor(Color.black);
						g.drawString("Triple", 5+(35*i), 17 + (35 * j));
						g.drawString("Word", 6+(35*i), 25 + (35 * j));
						break;
					case "TL":
						g.setColor(Color.getHSBColor(0.575f, 0.85f, 0.72f));
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
						g.setColor(Color.black);
						g.drawString("Triple", 5+(35*i), 17 + (35 * j));
						g.drawString("Letter", 5+(35*i), 25 + (35 * j));
						break;
					case "DW":
						g.setColor(Color.pink);
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
						g.setColor(Color.black);
						g.drawString("Double", 2+(35*i), 17 + (35 * j));
						g.drawString("Word", 6+(35*i), 25 + (35 * j));
						break;
					case "DL":
						g.setColor(Color.cyan);
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
						g.setColor(Color.black);
						g.drawString("Double", 2+(35*i), 17 + (35 * j));
						g.drawString("Letter", 5+(35*i), 25 + (35 * j));
						break;
					case "*":
						g.setColor(Color.pink);
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
						break;
					default:
						g.setColor(Color.gray);
						g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
					}
				} else {
					g.setColor(Color.gray);
					g.fillRect(2 + (35 * i), 2 + (35 * j), 33, 33);
				}
			}
		}

	}
}
