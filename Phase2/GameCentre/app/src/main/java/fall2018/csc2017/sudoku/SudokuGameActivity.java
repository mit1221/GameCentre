package fall2018.csc2017.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Board;
import fall2018.csc2017.Model;
import fall2018.csc2017.CustomAdapter;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Move;
import fall2018.csc2017.MovementController;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * The Sudoku game activity.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    public static final String SUDOKU_HS_FILE = "Sudoku.txt";

    /**
     * The board manager.
     */
    private SudokuModel model;

    /**
     * The buttons to display.
     */
    private ArrayList<EditText> tiles;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private SudokuGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Current user
     */
    private User user;

    private MovementController mController;

    /**
     * Call the adapter to set the view.
     */
    // Display
    public void display() {
        gridView.setAdapter(new CustomAdapter(tiles, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleExtras();

        createTiles(this);
        setContentView(R.layout.activity_sudoku_game);
        mController = new MovementController();
        mController.setModel(model);

        // Add an undo button to the game
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setGravity(Gravity.BOTTOM);
        undoButton.setText("Undo");
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!model.puzzleSolved()) {
                        model.undoLastMove();
                        updateTiles();
                    }
                } catch (NoSuchElementException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        int boardSize = model.getBoard().getSize();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardSize);
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
                        updateTiles();
                    }
                });
    }

    /**
     * Get extras from past activity and initialize the model correctly.
     */
    private void handleExtras() {
        // Retrieve the user, whether the previous game should be loaded, and game options
        Bundle extras = getIntent().getExtras();

        user = (User) extras.getSerializable("User");
        boolean shouldLoad = extras.getBoolean("LoadGame", true);

        if (shouldLoad) {
            model = (SudokuModel) user.getSave(Game.SUDOKU);
        } else {
            SudokuGameOptions gameOptions = (SudokuGameOptions)
                    extras.getSerializable("GameOptions");
            int maxUndoMoves = gameOptions.getUndoMoves();

            model = new SudokuModel(maxUndoMoves);
            user.setSave(Game.SUDOKU, model.getBoard());
        }
    }

    /**
     * Create the tiles to be displayed.
     *
     * @param context the context
     */
    private void createTiles(Context context) {
        SudokuBoard board = (SudokuBoard) model.getBoard();
        tiles = new ArrayList<>();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                EditText tmp = new EditText(context);
                SudokuTile tile = (SudokuTile) board.getTile(row, col);
                tmp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                tmp.setInputType(InputType.TYPE_CLASS_NUMBER);
                tmp.getText().clear();
                tmp.setGravity(Gravity.CENTER);
                if (tile instanceof SudokuLockedTile) {
                    tmp.setText(tile.getStringValue());
                    tmp.setEnabled(false);
                    tmp.setTextColor(Color.BLACK);
                    tmp.setBackgroundResource(R.drawable.border);
                } else {
                    // tile is a SudokuEditableTile
                    tmp.addTextChangedListener(tileInputTextWatcher);
                    tmp.setBackgroundResource(R.drawable.border_with_background);
                }
                this.tiles.add(tmp);
            }
        }
    }

    /**
     * Handling input entered in the Sudoku tiles.
     */
    @NonNull
    private TextWatcher tileInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                if (s.toString().equals("0")) {
                    s.clear();
                } else {
                    int position = gridView.getPositionClicked();
                    int newNumber = Integer.parseInt(s.toString());
                    Move move = SudokuMove.createMove(position, model.getBoard(), newNumber);
                    mController.processMove(move);
                }
            }
        }
    };

    /**
     * Update the text on the tiles once the internal board changes.
     */
    private void updateTiles() {
        SudokuBoard board = (SudokuBoard) model.getBoard();

        int boardSize = board.getSize();
        int nextPos = 0;

        for (EditText t : tiles) {
            int row = nextPos / boardSize;
            int col = nextPos % boardSize;
            SudokuTile tile = (SudokuTile) board.getTile(row, col);
            t.setText(tile.getStringValue());
            nextPos++;
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
        user.setSave(Game.SUDOKU, save);
        UserManager.saveUserState(user, this);

        // save score if game is finished
        if (model.puzzleSolved()) {
//            Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show();
//            Score score = new Score(user.getUserName(), model.getMovesMade());
//            GameScoreboard.addScore(this, Board.getHighScoreFile(
//                    Game.SUDOKU, model.getBoard().getSize()), score);
            //emulating what Jason did
            String result = model.processGameOver(this, user);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
