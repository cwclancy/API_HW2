package HW2;

/**
 * Thrown if an invalid move is attempted during a ConnectFourGame. An 
 * invalid move is placing the piece in a full column, a column lower
 * than 0 or higher than {@code BOARD_COLS}, or when it's not your turn.
 */
public class InvalidMoveExcpetion extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    /**
     * The constructor for an exception for an invalid move. This exception is simply thrown
     * <blockquote><pre> throw new InvalidMoveException(); </pre></blockquote>
     */
    public InvalidMoveExcpetion() {
        super();
    }
}
