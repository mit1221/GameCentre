package fall2018.csc2017.sudoku;

public class SudokuLockedTile extends SudokuTile {

    /**
     * A Sudoku Tile with an id, which cannot be changed.
     *
     * @param id    the id
     * @param value the displayed number on the tile
     */
    public SudokuLockedTile(int id, int value) {
        super(id, value);
    }

    /**
     * Don't allow setValue method for a locked tile.
     */
    @Override
    void setValue(int value) {
        throw new UnsupportedOperationException("Cannot modify this tile.");
    }
}
