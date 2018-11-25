package fall2018.csc2017.sudoku;

import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * The sliding tiles board.
 */
public class SudokuBoard extends Board {

    /**
     * Returns the string filename to store highscores for certain boards
     * @return file name
     */
    static String getHighScoreFile(){
        return "sudoku.txt";
    }

    SudokuBoard(int size, List<Tile> tiles) {
        super(size, tiles);
    }

    SudokuBoard(int size, List<Tile> tiles, int maxUndoMoves) {
        super(size, tiles, maxUndoMoves);
    }

    @Override
    public void gameMove(Move m) {
        swap((SudokuMove) m);
    }

    /** Switch two tiles in the board
     * @param move move to make
     */
    private void swap(SudokuMove move){
        int row1 = move.getRow1();
        int row2 = move.getRow2();
        int col1 = move.getCol1();
        int col2 = move.getCol2();

        Tile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }
}