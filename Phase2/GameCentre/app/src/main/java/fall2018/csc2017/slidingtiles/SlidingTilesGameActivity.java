package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.res.Resources;
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

    private SlidingTilesGameController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new SlidingTilesGameController();
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Resources resources = getResources();
        controller.handleExtras(extras, resources);
        model = controller.getModel();
        gridView = controller.getGridView();
        columnWidth = SlidingTilesGameController.getColumnWidth();
        columnHeight = SlidingTilesGameController.getColumnHeight();
        user = controller.getUser();
        tileImages = controller.getTileImages();

        Context context = getApplicationContext();

        controller.createTileButtons(context);
        tileButtons = controller.getTileButtons();
        setContentView(R.layout.activity_slidingtiles_game);

        // Add an undo button to the game
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setGravity(Gravity.BOTTOM);
        undoButton.setText("Undo");
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.undoClick(getApplicationContext());
            }
        });

        int boardSize = model.getBoard().getSize();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardSize);
        gridView.setModel(model);
        model.addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / model.getBoard().getSize();
                        // Leave some space for display at the top
                        columnHeight = ((int) (displayHeight * 0.7)) /
                                model.getBoard().getSize();

                        display();
                    }
                });
    }


    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SlidingTilesBoard board = (SlidingTilesBoard) model.getBoard();
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
        if (model.puzzleSolved()) {
            Score score = new Score(user.getUserName(), model.getMovesMade());
            GameScoreboard.addScore(this, Board.getHighScoreFile(
                    Game.SLIDING_TILES, model.getBoard().getSize()), score);
        }
    }

}
