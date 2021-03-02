package HW2;

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
        // should be correct player making the move and a valid column
        return true;
    }

    public void makeMove(Player player, Integer column) throws InvalidMoveExcpetion {}

    public Player playerToMove = Player.A; 

    public Piece getSqure(int row, int col) {
        return Piece.A;
    }

    // make constructor
    public ConnectFourGame() {
        // TODO: make random player "toMove"
        // TODO: setup shit
    }
}