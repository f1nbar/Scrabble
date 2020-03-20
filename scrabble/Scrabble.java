package scrabble;

import java.util.Scanner;

public class Scrabble {
    private Pool pool;
    private Board board;
    private Player playerOne, playerTwo;
    private UIFX ui;
    private Scanner in;

    public Scrabble(){
        this.in = new Scanner(System.in);
        this.pool =  new Pool();
        this.board = new Board();
        this.playerOne = initialisePlayer(this.pool, this.in);
        this.playerTwo = initialisePlayer(this.pool, this.in);
        this.ui = new UIFX(board,playerOne,playerTwo);
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

    public void setIn(Scanner in) {
        this.in = in;
    }

    public Player initialisePlayer(Pool pool, Scanner input) {
        System.out.print("Enter player name: ");
        String name = input.nextLine();
        Player player = new Player(name);
        while (player.getFrame().frameSize() != player.getFrame().FRAME_SIZE) {
            player.getFrame().fillFrame(pool.randomTile());
        }
        return player;
    }

    public void playerTurn(Player player, Board board, Scanner input, Pool pool) {
        player.setTurn(true);
        System.out.println(player.getName() + "'s turn:");
        Move move = new Move(board, player.getFrame());
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

        int turns = 0;
        while (true) { //TODO: add end clause to game.
            if (turns % 2 == 0) {
                this.playerTurn(playerOne, board, in, pool);
            } else {
                this.playerTurn(playerTwo, board, in, pool);
            }
            System.out.println(board);
            turns++;
        }
    }
}
