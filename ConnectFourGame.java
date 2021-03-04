package HW2;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code ConnectFourGame} class represents a game of connect four - including
 * the board and game state. This class is typically used to simplify the creation
 * of 2-player connect four client.
 * 
 * <p>
 * To create a Connect Four Game (an instance of this class) it's as simple as
 * <blockquote><pre>
 * ConnectFourGame game = new ConnectFourGame();
 * </pre></blockquote>
 * When instantiated, the state of the 
 * game is {@code GameState.IN_PROGRESS} and a random GamePlayer A or B is selected
 * to make the first move. The state of the game can be checked 
 * with {@code game.state()} and the player whose turn it is with {@code game.playerToMove()}. 
 * 
 * <p>
 * <b>To get started</b>, a typical client will write code like:
 * <blockquote><pre>
        // create an instance of the game
        ConnectFourGame game = new ConnectFourGame();
        // keep asking user for moves while the game hasn't ended
        while (game.state() == GameState.IN_PROGRESS) {
            // read input from stdin
            Scanner scanner = new Scanner(System.in);
            System.out.print("Select a column: ");
            int colForPlayer = scanner.nextInt() - 1;
            // check if the move is valid
            if (game.isValidMove(game.playerToMove(), colForPlayer)) {
                // make the move
                game.makeMove(game.playerToMove(), colForPlayer);
            }
        }
        // GameState is now GameState.OVER
        switch (game.winner()) {
            // case on GameWinner.A, GameWinner.B, GameWinner.DRAW
        }
 * </pre></blockquote>
 * <p>
 * This class in not thread safe.
 * </p>
 */
public class ConnectFourGame {
    /**
     * A constant (6) indicating the number of rows for a Connect Four board. 
     */
    public static final int BOARD_ROWS = 6;
    /**
     * A constant (7) indicating the number of columns for a Connect Four board.
     */
    public static final int BOARD_COLS = 7;

    private GameState state = GameState.IN_PROGRESS;
    private GameWinner winner = GameWinner.NONE;
    private GamePlayer playerToMove = GamePlayer.A;
    private GamePiece[][] board = new GamePiece[BOARD_ROWS][BOARD_COLS];

    /**
     * Returns the winner of the Connect Four Game if the game is over, 
     * otherwise it returns {@code GameWinner.NONE}. If the game resulted in
     * a draw, the function returns {@code GameWinner.DRAW}. The game is over if
     * {@code game.state() == GameState.OVER}.
     * 
     * @return The winner of the Connect Four Game, DRAW, or NONE depending on
     * the state of the game.
     */
    public GameWinner winner() {
        return winner;
    }

    /**
     * Returns if the game is {@code IN_PROGRESS} or {@code OVER}. If the game
     * in {@code OVER}, then the value of {@code game.winner()} will be
     * {@code A}, {@code B}, or {@code DRAW}. If the game is {@code IN_PROGRESS}, 
     * the value of {@code game.winner()} will be {@code NONE}.
     * 
     * @return if the game is {@code GameState.IN_PROGRESS} or {@code GameState.OVER}.
     */
    public GameState state() {
        return state;
    }

    /**
     * Returns the player who makes the next move. At the beginning of the game
     * the player is chosen randomly. Afterwards it changes between player {@code A} and
     * {@code B}. If {@code game.playerToMove()} return {@code A}, then after a successful move
     * is made, {@code game.playerToMove()} will return {@code B}. And vice-versa.
     *  
     * @return The player to make the next move.
     */
    public GamePlayer playerToMove() {
        return playerToMove;
    }

    /**
     * Returns the {@code GamePiece} of the Connect Four Board at the given row and column.
     * The board is 0 indexed and has 6 rows and 7 columns. This function will return
     * {@code A}, {@code B}, or {@code NONE} based on if Player A, Player B, or no player
     * has placed a piece at the position in the board - respectively.
     * @param row the row of the board piece you want to query.
     * @param col the column of the board piece you want to query.
     * @return The GamePiece at the specified ({@code row}, {@code col}) in the Connect Four Board.
     * @throws IndexOutOfBoundsException throws if {@code row, col < 0} or {@code row >= BOARD_ROWS} or {@code col >= BOARD_COLS}. 
     */
    public GamePiece getSquare(int row, int col) throws IndexOutOfBoundsException {
        return board[row][col];
    }

    /**
     * Returns {@code true} if {@code game.nextToMove()} can legally place a 
     * piece in {@code column}. Tthe {@code column} must be in the board, and not full.
     * 
     * @param column the column to place the piece in
     * 
     * @return {@code true} if the {@code game.nextToMove()} player can legally place a piece in the {@code column}, and {@code false} otherwise.
     */
    public boolean isValidMove(int column)  {
        return !isColumnFull(column);
    }

    /**
     * Updates the {@code ConnectFourGame} with the move by {@code game.playerToMove()} placing a piece in {@code column}. A piece
     * is added to the board by the {@code game.playerToMove()} in the {@code column} specified. If this 
     * results in four in a row or full board, the {@code game.state()} becomes {@code OVER}
     * and the winner (or draw state) can be found in {@code game.winner()}. Finally, the
     * {@code game.playerToMove()} switches to the other player.
     * 
     * @param column The column to add the piece to.
     * 
     * @throws InvalidMoveExcpetion Thrown if {@code isValidMove(column)} is false.
     * 
     */
    public void makeMove(int column) throws InvalidMoveExcpetion {
        if (!isValidMove(column)) {
            throw new InvalidMoveExcpetion();
        }

        int rowForPlacedPiece = rowForPlacedPiece(column);
        board[rowForPlacedPiece][column] = playerToMove == GamePlayer.A ? GamePiece.A : GamePiece.B;

        if (playerWon()) {
            state = GameState.OVER;
            winner = playerToMove == GamePlayer.A ? GameWinner.A : GameWinner.B;
        }
        
        if (boardFull()) {
            state = GameState.OVER;
            winner = GameWinner.DRAW;
        }

        playerToMove = playerToMove == GamePlayer.A ? GamePlayer.B : GamePlayer.A;
    }

    /**
     * Returns a string representation of the Connect Four Game's board.
     * Empty squres are indicated by an {@code x} Player A's piece are indicated
     * by an {@code a}, and Player B's pieces are indictaed by a {@code b}.
     * 
     * @return A string representation of the Connect Four Game's board.
     */
    public String toString() {
        String boardString = "";
        for (int i=0; i<BOARD_ROWS; i++) {
            for (int j=0; j<BOARD_COLS; j++) {
                GamePiece currPiece = getSquare(i, j);
                switch (currPiece) {
                    case NONE:
                        boardString += "x";
                        break;
                    case A:
                        boardString += "a";
                        break;
                    case B:
                        boardString += "b";
                }
            }
            boardString += i == BOARD_ROWS-1 ? "" : "\n";
        }
        return boardString;
    }

    /**
     * Creates a {@code ConnectFourGame}. The board is initially empty, and the {@code game.playerToMove()}
     * is random. The class is simply instantiated 
     * <blockquote><pre>
     * ConnectFourGame game = new ConnectFourGame();
     * </pre></blockquote>
     */
    public ConnectFourGame() {
        // choose random player to be first
        int coinFlip = ThreadLocalRandom.current().nextInt(2);
        if (coinFlip == 0) {
            playerToMove = GamePlayer.A;
        } else {
            playerToMove = GamePlayer.B;
        }

        // initalize board to empty
        for (int row=0; row<BOARD_ROWS; row++) {
            for (int col=0; col<BOARD_COLS; col++) {
                board[row][col] = GamePiece.NONE;
            }
        }
    }

    private boolean isColumnFull(int col) {
        if (0 > col || col >= BOARD_COLS) {
            return true;
        }
        for (int row=0; row<BOARD_ROWS; row++) {
            if (board[row][col] == GamePiece.NONE) {
                return false;
            }
        }
        return true;
    }

    private int rowForPlacedPiece(int col) {
        for (int row=BOARD_ROWS-1; row >= 0; row--) {
            if (board[row][col] == GamePiece.NONE) {
                return row;
            }
        }
        return -1; // this should never be returned
    }

    private boolean boardFull() {
        for (int row=0; row<BOARD_ROWS; row++) {
            for (int col=0; col<BOARD_COLS; col++) {
                if (getSquare(row, col) == GamePiece.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean playerWon() {
        for (int row=0; row<BOARD_ROWS; row++) {
            for (int col=0; col<BOARD_COLS; col++) {
                if (checkDiagonals(row, col) || 
                    checkRight(row, col) || 
                    checkLeft(row, col) ||
                    checkUp(row, col) ||
                    checkDown(row, col) ||
                    checkDiagonals(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRight(int row, int col) {
        GamePiece winningPiece = getSquare(row, col);

        if (winningPiece == GamePiece.NONE) {
            return false;
        }
        
        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row, col+i+1) != winningPiece) {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    private boolean checkLeft(int row, int col) {
        GamePiece winningPiece = getSquare(row, col);

        if (winningPiece == GamePiece.NONE) {
            return false;
        }
        
        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row, col-i-1) != winningPiece) {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    private boolean checkUp(int row, int col) {
        GamePiece winningPiece = getSquare(row, col);

        if (winningPiece == GamePiece.NONE) {
            return false;
        }
        
        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row+i+1, col) != winningPiece) {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    private boolean checkDown(int row, int col) {
        GamePiece winningPiece = getSquare(row, col);

        if (winningPiece == GamePiece.NONE) {
            return false;
        }
        
        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row-i-1, col) != winningPiece) {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    private boolean checkDiagonals(int row, int col) {
        GamePiece winningPiece = getSquare(row, col);

        boolean topLeft = true;
        boolean topRight = true;
        boolean bottomLeft = true;
        boolean bottomRight = true;

        if (winningPiece == GamePiece.NONE) {
            return false;
        }

        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row-i-1, col-i-1) != winningPiece) {
                    topLeft = false;
                }
            } catch (IndexOutOfBoundsException e) {
                topLeft = false;
            }
        }

        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row-i-1, col+i+1) != winningPiece) {
                    topRight = false;
                }
            } catch (IndexOutOfBoundsException e) {
                topRight = false;
            }
        }

        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row+i+1, col-i-1) != winningPiece) {
                    bottomLeft = false;
                }
            } catch (IndexOutOfBoundsException e) {
                bottomLeft = false;
            }
        }

        for (int i=0; i<3; i++) {
            try {
                if (getSquare(row+i+1, col+i+1) != winningPiece) {
                    bottomRight = false;
                }
            } catch (IndexOutOfBoundsException e) {
                bottomRight = false;
            }
        }


        return topLeft || topRight || bottomLeft || bottomRight;
    }
}