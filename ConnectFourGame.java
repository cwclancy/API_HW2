package HW2;

import java.util.concurrent.ThreadLocalRandom;

public class ConnectFourGame {

    public static final int BOARD_ROWS = 6;
    public static final int BOARD_COLS = 7;

    private GameState state = GameState.IN_PROGRESS;
    private GameWinner winner = GameWinner.NONE;
    private GamePlayer playerToMove = GamePlayer.A;
    private GamePiece[][] board = new GamePiece[BOARD_ROWS][BOARD_COLS];

    public GameWinner winner() {
        return winner;
    }

    public GameState state() {
        return state;
    }

    public GamePlayer playerToMove() {
        return playerToMove;
    }

    public GamePiece getSquare(int row, int col) throws IndexOutOfBoundsException {
        return board[row][col];
    }

    public boolean isValidMove(GamePlayer player, int column)  {
        return !isColumnFull(column) && player == playerToMove;
    }

    public void makeMove(GamePlayer player, int column) throws InvalidMoveExcpetion {
        if (!isValidMove(player, column)) {
            throw new InvalidMoveExcpetion();
        }

        int rowForPlacedPiece = rowForPlacedPiece(column);
        board[rowForPlacedPiece][column] = player == GamePlayer.A ? GamePiece.A : GamePiece.B;

        if (playerWon()) {
            state = GameState.OVER;
            winner = player == GamePlayer.A ? GameWinner.A : GameWinner.B;
        }
        
        if (boardFull()) {
            state = GameState.OVER;
            winner = GameWinner.DRAW;
        }

        playerToMove = player == GamePlayer.A ? GamePlayer.B : GamePlayer.A;
    }

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
        if (0 < col || col >= BOARD_COLS) {
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