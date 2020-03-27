package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Scrabble {
    private Pool pool;
    private Board board;
    private Player playerOne, playerTwo;
    public static HashSet<String> dictionary = null;

    static {
        try {
            dictionary = initializeDictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Move previousMove;

    public Scrabble() {
        this.pool = new Pool();
        this.board = new Board();
    }

    //getters and setters
    public Board getBoard() {
        return board;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setIsOver(boolean isOver) {
        Main.gameGo = isOver;
    }

    public Pool getPool() {
        return this.pool;
    }

    private static HashSet<String> initializeDictionary() throws FileNotFoundException {
        Scanner file = new Scanner(new File("scrabble/sowpods.txt"));
        HashSet<String> dictionary = new HashSet<>();
        while(file.hasNext()) {
            dictionary.add(file.next().trim());
        }
        return dictionary;
    }

    public Player initialisePlayer(Pool pool, Scanner input) {
        System.out.print("Enter player name: ");
        String name = input.nextLine();
        Player player = new Player(name);
        while (player.getFrame().frameSize() != Frame.FRAME_SIZE) {
            player.getFrame().fillFrame(pool.randomTile());
        }
        return player;
    }

    public boolean playerTurn(Player player, Board board, Scanner input, Pool pool, String inputString) {
        Move move = new Move(board, player.getFrame());

        boolean validMove = move.makeMove(input, inputString);
        if (validMove) {
            player.getFrame().refill(pool);
            player.increaseScore(move.calculateScore());
            previousMove = move;
            return true;
        }
        return false;
    }
}
