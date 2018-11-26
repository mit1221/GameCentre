package fall2018.csc2017.sudoku;

import fall2018.csc2017.Tile;

abstract class SudokuTile extends Tile {

    /**
     * Display value of the Sudoku tile.
     */
    protected int value;

    /**
     * A Sudoku Tile with an id and a value.
     *
     * @param id    the id
     * @param value the number on the tile
     */
    SudokuTile(int id, int value) {
        super(id);
        this.value = value;
    }

    /**
     * Get value at the Sudoku tile.
     *
     * @return value
     */
    int getValue() {
        return value;
    }
}
