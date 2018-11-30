package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import fall2018.csc2017.Game;
import fall2018.csc2017.Tile;
import fall2018.csc2017.User;

public class SlidingTilesGameController {

    /**
     * The board manager.
     */
    private SlidingTilesModel model;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Current user
     */
    private User user;

    /**
     * array of the images for the tiles
     */
    private ArrayList<Drawable> tileImages;

    public SlidingTilesModel getModel() {
        return model;
    }

    public void setModel(SlidingTilesModel model) {
        this.model = model;
    }

    public ArrayList<Button> getTileButtons() {
        return tileButtons;
    }

    public void setTileButtons(ArrayList<Button> tileButtons) {
        this.tileButtons = tileButtons;
    }

    public GestureDetectGridView getGridView() {
        return gridView;
    }

    public void setGridView(GestureDetectGridView gridView) {
        this.gridView = gridView;
    }

    public static int getColumnWidth() {
        return columnWidth;
    }

    public static void setColumnWidth(int columnWidth) {
        SlidingTilesGameController.columnWidth = columnWidth;
    }

    public static int getColumnHeight() {
        return columnHeight;
    }

    public static void setColumnHeight(int columnHeight) {
        SlidingTilesGameController.columnHeight = columnHeight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Drawable> getTileImages() {
        return tileImages;
    }

    public void setTileImages(ArrayList<Drawable> tileImages) {
        this.tileImages = tileImages;
    }

    public SlidingTilesGameController() {
    }

    /**
     * Get extras from past activity and initialize the model correctly.
     */
    void handleExtras(Bundle extras, Resources resources) {

        user = (User) extras.getSerializable("User");
        boolean shouldLoad = extras.getBoolean("LoadGame", true);

        if (shouldLoad) {
            model = (SlidingTilesModel) user.getSave(Game.SLIDING_TILES);
            populateTileImages(resources);
        } else {
            SlidingTilesGameOptions gameOptions = (SlidingTilesGameOptions)
                    extras.getSerializable("GameOptions");
            int maxUndoMoves = gameOptions.getUndoMoves();
            int boardSize = gameOptions.getSize();
            byte[] byteArray = gameOptions.getImage();

            if (byteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                // Cropping the image to make it a square
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap squaredImage = Bitmap.createBitmap(bmp, 0, 0, Math.min(width, height), Math.min(width, height));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                squaredImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                model = new SlidingTilesModel(boardSize, maxUndoMoves, stream.toByteArray());
                populateTileImages(resources);
            } else {
                model = new SlidingTilesModel(boardSize, maxUndoMoves, null);
            }

            user.setSave(Game.SLIDING_TILES, model.getBoard());
        }
    }

    /**
     * Adds the generated tiles to tileImages.
     */
    private void populateTileImages(Resources resources) {
        SlidingTilesBoard board = (SlidingTilesBoard) model.getBoard();
        byte[] image = board.getImage();
        if (image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            ArrayList<Bitmap> bitmapImages = generateTiles(bmp, resources);

            tileImages = new ArrayList<>(model.getBoard().numTiles());
            // convert Bitmap tiles to Drawable so they can be used as backgrounds for the buttons
            for (Bitmap tile : bitmapImages) {
                Drawable drawable = new BitmapDrawable(resources, tile);
                tileImages.add(drawable);
            }
        }
    }

    /**
     * Creates tiles from a square image based on size of board.
     * <p>
     * Adapted from: http://androidattop.blogspot.com/2012/05/splitting-image-into-smaller-chunks-in.html
     *
     * @param image the image to split
     * @return returns an ArrayList of the tiles as Bitmaps
     */
    private ArrayList<Bitmap> generateTiles(Bitmap image, Resources resources) {
        ArrayList<Bitmap> tiles = new ArrayList<>(model.getBoard().numTiles());

        int rows, cols;
        rows = cols = model.getBoard().getSize();

        // width and height of each tile
        int chunkWidth, chunkHeight;
        chunkWidth = image.getWidth() / rows;
        chunkHeight = image.getHeight() / cols;

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                tiles.add(Bitmap.createBitmap(image, x * chunkWidth,
                        y * chunkHeight, chunkWidth, chunkHeight));
            }
        }

        // change the last tile to be an empty tile
        tiles.set(tiles.size() - 1, BitmapFactory.decodeResource(resources, R.drawable.tile_0));
        return tiles;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    void createTileButtons(Context context) {
        SlidingTilesBoard board = (SlidingTilesBoard) model.getBoard();
        tileButtons = new ArrayList<>();

        if (tileImages != null) {
            for (Tile tile : board) {
                Button tmp = new Button(context);
                tmp.setBackground(tileImages.get(tile.getId()));
                this.tileButtons.add(tmp);
            }
        } else {
            for (Tile tile : board) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(((SlidingTile) tile).getBackground());
                this.tileButtons.add(tmp);
            }
        }

    }
}

