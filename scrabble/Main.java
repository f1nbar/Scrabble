package scrabble;

import java.io.InputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System. in);
		System.out.println("Enter your name: ");
		String name = input.next();
		input.close();
	
		Pool pool = new Pool();
		Player player = new Player(name);
		Frame frame = new Frame();
		
		pool.fillPool();
	
	   while(frame.size())
		
		
		System.out.println(frame.toString());
	}

}
