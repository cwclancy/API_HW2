package HW2;

import java.util.*;

import HW2.ConnectFourGame.GameWinner;
import HW2.ConnectFourGame.Player;
import HW2.ConnectFourGame.Piece;

public class PlayConnectFour {

    private static void printBoard(ConnectFourGame game) {
        for (int i=0; i< ConnectFourGame.BOARD_ROWS; i++) {
            for (int j=0; j<ConnectFourGame.BOARD_COLS; j++) {
                Piece currPiece = game.getSqure(i, j);
                switch (currPiece) {
                    case NONE:
                        System.out.print("x");
                        break;
                    case A:
                        System.out.print("a");
                        break;
                    case B:
                        System.out.print("b");
                }
            }
            System.out.println("");
        }
    }

    private static void doTurn(ConnectFourGame game, Player playerToMove) {
        printBoard(game);
        Scanner scanner = new Scanner(System.in);
        String playerName = playerToMove == Player.A ? "Player A" : "Player B";
        System.out.print(playerName + " choose a column for your piece: ");
        try {
            Integer columnForPiece = scanner.nextInt();
            if (game.isValidMove(playerToMove, columnForPiece)) {
                try {
                    game.makeMove(playerToMove, columnForPiece);
                } catch (InvalidMoveExcpetion e) {
                    System.out.println(playerName + " made an illegal move, try again.");
                    doTurn(game, playerToMove);
                }
            } else {
                doTurn(game, playerToMove);
            }
        } catch (InputMismatchException e) {
            System.out.println(playerName + " please enter a number between 1 and 7");
            doTurn(game, playerToMove);
        }
        scanner.close();
    }

    public static void main(String[] args) {
        ConnectFourGame game = new ConnectFourGame();

        String playerName = game.toMove == Player.A ? "Player A" : "Player B";
        System.out.println("Welcome to connect four! " + playerName + " will move first.");
        System.out.println("When prompted, enter a number between 1 and 7 to place a piece.");

        while (game.winner == GameWinner.NONE) { // TODO: this is only slightly confusing because why is 'not having a winner' the end state
            PlayConnectFour.doTurn(game, game.toMove);
        }
        switch (game.winner) {
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
    }
}
