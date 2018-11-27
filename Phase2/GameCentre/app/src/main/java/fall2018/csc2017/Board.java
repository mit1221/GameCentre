package fall2018.csc2017;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * A general board.
 */
public abstract class Board extends Observable implements Serializable, Iterable<Tile> {

    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.getValue() - o2.getValue();
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
     * Return the list of moves made
     *
     * @return list of moves made
     */
    public UndoMoveList<Move> getUndoMoveList() {return moves;}

    /**
     * The number of moves made
     */
    private int movesMade;

    /**
     * Return the number of moves made
     *
     * @return number of moves made
     */
    public int getMovesMade() {
        return movesMade;
    }

    /**
     * Get the size of the board
     *
     * @return size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the string filename to store highscores for certain boards
     *
     * @param boardGame game to get highscore for
     * @param size      Size of the game
     * @return file name
     */
    public static String getHighScoreFile(Game boardGame, int size) {
        String filename = "";
        switch (boardGame) {
            case SLIDING_TILES:
                filename = "slidingTiles" + size + "x" + size + ".txt";
                break;
            case SUDOKU:
                filename = "sudoku" + size + "x" + size + ".txt";
                break;
        }
        return filename;
    }

    /**
     * The game specific move to make
     *
     * @param m the move to make
     */
    public abstract void gameMove(Move m);

    /**
     * Apply the move to the board
     * Note: this method is called for both a forward move and an undo move
     *
     * @param m the move to make
     */
    private void move(Move m) {
        gameMove(m);
        movesMade += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Helper method for setting the tiles for the board.
     *
     * @param tiles the tiles for the board
     */
    private void setTiles(List<? extends Tile> tiles) {
        this.tiles = new Tile[size][size];
        Iterator<? extends Tile> iter = tiles.iterator();
        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        this.movesMade = 0;
    }

    /**
     * A new board of tiles with maxUndoMoves moves that can be undone.
     *
     * @param size         the size of the board
     * @param tiles        the tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    public Board(int size, List<? extends Tile> tiles, int maxUndoMoves) {
        this.size = size;
        setTiles(tiles);
        moves = new UndoMoveList<>(maxUndoMoves);
    }

    /**
     * Return the number of tiles on the board.
     *
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
     * Make move and add it to the moves made.
     *
     * @param m move to make
     */
    public void makeMove(Move m) {
        move(m);
        moves.addMove(m);
    }

    /**
     * Undo the last move made.
     */
    public void undoLastMove() {
        move(moves.popLastMove().reverseMove());
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Return the board.
     *
     * @return the board
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * Iterate over the tiles of the board.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The location of the next tile in the board.
         */
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex < Math.pow(size, 2);
        }

        @Override
        public Tile next() {
            if (hasNext()) {
                Tile nextTile = tiles[nextIndex / size][nextIndex % size];
                nextIndex++;
                return nextTile;
            }
            throw new NoSuchElementException("Out of range.");
        }
    }
}
