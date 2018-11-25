package fall2018.csc2017.slidingtiles;

import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends Board {

    /**
     * Returns the string filename to store highscores for certain boards
     * @param size Size of the game
     * @return file name
     */
    static String getHighScoreFile(int size){
        return "slidingTiles" + size + "x" + size + ".txt";
    }

    /**
     * The image for the tiles of the board. If null, numbered tiles are used.
     */
    private byte[] image = null;

    SlidingTilesBoard(int size, List<Tile> tiles) {
        super(size, tiles);
    }

    SlidingTilesBoard(int size, List<Tile> tiles, int maxUndoMoves) {
        super(size, tiles, maxUndoMoves);
    }

    SlidingTilesBoard(int size, List<Tile> tiles, int maxUndoMoves, byte[] image) {
        super(size, tiles, maxUndoMoves);
        this.image = image;
    }

    /**
     * Return image of the board
     *
     * @return returns the image
     */
    public byte[] getImage() {
        return image;
    }


    @Override
    public void gameMove(Move m) {
        swap((SlidingTilesMove) m);
    }

    /** Switch two tiles in the board
     * @param move move to make
     */
    private void swap(SlidingTilesMove move){
        int row1 = move.getRow1();
        int row2 = move.getRow2();
        int col1 = move.getCol1();
        int col2 = move.getCol2();

        Tile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }
}
