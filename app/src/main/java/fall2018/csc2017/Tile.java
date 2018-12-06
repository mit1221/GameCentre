package fall2018.csc2017;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A general Tile for a board.
 */
public abstract class Tile implements Serializable, Comparable<Tile> {

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with an id.
     *
     * @param id the id
     */
    public Tile(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.getId() - this.getId();
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
