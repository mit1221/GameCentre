package fall2018.csc2017;

/**
 * Controller for a general board game activity.
 */
public class MovementController {

    private Model model = null;

    public MovementController() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Process a move made on the board.
     *
     * @param move the move to make
     * @return "YOU WIN" if the puzzle is solved, "Invalid Tap" if the tap is not allowed and null otherwise
     */
    public String processMove(Move move) {
        String result = null;
        if (model.isValidMove(move)) {
            model.makeMove(move);
            if (model.puzzleSolved()) {
                result = "YOU WIN!";
            }
        } else {
            result = "Invalid Tap";
        }
        return result;
    }
}
