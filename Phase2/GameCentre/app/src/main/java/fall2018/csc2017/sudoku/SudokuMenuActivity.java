package fall2018.csc2017.sudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fall2018.csc2017.Game;
import fall2018.csc2017.MenuActivity;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * Menu Activity for Sudoku.
 */

public class SudokuMenuActivity extends AppCompatActivity implements MenuActivity {

    /**
     * The current user
     */
    private User user;

    /**
     * Customizable options for the game
     */
    private SudokuGameOptions gameOptions = new SudokuGameOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_menu);

        // retrieve the logged in user
        user = (User) getIntent().getSerializableExtra("User");
    }

    /**
     * Handle what happens when user clicks New Game
     * @param view - View that was clicked
     */
    public void onBtnStartClick(View view) {
        openDialog();
    }

    /**
     * Open the dialog to ask user for max undos allowed.
     */
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // input in the dialog for specifying max # of undos allowed
        final EditText input = new EditText(this);
        input.setText("3");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        // setup the alert builder
        builder.setView(input);
        builder.setTitle("Choose the maximum number of moves to undo");

        // adding a checkbox for allowing unlimited undos
        final String[] items = {"Unlimited"};
        final boolean[] unlimitedUndoMoves = {false};

        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        unlimitedUndoMoves[0] = isChecked;

                        // disabling text field if checkbox is checked
                        input.setEnabled(!isChecked);
                        input.setFocusable(!isChecked);
                        input.setFocusableInTouchMode(!isChecked);
                    }
                });

        // add Next and Cancel buttons
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int undoMoves = Integer.parseInt(String.valueOf(input.getText()));

                if (undoMoves == 0) {
                    Toast.makeText(getApplicationContext(), "Specify a number greater than 0",
                            Toast.LENGTH_SHORT).show();
                } else {
                    undoMoves = unlimitedUndoMoves[0] ? -1 : undoMoves;
                    gameOptions.setUndoMoves(undoMoves);
                    startGame();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * The Load button event handler
     * @param view - View that was clicked
     */
    public void onBtnLoadClick(View view) {
        loadSavedGame();
    }

    /**
     * Starts Sudoku.
     */
    @Override
    public void startGame() {
        Intent intent = new Intent(this, SudokuGameActivity.class);
        intent.putExtra("User", user);
        intent.putExtra("LoadGame", false);
        intent.putExtra("GameOptions", gameOptions);
        startActivity(intent);
    }

    /**
     * Load the user's saved game if there is one.
     */
    @Override
    public void loadSavedGame() {
        if (user != null && user.hasSave(Game.SUDOKU)) {
            Intent intent = new Intent(this, SudokuGameActivity.class);
            intent.putExtra("User", user);
            intent.putExtra("LoadGame", true);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No game to load", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When activity resumes, reload the user's data in case it has changed
     */
    @Override
    protected void onResume() {
        super.onResume();
        user = UserManager.getUser(user.getUserName(), this); //reload the user data
    }
}
