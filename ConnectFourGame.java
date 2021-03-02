package HW2;

import java.util.ArrayList;

public class ConnectFourGame {

    public static final int BOARD_ROWS = 6;
    public static final int BOARD_COLS = 7;

    enum Piece {
        A,
        NONE,
        B
    }

    enum GameWinner {
        DRAW,
        NONE,
        A,
        B
    }

    enum Player {
        A,
        B
    }

    public GameWinner winner = GameWinner.NONE;

    public boolean isValidMove(Player player, Integer column) {
        return true;
    }

    public ArrayList<ArrayList<Piece>> makeMove(Player player, Integer column) throws InvalidMoveExcpetion {
        return new ArrayList<>();
    }

    public Player toMove = Player.A; 

    public Piece getSqure(int row, int col) {
        return Piece.A;
    }

    // make constructor
    public ConnectFourGame() {
        // TODO: make random player "toMove"
        // TODO: setup shit
    }
}