package fall2018.csc2017.slidingtiles;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.Tile;



public class SlidingTilesBoardManagerTest {

    /**
     * The board manager for testing.
     */
    SlidingTilesBoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles(int size) {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved board.
     *
     * @param size the size of the board (number of rows/cols).
     */
    private void setUpCorrect(int size) {
        List<SlidingTile> tiles = makeTiles(size);
        SlidingTilesBoard board = new SlidingTilesBoard(size, tiles, 3);
        boardManager = new SlidingTilesBoardManager(board);
    }

    /**
     * Make an unsolved board.
     *
     * @param size the size of the board (number of rows/cols).
     */
    private void setUpIncorrect(int size) {
        List<SlidingTile> tiles = makeTiles(size);
        Collections.reverse(tiles);
        SlidingTilesBoard board = new SlidingTilesBoard(size, tiles, 3);
        boardManager = new SlidingTilesBoardManager(board);
    }

    @Test
    public void testSolved() {
        setUpCorrect(3);
        assertTrue(boardManager.puzzleSolved());
    }


    //test not solved collections swap

    @Test
    public void testNotSolved() {
        setUpIncorrect(4);
        assertFalse(boardManager.puzzleSolved());
    }

    @Test
    public void testIsValidTap() {
        setUpCorrect(5);
        assertTrue(boardManager.isValidTap(23));
        assertTrue(boardManager.isValidTap(19));
        assertFalse(boardManager.isValidTap(18));
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(24));
    }

    @Test
    public void testTouchMove() {
        setUpIncorrect(3);
        Tile tile0 = boardManager.getBoard().getTile(0, 0);
        Tile tile1 = boardManager.getBoard().getTile(0, 1);
        boardManager.touchMove(1);
        assertEquals(boardManager.getBoard().getTile(0, 0), tile1);
        assertEquals(boardManager.getBoard().getTile(0, 1), tile0);
    }
}