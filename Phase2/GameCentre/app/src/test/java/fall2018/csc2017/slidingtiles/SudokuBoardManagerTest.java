package fall2018.csc2017.slidingtiles;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fall2018.csc2017.MovementController;
import fall2018.csc2017.sudoku.BoardGenerator;
import fall2018.csc2017.sudoku.SudokuBoard;
import fall2018.csc2017.sudoku.SudokuBoardManager;
import fall2018.csc2017.sudoku.SudokuEditableTile;
import fall2018.csc2017.sudoku.SudokuLockedTile;
import fall2018.csc2017.sudoku.SudokuMove;
import fall2018.csc2017.sudoku.SudokuTile;

import android.content.Context;
import android.widget.Toast;


import fall2018.csc2017.*;

public class SudokuBoardManagerTest {

    int[][] board =  {{4, 5, 1, 2, 3, 6, 8, 7, 9},{8, 6, 3, 7, 4, 9, 2, 1, 5},{7, 9, 2, 1, 8, 5, 3, 4, 6},{2, 4, 5, 3, 9, 8, 7, 6, 1},{1, 3, 6, 5, 7, 4, 9, 2, 8},{0, 7, 8, 6, 1, 2, 4, 5, 3},{3, 2, 4, 9, 6, 1, 5, 8, 7},{5, 1, 9, 8, 2, 7, 6, 3, 4},{6, 8, 7, 4, 5, 3, 1, 9, 2}};
    int[][] solvedBoard = {{1, 7, 9, 3, 2, 4, 6, 5, 8},{5, 2, 6, 7, 8, 1, 3, 4, 9},{8, 3, 4, 6, 9, 5, 1, 2, 7},{2, 1, 3, 5, 6, 9, 8, 7, 4},{4, 6, 7, 2, 1, 8, 9, 3, 5},{9, 5, 8, 4, 3, 7, 2, 6, 1},{6, 8, 5, 9, 4, 2, 7, 1, 3},{3, 4, 1, 8, 7, 6, 5, 9, 2},{7, 9, 2, 1, 5, 3, 4, 8, 6}};
    /**
     * The Board Manager for Testing.
     */
    SudokuBoardManager boardManager;

//    /**
//     * The movement controller for testing.
//     */
//    MovementController movementController;

    /**
     * Board Generator
     */
    BoardGenerator generator;

    private SudokuBoard setCorrect(){
//        boardManager = new SudokuBoardManager(3);
//        List<SudokuTile> solvedTiles = boardManager.genTiles(9);
//        SudokuBoard board = new SudokuBoard(solvedTiles, 3);
//        boardManager = new SudokuBoardManager(board);
        List<SudokuTile> tiles = new ArrayList<>();
        int id = 0;
        while(id < 82) {
            for (int i = 0; i < solvedBoard.length; i++) {
                for (int j = 0; j < 9; j++) {
                    SudokuTile tile;
                    tile = solvedBoard[i][j] == 0 ? new SudokuEditableTile(id) : new SudokuLockedTile(id, j);
                    tiles.add(tile);
                    id++;
                }

            }

        }
//        System.out.println(tiles);
        SudokuBoard board = new SudokuBoard(tiles);
        return board;


    }
//
//    private void setUnfinished(){
//        boardManager = new SudokuBoardManager(3);
//        List<SudokuTile> solvedTiles = boardManager.generateTiles(9);
//        SudokuBoard board = new SudokuBoard(solvedTiles, 3);
//        boardManager = new SudokuBoardManager(board);


    private List<SudokuTile> specficBoard(){
        List<SudokuTile> tiles = new ArrayList<>();
        int id = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    SudokuTile tile;
                    System.out.println(board[i][j]);
                    tile = board[i][j] == 0 ? new SudokuEditableTile(id) : new SudokuLockedTile(id, j);
                    tiles.add(tile);
                    id++;
                }
            }

        return tiles;
    }

    @Test
    public void testSolved(){
        boardManager = new SudokuBoardManager(setCorrect(), 3);
        assertTrue(boardManager.puzzleSolved());
    }

    @Test
    public void testNotSolved(){
        SudokuBoard brd = new SudokuBoard(this.specficBoard());
        boardManager = new SudokuBoardManager(brd, 3);
        assertFalse(boardManager.puzzleSolved());
    }

    @Test
    public void testLockedValidity(){
        SudokuBoard brd = new SudokuBoard(this.specficBoard());
        boardManager = new SudokuBoardManager(brd, 3);
        SudokuMove move = new SudokuMove(1, 1, 4, 3);
        assertFalse(boardManager.isValidMove(move));
    }

    @Test
    public void testEditableValidity(){
        SudokuBoard brd = new SudokuBoard(this.specficBoard());
        boardManager = new SudokuBoardManager(brd, 3);
        SudokuMove move = new SudokuMove(5, 0, 0, 3);
        assertTrue(boardManager.isValidMove(move));

    }

    @Test
    public void testMoveWin(){
        SudokuBoard brd = new SudokuBoard(this.specficBoard());
        boardManager = new SudokuBoardManager(brd, 3);
        SudokuMove move = new SudokuMove(5, 0, 0, 9);
        boardManager.gameMove(move);
        System.out.println(Arrays.deepToString(board));
        assertFalse(boardManager.puzzleSolved());

    }

//    @Test
//    public void




}
