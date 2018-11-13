package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board with numbered tiles.
     *
     * @param maxUndoMoves the maximum undos that the user can do
     */
    BoardManager(int maxUndoMoves) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Tile t = tiles.remove(tiles.size()-2);
        tiles.add(t);

        Collections.shuffle(tiles);
        makeSolvable(tiles);  // make sure the board is solvable
        this.board = new Board(tiles, maxUndoMoves);
    }

    /**
     * Manage a new shuffled board with image tiles.
     *
     * @param maxUndoMoves the maximum undos that the user can do
     */
    BoardManager(int maxUndoMoves, byte[] image) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Tile t = tiles.remove(tiles.size() - 2);
        tiles.add(t);

        Collections.shuffle(tiles);
        makeSolvable(tiles);  // make sure the board is solvable
        this.board = new Board(tiles, maxUndoMoves, image);
    }

    /**
     * Change the tiles so that it is solvable if it isn't. Leave it alone otherwise.
     * source: wikipedia https://en.wikipedia.org/wiki/15_puzzle
     *
     * @param tiles a list of tiles
     */
    private void makeSolvable(List<Tile> tiles) {
        int inv = 0;
        int rowDist = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(j).getId() < tiles.get(i).getId()
                        && tiles.get(j).getId() != tiles.size()
                        && tiles.get(i).getId() != tiles.size()) {
                    inv++;
                }
            }
            if (tiles.get(i).getId() == tiles.size()) {
                rowDist = Board.NUM_ROWS - 1 - i/Board.NUM_ROWS;
            }
        }
        if ((inv+rowDist) % 2 == 1) {
            Collections.swap(tiles, tiles.size()-1, tiles.size()-3);
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        int counter = 0;
        for (int row = 0; row < Board.NUM_ROWS; row++) {
            for (int col = 0; col < Board.NUM_COLS; col++) {
                counter++;
                if (board.getTile(row, col).getId() != counter) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = Board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        if(this.puzzleSolved()){ // don't let user shift tiles if game is finished
            return;
        }
        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = Board.numTiles();

        int[][] adjRowCols = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
        for (int[] adjRowCol: adjRowCols){
            if (0 <= adjRowCol[0] && adjRowCol[0] < Board.NUM_ROWS &&
                    0 <= adjRowCol[1] && adjRowCol[1] < Board.NUM_COLS){
                int adjRow = adjRowCol[0];
                int adjCol = adjRowCol[1];
                if (board.getTile(adjRow,adjCol).getId() == blankId) {
                    board.makeMove(row, col, adjRow, adjCol);
                    break;
                }
            }
        }

    }

}
