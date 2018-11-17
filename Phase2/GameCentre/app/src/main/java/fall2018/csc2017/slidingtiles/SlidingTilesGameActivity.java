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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.CustomAdapter;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

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
        super.onCreate(savedInstanceState);
        handleExtras();

        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add an undo button to the game
        Button undoButton = (Button) findViewById(R.id.undoButton);
        undoButton.setGravity(Gravity.BOTTOM);
        undoButton.setText("Undo");
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!boardManager.puzzleSolved()) {
                        boardManager.getBoard().undoLastMove();
                    }
                } catch (NoSuchElementException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        // Leave some space for display at the top
                        columnHeight = ((int) (displayHeight * 0.7)) / Board.NUM_ROWS;

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
            Board savedBoard = (Board) user.getSave("slidingtiles");
            Board.NUM_ROWS = savedBoard.getBoardHeight();
            Board.NUM_COLS = savedBoard.getBoardWidth();
            boardManager = new BoardManager(savedBoard);
            populateTileImages();
        } else {
            SlidingTilesGameOptions gameOptions = (SlidingTilesGameOptions)
                    extras.getSerializable("GameOptions");
            int maxUndoMoves = gameOptions.getUndoMoves();
            byte[] byteArray = gameOptions.getImage();

            if (byteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                // Cropping the image to make it a square
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap squaredImage = Bitmap.createBitmap(bmp, 0, 0, Math.min(width, height), Math.min(width, height));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                squaredImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                boardManager = new BoardManager(maxUndoMoves, stream.toByteArray());
                populateTileImages();
            } else {
                boardManager = new BoardManager(maxUndoMoves);
            }

            user.setSave("slidingtiles", boardManager.getBoard());
        }
    }

    /**
     * Adds the generated tiles to tileImages.
     */
    private void populateTileImages() {
        byte[] image = boardManager.getBoard().getImage();
        if (image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            ArrayList<Bitmap> bitmapImages = generateTiles(bmp);

            tileImages = new ArrayList<>(Board.numTiles());
            // convert Bitmap tiles to Drawable so they can be used as backgrounds for the buttons
            for (Bitmap tile : bitmapImages) {
                Drawable drawable = new BitmapDrawable(getResources(), tile);
                tileImages.add(drawable);
            }
        }
    }

    /**
     * Creates tiles from a square image based on size of board.
     *
     * Adapted from: http://androidattop.blogspot.com/2012/05/splitting-image-into-smaller-chunks-in.html
     *
     * @param image the image to split
     * @return returns an ArrayList of the tiles as Bitmaps
     */
    private ArrayList<Bitmap> generateTiles(Bitmap image) {
        ArrayList<Bitmap> tiles = new ArrayList<>(Board.numTiles());

        int rows = Board.NUM_ROWS;
        int cols = Board.NUM_COLS;

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
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();

        if (tileImages != null) {
            for (int row = 0; row < Board.NUM_ROWS; row++) {
                for (int col = 0; col < Board.NUM_COLS; col++) {
                    Button tmp = new Button(context);
                    tmp.setBackground(tileImages.get(board.getTile(row, col).getId() - 1));
                    this.tileButtons.add(tmp);
                }
            }
        } else {
            for (int row = 0; row < Board.NUM_ROWS; row++) {
                for (int col = 0; col < Board.NUM_COLS; col++) {
                    Button tmp = new Button(context);
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                    this.tileButtons.add(tmp);
                }
            }
        }

    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;

        if (tileImages != null) {
            for (Button b : tileButtons) {
                int row = nextPos / Board.NUM_ROWS;
                int col = nextPos % Board.NUM_COLS;
                b.setBackground(tileImages.get(board.getTile(row, col).getId() - 1));
                nextPos++;
            }
        } else {
            for (Button b : tileButtons) {
                int row = nextPos / Board.NUM_ROWS;
                int col = nextPos % Board.NUM_COLS;
                b.setBackgroundResource(board.getTile(row, col).getBackground());
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
        //saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        if (user != null) {
            UserManager.saveUserState(user, this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        Board board = (Board) o;
        // save the state of the board when it changes
        if (user != null) {
            user.setSave("slidingtiles", board);
            UserManager.saveUserState(user, this);
        }

        // save score if game is finished
        if (boardManager.puzzleSolved()) {
            Score score = new Score(user.getUserName(), board.getMovesMade());
            GameScoreboard.addScore(this, Board.getHighScoreFile(board.getBoardWidth()), score);
        }
    }

}
