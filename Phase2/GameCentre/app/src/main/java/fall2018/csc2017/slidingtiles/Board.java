package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.Score;
import fall2018.csc2017.UndoMoveList;

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
    static String getHighScoreFile(int size){
        return "slidingTiles" + size + "x" + size + ".txt";
    }

    /**
     * The size of the board. The board is square so # rows = # columns.
     */
    private int size;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * Keep track of moves made
     */
    private UndoMoveList<int[]> moves;

    /**
     * The number of moves made
     */
    private int movesMade;

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
     * Get the size of the board
     * @return size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Helper method for setting the sliding tiles for the board.
     *
     * @param tiles the tiles for the board
     */
    private void setTiles(List<Tile> tiles) {
        this.tiles = new Tile[size][size];
        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        this.movesMade = 0;
    }

    /**
     * A new board of tiles in row-major order with a maximum of 3 moves that can be undone.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param size the size of the board
     * @param tiles the tiles for the board
     */
    Board(int size, List<Tile> tiles) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(3);
    }

    /**
     * A new board of tiles in row-major order with maxUndoMoves moves that can be undone.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param size the size of the board
     * @param tiles the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    Board(int size, List<Tile> tiles, int maxUndoMoves) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(maxUndoMoves);
    }

    /**
     * A new board of tiles in row-major order with maxUndoMoves moves that can be undone and
     * .
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param size the size of the board
     * @param tiles        the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     * @param image      byte array of the background image of the board
     */
    Board(int size, List<Tile> tiles, int maxUndoMoves, byte[] image) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(maxUndoMoves);
        this.image = image;
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return (int) Math.pow(this.size, 2);
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
    void undoLastMove(){
        int[] lastMove = moves.popLastMove();
        swap(lastMove[2], lastMove[3], lastMove[0], lastMove[1]);
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull @Override
    public Iterator<Tile> iterator(){
        return new TileIterator();
    }

    private class TileIterator implements Iterator<Tile> {
        int nextRowIndex = 0;
        int nextColIndex = 0;
        @Override
        public boolean hasNext() {
            return nextRowIndex + nextColIndex != (2 * size) - 2;
        }

        @Override
        public Tile next() {
            Tile next = getTile(nextRowIndex, nextColIndex);
            if(nextRowIndex != size - 1){
                nextRowIndex++;
            }else{
                nextRowIndex = 0;
                nextColIndex++;
            }
            return next;
        }
    }
}
