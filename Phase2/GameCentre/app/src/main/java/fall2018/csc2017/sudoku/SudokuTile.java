package fall2018.csc2017.sudoku;

import fall2018.csc2017.Tile;

public abstract class SudokuTile extends Tile {

    /**
     * Value of the Sudoku tile.
     */
    private int value;

    /**
     * Value for an empty Sudoku tile.
     */
    final static int EMPTY_TILE_VALUE = 0;

    /**
     * A Sudoku Tile with an id and a value.
     *
     * @param id    the id
     * @param value the number on the tile
     */
    public SudokuTile(int id, int value) {
        super(id);
        if (valueInvariant(value)) {
            this.value = value;
        }
    }

    /**
     * Get value at the Sudoku tile.
     *
     * @return value
     */
    int getValue() {
        return value;
    }

    /**
     * Get string value at the Sudoku tile.
     *
     * @return value
     */
    String getStringValue() {
        return String.valueOf(value);
    }

    /**
     * Set the value of the tile.
     *
     * @param value the value to be set to the tile
     */
    void setValue(int value) {
        if (valueInvariant(value)) {
            this.value = value;
        }
    }

    /**
     * Return whether the value is valid for the Sudoku game.
     *
     * @param value value
     * @return whether the value is valid
     */
    private boolean valueInvariant(int value) {
        return (value > 0 && value < 10) || value == EMPTY_TILE_VALUE;
    }
}
