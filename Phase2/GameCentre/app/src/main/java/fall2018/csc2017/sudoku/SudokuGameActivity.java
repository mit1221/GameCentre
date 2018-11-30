package fall2018.csc2017.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Board;
import fall2018.csc2017.BoardManager;
import fall2018.csc2017.CustomAdapter;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;

/**
 * The Sudoku game activity.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    public static final String SUDOKU_HS_FILE = "Sudoku.txt";

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<EditText> tiles;

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
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTiles();
        gridView.setAdapter(new CustomAdapter(tiles, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleExtras();

        createTiles(this);
        setContentView(R.layout.activity_sudoku_game_real);

        // Add an undo button to the game
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setGravity(Gravity.BOTTOM);
        undoButton.setText("Undo");
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!boardManager.puzzleSolved()) {
                        boardManager.undoLastMove();
                    }
                } catch (NoSuchElementException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        int boardSize = boardManager.getBoard().getSize();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardSize);
        gridView.setBoardManager(boardManager);
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
            boardManager = (SudokuBoardManager) user.getSave(Game.SUDOKU);
        } else {
            SudokuGameOptions gameOptions = (SudokuGameOptions)
                    extras.getSerializable("GameOptions");
            int maxUndoMoves = gameOptions.getUndoMoves();

            boardManager = new SudokuBoardManager(maxUndoMoves);
            user.setSave(Game.SUDOKU, boardManager.getBoard());
        }
    }

    /**
     * Create the tiles to be displayed.
     *
     * @param context the context
     */
    private void createTiles(Context context) {
        SudokuBoard board = (SudokuBoard) boardManager.getBoard();
        tiles = new ArrayList<>();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                EditText tmp = new EditText(context);
                SudokuTile tile = (SudokuTile) board.getTile(row, col);
                tmp.getText().clear();
                tmp.setGravity(Gravity.CENTER);
                if (tile instanceof SudokuLockedTile) {
                    tmp.setText(tile.getStringValue());
                    tmp.setEnabled(false);
                    tmp.setTextColor(Color.BLACK);
                    tmp.setBackground(null);
                }
                this.tiles.add(tmp);
            }
        }
    }

    /**
     * Update the text on the tiles once the internal board changes.
     */
    private void updateTiles() {
        SudokuBoard board = (SudokuBoard) boardManager.getBoard();
        int boardSize = board.getSize();
        int nextPos = 0;

        for (EditText t : tiles) {
            int row = nextPos / boardSize;
            int col = nextPos % boardSize;
            SudokuTile tile = (SudokuTile) board.getTile(row, col);
            if (tile instanceof SudokuLockedTile) {
                t.setText(tile.getStringValue());
            }
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
        BoardManager save = (BoardManager) o;
        // save the state of the board when it changes
        user.setSave(Game.SUDOKU, save);
        UserManager.saveUserState(user, this);

        // save score if game is finished
        if (boardManager.puzzleSolved()) {
            Score score = new Score(user.getUserName(), boardManager.getMovesMade());
            GameScoreboard.addScore(this, Board.getHighScoreFile(
                    Game.SUDOKU, boardManager.getBoard().getSize()), score);
        }
    }
}
