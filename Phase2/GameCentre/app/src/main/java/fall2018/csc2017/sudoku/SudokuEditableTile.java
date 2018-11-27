package fall2018.csc2017.sudoku;

class SudokuEditableTile extends SudokuTile {

    /**
     * An empty Sudoku editable Tile with an id.
     *
     * @param id the id
     */
    SudokuEditableTile(int id) {
        super(id, SudokuTile.EMPTY_TILE_VALUE);
    }
}
