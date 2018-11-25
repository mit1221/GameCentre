package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.R;

/**
 * The Sudoku game activity.
 */
public class SudokuGameActivty extends AppCompatActivity implements Observer {

    /**
     * The buttons to display.
     */
    private ArrayList<EditText> tiles;

    /**
     * Current user
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSudokuTiles(this);
        setContentView(R.layout.activity_sudoku_game_activty);
    }

    /**
     * Create the Edittext fields for displaying the Sudoku tiles.
     *
     * @param context the context
     */
    private void createSudokuTiles(Context context) {
        tiles = new ArrayList<>();

        BoardGenerator sudoku = new BoardGenerator(9, 14);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                EditText tmp = new EditText(context);
                tmp.setText(sudoku.mat[row][col]);
                this.tiles.add(tmp);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
