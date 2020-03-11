package scrabble;

import java.util.Scanner;

public class Main {

    private static Player initialisePlayer(Pool pool, Scanner input) {
        System.out.print("Enter player name: ");
        String name = input.nextLine();
        Player player = new Player(name);
        while (player.getFrame().frameSize() != player.getFrame().FRAME_SIZE) {
            player.getFrame().fillFrame(pool.randomTile());
        }
        return player;
    }

    private static void playerTurn(Player player, Board board, Scanner input, Pool pool) {
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

    // driver code
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Pool pool = new Pool();
        pool.fillPool();
        Board board = new Board();
        Player playerOne = initialisePlayer(pool, in);
        Player playerTwo = initialisePlayer(pool, in);

        System.out.println(board);
        int turns = 0;
        while (true) { //TODO: add end clause to game.
            if (turns % 2 == 0) {
                playerTurn(playerOne, board, in, pool);
            } else {
                playerTurn(playerTwo, board, in, pool);
            }
            System.out.println(board);
            turns++;
        }
    }

}
