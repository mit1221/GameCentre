package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Board;
import fall2018.csc2017.Model;
import fall2018.csc2017.CustomAdapter;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.Tile;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;

/**
 * The Sliding Tiles game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesModel boardManager;

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

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        gameController = new SlidingTilesGameController(boardManager.getBoard());
        super.onCreate(savedInstanceState);
        handleExtras();

        createTileButtons(this);
        setContentView(R.layout.activity_slidingtiles_game);

        // Add an undo button to the game
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setGravity(Gravity.BOTTOM);
        undoButton.setText("Undo");
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.undoClick(getApplicationContext());
            }
        });

        int boardSize = boardManager.getBoard().getSize();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardSize);
        gridView.setModel(boardManager);
        boardManager.addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getSize();
                        // Leave some space for display at the top
                        columnHeight = ((int) (displayHeight * 0.7)) /
                                boardManager.getBoard().getSize();

                        display();
                    }
                });
    }



    /**
     * Get extras from past activity and initialize the boardManager correctly.
     */
    private void handleExtras() {
        // Retrieve the user, whether the previous game should be loaded, and game options
        Bundle extras = getIntent().getExtras();

        user = (User) extras.getSerializable("User");
        boolean shouldLoad = extras.getBoolean("LoadGame", true);

        if (shouldLoad) {
            boardManager = (SlidingTilesModel) user.getSave(Game.SLIDING_TILES);
            populateTileImages();
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

                boardManager = new SlidingTilesModel(boardSize, maxUndoMoves, stream.toByteArray());
                populateTileImages();
            } else {
                boardManager = new SlidingTilesModel(boardSize, maxUndoMoves, null);
            }

            user.setSave(Game.SLIDING_TILES, boardManager.getBoard());
        }
    }

    /**
     * Adds the generated tiles to tileImages.
     */
    private void populateTileImages() {
        SlidingTilesBoard board = (SlidingTilesBoard) boardManager.getBoard();
        byte[] image = board.getImage();
        if (image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            ArrayList<Bitmap> bitmapImages = generateTiles(bmp);

            tileImages = new ArrayList<>(boardManager.getBoard().numTiles());
            // convert Bitmap tiles to Drawable so they can be used as backgrounds for the buttons
            for (Bitmap tile : bitmapImages) {
                Drawable drawable = new BitmapDrawable(getResources(), tile);
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
    private ArrayList<Bitmap> generateTiles(Bitmap image) {
        ArrayList<Bitmap> tiles = new ArrayList<>(boardManager.getBoard().numTiles());

        int rows, cols;
        rows = cols = boardManager.getBoard().getSize();

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
        tiles.set(tiles.size() - 1, BitmapFactory.decodeResource(getResources(), R.drawable.tile_0));
        return tiles;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTilesBoard board = (SlidingTilesBoard) boardManager.getBoard();
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

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SlidingTilesBoard board = (SlidingTilesBoard) boardManager.getBoard();
        int boardSize = board.getSize();
        int nextPos = 0;

        if (tileImages != null) {
            for (Button b : tileButtons) {
                int row = nextPos / boardSize;
                int col = nextPos % boardSize;
                b.setBackground(tileImages.get(board.getTile(row, col).getId()));
                nextPos++;
            }
        } else {
            for (Button b : tileButtons) {
                int row = nextPos / boardSize;
                int col = nextPos % boardSize;
                b.setBackgroundResource(((SlidingTile) board.getTile(row, col)).getBackground());
                nextPos++;
            }
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        UserManager.saveUserState(user, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        Model save = (Model) o;
        // save the state of the board when it changes
        user.setSave(Game.SLIDING_TILES, save);
        UserManager.saveUserState(user, this);

        // save score if game is finished
        if (boardManager.puzzleSolved()) {
            Score score = new Score(user.getUserName(), boardManager.getMovesMade());
            GameScoreboard.addScore(this, Board.getHighScoreFile(
                    Game.SLIDING_TILES, boardManager.getBoard().getSize()), score);
        }
    }

}
