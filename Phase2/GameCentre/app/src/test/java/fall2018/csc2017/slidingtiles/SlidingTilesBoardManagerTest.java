package fall2018.csc2017.slidingtiles;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import android.content.Context;
import android.widget.Toast;


import fall2018.csc2017.*;

public class SlidingTilesBoardManagerTest {

    /**
     * The board manager for testing.
     */
    SlidingTilesBoardManager boardManager;

    /**
     * The movement controller for testing.
     */
    MovementController movementController;


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
        Collections.swap(tiles, size*size-1, size*size-2);
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

//    @Test
//    public void testIsValidTap() {
//        setUpCorrect(5);
//        assertTrue(boardManager.isValidMove((SlidingTilesMove.createMove(23,boardManager.getBoard()))));
//        assertTrue(boardManager.isValidMove((SlidingTilesMove.createMove(19,boardManager.getBoard()))));
//        assertFalse(boardManager.isValidMove((SlidingTilesMove.createMove(18,boardManager.getBoard()))));
//        assertFalse(boardManager.isValidMove((SlidingTilesMove.createMove(0,boardManager.getBoard()))));
//        assertFalse(boardManager.isValidMove((SlidingTilesMove.createMove(24,boardManager.getBoard()))));
//    }

    @Test
    public void testTouchMove() {
        setUpIncorrect(3);
        Tile tile0 = boardManager.getBoard().getTile(2, 2);
        Tile tile1 = boardManager.getBoard().getTile(2, 1);
        boardManager.touchMove(SlidingTilesMove.createMove(8,boardManager.getBoard()));
        assertEquals(boardManager.getBoard().getTile(2, 2), tile1);
        assertEquals(boardManager.getBoard().getTile(2, 1), tile0);
    }

    @Test
    public void testMove(){
        setUpCorrect(4);
        Context context = mock(Context.class);
        movementController = new MovementController();
        movementController.setBoardManager(boardManager);
        movementController.processMove(context, SlidingTilesMove.createMove(14,boardManager.getBoard()));
        verify(Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT)).show();

    }
}