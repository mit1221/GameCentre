package fall2018.csc2017;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.slidingtiles.Tile;

/**
 * A general board.
 */
public abstract class Board extends Observable implements Serializable, Iterable<Tile> {

    static Comparator<Score> getComparator(){
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return  o1.getValue() - o2.getValue();
            }
        };
    }

    /**
     * The size of the board. The board is square so # rows = # columns.
     */
    private int size;

    /**
     * The tiles on the board.
     */
    protected Tile[][] tiles;

    /**
     * Keep track of moves made
     */
    private UndoMoveList<Move> moves;

    /**
     * The number of moves made
     */
    private int movesMade;

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
     * Apply the move to the board
     * @param m the move to make
     */
    public abstract void move(Move m);

    /**
     * Helper method for setting the tiles for the board.
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
     * A new board of tiles with a maximum of 3 moves that can be undone.
     *
     * @param size the size of the board
     * @param tiles the tiles for the board
     */
    public Board(int size, List<Tile> tiles) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(3);
    }

    /**
     * A new board of tiles with maxUndoMoves moves that can be undone.
     *
     * @param size the size of the board
     * @param tiles the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    public Board(int size, List<Tile> tiles, int maxUndoMoves) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(maxUndoMoves);
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return (int) Math.pow(this.size, 2);
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Make move to swap tiles at the given positions
     * @param m move to make
     */
    public void makeMove(Move m) {
        move(m);
        movesMade += 1;
        moves.addMove(m);
    }

    /**
     * Undo the last move made
     */
    public void undoLastMove(){
        Move lastMove = moves.popLastMove();
        move(lastMove.reverseMove());
        movesMade += 1;
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
