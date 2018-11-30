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
     * The model that contains data.
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
     * Controller
     */

    private SlidingTilesGameController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new SlidingTilesGameController();
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Resources resources = getResources();

        // Get extras from past activity and initialize the model correctly.
        controller.handleExtras(extras, resources);
        // Obtain info from controller
        model = controller.getModel();
        gridView = controller.getGridView();
        columnWidth = SlidingTilesGameController.getColumnWidth();
        columnHeight = SlidingTilesGameController.getColumnHeight();
        user = controller.getUser();

        // Create tile buttons
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

                        // Set up the background image for each button based on the master list
                        // of positions, and then call the adapter to set the view.
                        controller.updateTileButtons();
                        tileButtons = controller.getTileButtons();
                        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
                    }
                });
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
        controller.updateTileButtons();
        tileButtons = controller.getTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        Model save = (Model) o;
        // save the state of the board when it changes
        user.setSave(Game.SLIDING_TILES, save);
        UserManager.saveUserState(user, this);
        // save score if game is finished
        Context context = getApplicationContext();
        controller.addScore(context);
        }
}
