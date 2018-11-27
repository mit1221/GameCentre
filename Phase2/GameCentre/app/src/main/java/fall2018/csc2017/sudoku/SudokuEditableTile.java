package fall2018.csc2017.sudoku;

public class SudokuEditableTile extends SudokuTile {

    /**
     * A Sudoku editable Tile with an id.
     *
     * @param id the id
     */
    public SudokuEditableTile(int id) {
        super(id, -1);
    }

    /**
     * Set the value of the tile.
     *
     * @param value the value to be set to the tile
     */
    public void setValue(int value) {
        this.value = value;
    }
}
