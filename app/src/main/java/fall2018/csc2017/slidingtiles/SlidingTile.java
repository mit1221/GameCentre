package fall2018.csc2017.slidingtiles;

import fall2018.csc2017.Tile;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class SlidingTile extends Tile {
    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * A tile with an id and a background determined by the id.
     *
     * @param id id of the tile
     */
    SlidingTile(int id) {
        super(id);
        int[] drawables = {R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3,
                R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7,
                R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11,
                R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15,
                R.drawable.tile_16, R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19,
                R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22, R.drawable.tile_23,
                R.drawable.tile_24, R.drawable.tile_0};
        background = drawables[getId()];
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the tile to a blank tile.
     */
    void setToBlankTile() {
        this.background = R.drawable.tile_0;
    }
}
