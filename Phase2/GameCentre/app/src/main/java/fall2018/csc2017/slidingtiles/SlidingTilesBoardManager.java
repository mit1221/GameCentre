package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager extends BoardManager {

    /**
     * Helper method for generating all the sliding tiles for the board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    @Override
    public List<SlidingTile> generateTiles(int size) {
        List<SlidingTile> tiles = new ArrayList<>();
        int numTiles = (int) Math.pow(size, 2);
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum));
        }

        // set the last tile to a blank tile
        tiles.get(tiles.size() - 1).setToBlankTile();

        SlidingTile t = tiles.remove(tiles.size() - 2);
        tiles.add(t);

        Collections.shuffle(tiles);
        makeSolvable(size, tiles);  // make sure the board is solvable
        return tiles;
    }

    /**
     * Manage a new shuffled board with image tiles.
     *
     * @param size         size of the board
     * @param maxUndoMoves the maximum undos that the user can do
     * @param image the image to use as the background
     */
    SlidingTilesBoardManager(int size, int maxUndoMoves, byte[] image) {
        super(maxUndoMoves);
        setBoard(new SlidingTilesBoard(size, generateTiles(size), image));
    }

    /**
     * Change the tiles so that it is solvable if it isn't. Leave it alone otherwise.
     * source: wikipedia https://en.wikipedia.org/wiki/15_puzzle
     *
     * @param size  size of the board
     * @param tiles a list of tiles
     */
    private void makeSolvable(int size, List<SlidingTile> tiles) {
        int inv = 0;
        int rowDist = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(j).getId() < tiles.get(i).getId()
                        && tiles.get(j).getId() != tiles.size() - 1
                        && tiles.get(i).getId() != tiles.size() - 1) {
                    inv++;
                }
            }
            if (tiles.get(i).getId() == tiles.size() - 1) {
                rowDist = size - 1 - i / size;
            }
        }
        if ((inv + rowDist) % 2 == 1) {
            Collections.swap(tiles, tiles.size() - 1, tiles.size() - 3);
        }
    }

    /**
     * Switch two tiles in the board
     *
     * @param m move to make
     */
    @Override
    public void gameMove(Move m) {
        SlidingTilesMove move = (SlidingTilesMove) m;
        int row1 = move.getRow1();
        int row2 = move.getRow2();
        int col1 = move.getCol1();
        int col2 = move.getCol2();

        Tile temp = getBoard().getTile(row1, col1);
        SlidingTilesBoard board = (SlidingTilesBoard) getBoard();

        board.setTile(row1, col1, getBoard().getTile(row2, col2));
        board.setTile(row2, col2, temp);
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
        for (Tile tile : getBoard()) {
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
     * @param m the move to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    // TODO: change this back to the old code
    public boolean isValidMove(Move m) {
        SlidingTilesMove move = (SlidingTilesMove) m;
        return move.getRow1() != move.getRow2() || move.getCol1() != move.getCol2();
    }
}
