package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;

import java.util.Comparator;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Iterable<Tile>, Serializable {

    public static Comparator<Score> getComparator(){
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return  o1.getValue() -o2.getValue();
            }
        };
    }

    /**
     * Returns the string filename to store highscores for certain boards
     * @param size Size of the game
     * @return file name
     */
    public static String getHighScoreFile(int size){
        return "slidingTiles" + size + "x" + size + ".txt";
    }

    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    /**
     * The number of columns.
     */
    static int NUM_COLS;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * Keep track of moves made
     */
    private UndoMoveList<int[]> moves;

    /**
     * Returns the width of the board instance
     * @return the board width
     */
    public int getBoardWidth(){return tiles[0].length;}

    /**
     * Returns the height of the board instance
     * @return the board height
     */
    public int getBoardHeight(){return tiles.length;}

    /**
     * The number of moves made
     */
    private int movesMade;

    /**
     * The name of this game
     */
    private String gameName;

    /**
     * The image for the tiles of the board. If null, numbered tiles are used.
     */
    private byte[] image = null;

    /**
     * Return the number of moves made
     * @return number of moves made
     */
    public int getMovesMade(){return movesMade;}

    /**
     * A new board of tiles in row-major order with a maximum of 3 moves that can be undone.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        moves = new UndoMoveList<>(3);
        movesMade = 0;
        gameName = "Sliding Tiles";
    }

    /**
     * A new board of tiles in row-major order with maxUndoMoves moves that can be undone.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    Board(List<Tile> tiles, int maxUndoMoves) {
        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        moves = new UndoMoveList<>(maxUndoMoves);
        movesMade = 0;
        gameName = "Sliding Tiles";
    }

    /**
     * A new board of tiles in row-major order with maxUndoMoves moves that can be undone and
     * .
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles        the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     * @param image      byte array of the background image of the board
     */
    Board(List<Tile> tiles, int maxUndoMoves, byte[] image) {
        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        moves = new UndoMoveList<>(maxUndoMoves);
        movesMade = 0;
        gameName = "Sliding Tiles";
        this.image = image;
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    static int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    public String getGameName() {
        return gameName;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return image of the board
     *
     * @return returns the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Make move to swap tiles at the given positions
     * @param row1 Row of first tile to swap
     * @param col1 Column of first tile to swap
     * @param row2 Row of second tile to swap
     * @param col2 Column of second tile to swap
     */
    void makeMove(int row1, int col1, int row2, int col2) {
        swap(row1, col1, row2, col2);
        moves.addMove(new int[]{row1, col1, row2, col2});
    }

    /** Switch two tiles in the board
     * @param row1 Row of first tile to swap
     * @param col1 Column of first tile to swap
     * @param row2 Row of second tile to swap
     * @param col2 Column of second tile to swap
     * */
    private void swap(int row1, int col1, int row2, int col2){
        Tile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
        movesMade += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Undo the last move made
     */
    public void undoLastMove(){
        int[] lastMove = moves.popLastMove();
        swap(lastMove[2], lastMove[3], lastMove[0], lastMove[1]);
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @Override
    public Iterator<Tile> iterator(){
        return new TileIterator();
    }

    private class TileIterator implements Iterator<Tile> {
        int nextRowIndex = 0;
        int nextColIndex = 0;
        @Override
        public boolean hasNext() {
            return nextRowIndex + nextColIndex != NUM_ROWS + NUM_COLS - 2;
        }

        @Override
        public Tile next() {
            Tile next = getTile(nextRowIndex, nextColIndex);
            if(nextRowIndex != NUM_ROWS-1){
                nextRowIndex++;
            }else{
                nextRowIndex = 0;
                nextColIndex++;
            }
            return next;
        }
    }
}
