package fall2018.csc2017.slidingtiles;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import fall2018.csc2017.UndoMoveList;

import static org.junit.Assert.*;


public class BoardManagerTest {

    /** The board manager for testing. */
    BoardManager boardManager;

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
     * @param size size of the board (number of rows/cols).
     */
    private void setUpCorrect(int size) {
        Board.NUM_ROWS = Board.NUM_COLS = size;
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Make a solved Board.
     * @param size size of the board (number of rows/cols).
     */
    private void setUpIncorrect(int size) {
        Board.NUM_ROWS = Board.NUM_COLS = size;
        List<Tile> tiles = makeTiles();
        Collections.reverse(tiles);
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }


    @Test
    public void testSolved(){
        setUpCorrect(3);
        assertTrue(boardManager.puzzleSolved());
    }

    //test not solved collections swap

    @Test
    public void testNotSolved(){
        setUpIncorrect(4);
        assertFalse(boardManager.puzzleSolved());
    }

    @Test
    public void testIsValidTap(){
        setUpCorrect(5);
        assertTrue(boardManager.isValidTap(23));
        assertTrue(boardManager.isValidTap(19));
        assertFalse(boardManager.isValidTap(18));
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(24));

    }

    @Test
    public void testTouchMove(){
        setUpIncorrect(4);
        Tile tile0 = boardManager.getBoard().getTile(0,0);
        Tile tile1 = boardManager.getBoard().getTile(0,1);
        boardManager.touchMove(1);
        assertEquals(boardManager.getBoard().getTile(0,0), tile1);
        assertEquals(boardManager.getBoard().getTile(0,1), tile0);
    }

}
