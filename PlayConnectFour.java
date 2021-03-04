package HW2;
import java.util.*;

/**
 * The {@code PlayConnectFour} class represents a text-based client
 * that uses {@code ConnectFourGame}. This client allows two players
 * to play against each other and the game will stop when one of the player wins.
 */
 public class PlayConnectFour {
    private static void doTurn(ConnectFourGame game, Scanner scanner) {
        System.out.println(game.toString());
        String playerName = game.playerToMove() == GamePlayer.A ? "Player A" : "Player B";
        System.out.print(playerName + " enter the column for your piece: ");
        try {
            int columnForPiece = scanner.nextInt() - 1;
            if (game.isValidMove(columnForPiece)) {
                try {
                    game.makeMove(columnForPiece);
                } catch (InvalidMoveExcpetion e) {
                    System.out.println(playerName + " made an illegal move, try again.");
                    doTurn(game, scanner);
                }
            } else {
                System.out.println("was not a valid move");
                doTurn(game, scanner);
            }
        } catch (InputMismatchException e) {
            System.out.println(playerName + " please enter a number between 1 and 7");
            doTurn(game, scanner);
        }
    }

    public static void main(String[] args) {
        ConnectFourGame game = new ConnectFourGame();
        Scanner scanner = new Scanner(System.in);

        String playerName = game.playerToMove() == GamePlayer.A ? "Player A" : "Player B";
        System.out.println("Welcome to connect four! " + playerName + " will move first.");
        System.out.println("When prompted, enter a number between 1 and 7 to place a piece.");
        System.out.println("Entering anything that is not a number between 1 and 7 will break the game :)");

        while (game.state() == GameState.IN_PROGRESS) {
            PlayConnectFour.doTurn(game, scanner);
        }

        switch (game.winner()) {
            case A:
                System.out.println("Congrats Player A! You won!!");
                break;
            case B:
                System.out.println("Congrats Player B! You won!!");
                break;
            case DRAW:
                System.out.println("It's a draw!");
            default:
                break;
        }
        scanner.close();
    }
}
