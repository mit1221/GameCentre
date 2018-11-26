package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Tile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager implements BoardManager {

    /**
     * The board being managed.
     */
    private SlidingTilesBoard board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SlidingTilesBoardManager(SlidingTilesBoard board) {
        this.board = board;
    }

    @Override
    public SlidingTilesBoard getBoard() {
        return board;
    }

    /**
     * Helper method for generating all the tiles for the board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    private List<Tile> generateTiles(int size) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = (int) Math.pow(size, 2);
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        // set the last tile to a blank tile
        tiles.get(tiles.size() - 1).setToBlankTile();

        Tile t = tiles.remove(tiles.size() - 2);
        tiles.add(t);

        Collections.shuffle(tiles);
        makeSolvable(size, tiles);  // make sure the board is solvable
        return tiles;
    }

    /**
     * Manage a new shuffled board with numbered tiles.
     *
     * @param size         size of the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SlidingTilesBoardManager(int size, int maxUndoMoves) {
        this.board = new SlidingTilesBoard(size, generateTiles(size), maxUndoMoves);
    }

    /**
     * Manage a new shuffled board with image tiles.
     *
     * @param size         size of the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SlidingTilesBoardManager(int size, int maxUndoMoves, byte[] image) {
        this.board = new SlidingTilesBoard(size, generateTiles(size), maxUndoMoves, image);
    }

    /**
     * Change the tiles so that it is solvable if it isn't. Leave it alone otherwise.
     * source: wikipedia https://en.wikipedia.org/wiki/15_puzzle
     *
     * @param size  size of the board
     * @param tiles a list of tiles
     */
    private void makeSolvable(int size, List<Tile> tiles) {
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
                rowDist = size - 1 - i / size;
            }
        }
        if ((inv + rowDist) % 2 == 1) {
            Collections.swap(tiles, tiles.size() - 1, tiles.size() - 3);
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = true;

        Tile lastTile = null;
        for (Tile tile : board) {
            if (lastTile != null && lastTile.compareTo(tile) <= 0) {
                solved = false;
                break;
            }
            lastTile = tile;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    public boolean isValidTap(int position) {
        int row = position / board.getSize();
        int col = position % board.getSize();
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getSize() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getSize() - 1 ? null : board.getTile(row, col + 1);
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
    @Override
    public void touchMove(int position) {
        if (this.puzzleSolved()) { // don't let user shift tiles if game is finished
            return;
        }
        int row = position / board.getSize();
        int col = position % board.getSize();
        int blankId = board.numTiles();

        int[][] adjRowCols = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};
        for (int[] adjRowCol : adjRowCols) {
            if (0 <= adjRowCol[0] && adjRowCol[0] < board.getSize() &&
                    0 <= adjRowCol[1] && adjRowCol[1] < board.getSize()) {
                int adjRow = adjRowCol[0];
                int adjCol = adjRowCol[1];
                if (board.getTile(adjRow, adjCol).getId() == blankId) {
                    SlidingTilesMove move = new SlidingTilesMove(row, col, adjRow, adjCol);
                    board.makeMove(move);
                    break;
                }
            }
        }

    }

}
