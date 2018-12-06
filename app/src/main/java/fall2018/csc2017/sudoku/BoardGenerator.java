package fall2018.csc2017.sudoku;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A helper class to generate a solvable Sudoku board.
 * <p>
 * Adapted from: https://www.geeksforgeeks.org/program-sudoku-generator/
 */
public class BoardGenerator implements Iterable<Integer> {

    /**
     * An array representing the the Sudoku Board.
     */
    private int[] mat[];

    /**
     * The dimensions of the board.
     */
    private int rows;

    /**
     * Dimensions of each sub-grid in the board, or the square-root of rows.
     */
    private int root;

    /**
     * The number of blanks to be initialised upon creation of the board.
     */
    private int blanks;

    /**
     * Initialises an empty sudoku board.
     * @param rows dimensions of the board.
     * @param blanks the number of blanks in the board.
     */
    BoardGenerator(int rows, int blanks) {
        this.rows = rows;
        this.blanks = blanks;

        // Compute square root of rows
        Double SRNd = Math.sqrt(rows);
        root = SRNd.intValue();

        mat = new int[rows][rows];
    }

    /**
     * Populates the board with the appropriate number of values.
     */
    void fillValues() {
        // Fill the diagonal of root x root matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, root);

        // Remove Randomly blanks digits to make game
        removeKDigits();
    }

    /**
     * Diagonally populates the grids of the board.
     */
    private void fillDiagonal() {

        for (int i = 0; i < rows; i = i + root)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    /**
     * Checks for a duplicate value in a given root x root sub-grid.
     * @param rowStart the first row-coordinate of the mentioned grid.
     * @param colStart the first col-coordinate of the mentioned grid.
     * @param num the number to be checked for.
     * @return the boolean representing whether num is already in the given grid.
     */
    private boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < root; i++)
            for (int j = 0; j < root; j++)
                if (mat[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    /**
     * Fills a given root x root sub-grid with values.
     * @param row the first row-coordinate of the mentioned grid.
     * @param col colStart the first col-coordinate of the mentioned grid.
     */
    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < root; i++) {
            for (int j = 0; j < root; j++) {
                do {
                    num = randomGenerator(this.rows);
                }
                while (!unUsedInBox(row, col, num));

                mat[row + i][col + j] = num;
            }
        }
    }

    /**
     * Returns a random integer value from 1-9.
     * @param num A value restricting the range of possible return values.
     * @return a random integer.
     */
    private int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    /**
     * Determines whether it is safe to place a num into given cell.
     * @param i x-coordinate of the cell
     * @param j y-coordinate of the cell
     * @param num input value
     * @return bool determining safety of input value.
     */
    private boolean CheckIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % root, j - j % root, num));
    }

    /**
     * Determines whether it is safe to place a value in a given row.
     * @param i coordinate denoting the entire row in the board.
     * @param num input value
     * @return bool determining safety of input value.
     */
    private boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < rows; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Determines whether it is safe to place a value in a given column.
     * @param j coordinate denoting the entire column in the board.
     * @param num input value.
     * @return bool determining safety of input value.
     */
    private boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < rows; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Recursively fills the remaining matrices.
     * @param i x-coordinate of grid.
     * @param j y-coordinate of grid.
     * @return boolean determining if board has been populated.
     */
    private boolean fillRemaining(int i, int j) {
        // System.out.println(i+" "+j);
        if (j >= rows && i < rows - 1) {
            i = i + 1;
            j = 0;
        }
        //finished filling matrices.
        if (i >= rows && j >= rows)
            return true;

        if (i < root) {
            if (j < root)
                j = root;
        } else if (i < rows - root) {
            if (j == (int) (i / root) * root)
                j = j + root;
        } else {
            if (j == rows - root) {
                i = i + 1;
                j = 0;
                if (i >= rows)
                    return true;
            }
        }

        for (int num = 1; num <= rows; num++) {
            if (CheckIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    /**
     * Replaces a specified number of digits in the board with 0's.
     */
    private void removeKDigits() {
        int count = blanks;
        while (count != 0) {
            int cellId = randomGenerator(rows * rows) - 1;

            // System.out.println(cellId);
            // extract coordinates i and j
            int i = (cellId / rows);
            int j = cellId % 9;
//            if (j != 0)
//                j = j - 1;

            // System.out.println(i+" "+j);
            if (mat[i][j] != 0) {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    @NonNull
    @Override
    public Iterator<Integer> iterator() {
        return new SudokuIterator();
    }

    /**
     * Iterate over the tiles of the board.
     */
    private class SudokuIterator implements Iterator<Integer> {
        /**
         * The location of the next number.
         */
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex < Math.pow(rows, 2);
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                Integer nextNumber = mat[nextIndex / rows][nextIndex % rows];
                nextIndex++;
                return nextNumber;
            }
            throw new NoSuchElementException("Out of range.");
        }
    }
}
