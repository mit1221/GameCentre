package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.BoardManager;
import fall2018.csc2017.CustomAdapter;
import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.slidingtiles.R;

/**
 * The Sudoku game activity.
 */
public class SudokuGameActivty extends AppCompatActivity implements Observer {

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
//        handleExtras();

        createTileButtons(this);
        setContentView(R.layout.activity_sudoku_game_real);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SudokuBoard board = (SudokuBoard) boardManager.getBoard();
        tileButtons = new ArrayList<>();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Button tmp = new Button(context);
                tmp.setText(((SudokuTile) board.getTile(row, col)).getValue());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the text on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SudokuBoard board = (SudokuBoard) boardManager.getBoard();
        int boardSize = board.getSize();
        int nextPos = 0;

        for (Button b : tileButtons) {
            int row = nextPos / boardSize;
            int col = nextPos % boardSize;
            b.setText(((SudokuTile) board.getTile(row, col)).getValue());
            nextPos++;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
