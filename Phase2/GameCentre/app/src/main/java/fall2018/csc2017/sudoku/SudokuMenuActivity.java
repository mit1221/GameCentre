package fall2018.csc2017.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fall2018.csc2017.MenuActivity;
import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.R;

/**
 * Menu Activity for Sudoku.
 */

public class SudokuMenuActivity extends AppCompatActivity implements MenuActivity {

    /**
     * The current user
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_menu);

        // retrieve the logged in user
        user = (User)this.getIntent().getSerializableExtra("User");
    }

    /**
     * Starts Sudoku.
     * @param game game to use
     */
    private void launchSudoku(SudokuBoard game){
        Intent intent = new Intent(this, SudokuGameActivity.class);
        startActivity(intent);
    }

    /**
     * Handle what happens when user clicks New Game
     * @param view - clicked button
     */
    public void onBtnNewGameClick(View view){
//        SudokuBoard game = new SudokuBoard();
//        launchSudoku(game);
    }

    /**
     * Handle what happens when user clicks Load Game
     * @param view - clicked button
     */
    public void onBtnLoadGameClick(View view){

    }


    @Override
    public void startGame() {

    }

    @Override
    public void loadSavedGame() {

    }
}
