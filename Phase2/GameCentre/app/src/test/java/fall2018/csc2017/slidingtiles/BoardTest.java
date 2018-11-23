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

import static org.junit.Assert.*;


public class BoardTest {

    @Test
    public void testNumTiles() {
        Board board = mock(Board.class);
        Tile tile0 = new Tile(0);
        Tile tile1 = new Tile(1);
        when(board.getTile(0,0)).thenReturn(tile0);
        when(board.getTile(0,1)).thenReturn(tile1);

//        board.swap(0,0,0,1);

        Assert.assertEquals(board.getTile(0,0), tile0);
        Assert.assertEquals(board.getTile(0,1), tile1);

//        verify(board).swap(0,0,0,1);

    }

}
