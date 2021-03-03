package HW2;
import java.util.*;

public class PlayConnectFour {

    private static void doTurn(ConnectFourGame game, GamePlayer playerToMove, Scanner scanner) {
        System.out.println(game.toString());
        String playerName = playerToMove == GamePlayer.A ? "Player A" : "Player B";
        System.out.print(playerName + " enter the column for your piece: ");
        try {
            int columnForPiece = scanner.nextInt() - 1;
            if (game.isValidMove(playerToMove, columnForPiece)) {
                try {
                    game.makeMove(playerToMove, columnForPiece);
                } catch (InvalidMoveExcpetion e) {
                    System.out.println(playerName + " made an illegal move, try again.");
                    doTurn(game, playerToMove, scanner);
                }
            } else {
                System.out.println("was not a valid move");
                doTurn(game, playerToMove, scanner);
            }
        } catch (InputMismatchException e) {
            System.out.println(playerName + " please enter a number between 1 and 7");
            doTurn(game, playerToMove, scanner);
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
            PlayConnectFour.doTurn(game, game.playerToMove(), scanner);
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
