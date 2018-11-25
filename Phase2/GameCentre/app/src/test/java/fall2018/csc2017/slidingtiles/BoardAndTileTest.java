package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Tile;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /** The board manager for testing. */
    SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        slidingTilesBoardManager = new SlidingTilesBoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTilesBoardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, slidingTilesBoardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, slidingTilesBoardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingTilesBoardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTilesBoardManager.getBoard().getTile(0, 1).getId());
        slidingTilesBoardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTilesBoardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTilesBoardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingTilesBoardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, slidingTilesBoardManager.getBoard().getTile(3, 3).getId());
        slidingTilesBoardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, slidingTilesBoardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingTilesBoardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, slidingTilesBoardManager.isValidTap(11));
        assertEquals(true, slidingTilesBoardManager.isValidTap(14));
        assertEquals(false, slidingTilesBoardManager.isValidTap(10));
    }
}

