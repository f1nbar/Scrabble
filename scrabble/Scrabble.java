package scrabble;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Scanner;

public class Scrabble {
    private Pool pool;
    private Board board;
    private Player playerOne, playerTwo;
    private UIFX ui;
    private Scanner in;
    private boolean isOver;

    public Scrabble(Scanner input){
        this.in = input;
        this.pool =  new Pool();
        this.board = new Board();

        this.isOver = false;
    }

    //getters and setters
    public UIFX getUi() {
        return ui;
    }

    public void setUi(UIFX ui) {
        this.ui = ui;
    }

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

    public Scanner getIn() {
        return in;
    }
    public void setIsOver(boolean isOver){
        this.isOver = isOver;
    }
    public boolean getIsOver() {
        return this.isOver;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }
    public Pool getPool() {
        return this.pool;
    }

    public Player initialisePlayer(Pool pool, Scanner input) {
        this.in = input;
        System.out.print("Enter player name: ");
        String name = input.nextLine();
        Player player = new Player(name);
        while (player.getFrame().frameSize() != Frame.FRAME_SIZE) {
            player.getFrame().fillFrame(pool.randomTile());
        }
        return player;
    }

    public void playerTurn(Player player, Board board, Scanner input, Pool pool) {
        player.setTurn(true);
        System.out.println(player.getName() + "'s turn:");
        Move move = new Move(board, player.getFrame(), ui);
        boolean validMove = move.makeMove(input);

        if (!validMove) {
            move.undoMove();
            playerTurn(player, board, input, pool);
        } else{
            player.getFrame().refill(pool);
        }
        player.setTurn(false);
    }

    public void runGame(){
    }
}
