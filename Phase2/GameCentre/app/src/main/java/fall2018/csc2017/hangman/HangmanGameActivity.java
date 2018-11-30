package fall2018.csc2017.hangman;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.Game;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * Activity for playing Hangman
 * No unit tests since this class mainly deals with displaying views in android
 */
public class HangmanGameActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    public static final String HANGMAN_HS_FILE = "Hangman.txt";

    /**
     * Grid for displaying the solved/unknown letters
     */
    private GridView gridLetters;

    /**
     * Adapter for gridLetters
     */
    private LettersAdapter lettersAdapter;

    /**
     * Grid for displaying the unused/correct/incorrect letter guesses
     */
    private GridView gridLetterButtons;

    /**
     * Current game of hangman
     */
    private HangmanGame game;

    /**
     * The current logged in user
     */
    private User user;

    /**
     * Images related to display for the number of hangman lives the user has.
     * EX hangmanImages[0] => image for 0 lives ....
     */
    //private int[] hangmanImages = new int[]{R.drawable.gand, R.drawable.flower, R.drawable.cat, R.drawable.yodawg};
    private int[] hangmanImages = new int[]{R.drawable.man6, R.drawable.man5, R.drawable.man4, R.drawable.man3,R.drawable.man2, R.drawable.man1, R.drawable.man0};

    /**
     * The imageview for the hanged man
     */
    private ImageView imgHangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        game = (HangmanGame) getIntent().getSerializableExtra("HangmanGame");
        game.addObserver(this);
        user = (User) getIntent().getSerializableExtra("User");

        // Setup grid views
        setupGrid();

        // display the category
        TextView tvCategory = findViewById(R.id.tvCategory);
        tvCategory.setText("Category: " + game.getCategory());

        // Set the image for the hangman lives
        imgHangman = findViewById(R.id.imgHangman);
        imgHangman.setImageResource(hangmanImages[game.getNumLives()]);
    }

    private void setupGrid() {
        gridLetters = findViewById(R.id.gridLetters);
        gridLetters.setNumColumns(game.getLongestWordLength());
        lettersAdapter = new LettersAdapter(game.getFixedGameState(game.getLongestWordLength()));
        gridLetters.setAdapter(lettersAdapter);

        gridLetterButtons = findViewById(R.id.gridLetterButtons);
        LetterButtonsAdapter letterButtonsAdapter = new LetterButtonsAdapter(this, game.getLetters());
        gridLetterButtons.setAdapter(letterButtonsAdapter);
    }


    /**
     * Handle the click event for the letter buttons
     *
     * @param v View that was clicked
     */
    @Override
    public void onClick(View v) {
        // Ignore letter buttons if game is already over
        if (game.isGameOver()) {
            return;
        }
        Button btn = (Button) v;
        // Get the letter that was clicked on
        Character letter = btn.getText().toString().charAt(0);

        // Make the user's guess
        if (!game.makeLetterGuess(letter)) { // Tell user letter has already been used
            Toast.makeText(this, "Letter has already been used", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Update the views in the game activity
     */
    private void updateViews() {
        // update the solved/unsolved letters
        lettersAdapter.setLetters(game.getFixedGameState(game.getLongestWordLength()));
        gridLetters.invalidateViews();

        // update the correct/incorrect guesses
        gridLetterButtons.invalidateViews();

        // update the image
        imgHangman.setImageResource(hangmanImages[game.getNumLives()]);
    }

    /**
     * Create a dialog when user clicks on Make a Guess to prompt user for an answer.
     */
    public void onBtnMakeGuessClick(View view) {
        if (game.isGameOver()) { // ignore button press if game is over
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // input in the dialog for specifying max # of undos allowed
        final EditText input = new EditText(this);
        input.setText("");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        // setup the alert builder
        builder.setView(input);
        builder.setTitle("Make a guess");

        // add Next and Cancel buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (!game.makeAnswerGuess(input.getText().toString())) {
                    Toast.makeText(HangmanGameActivity.this, "Wrong answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        updateViews();
        // Save the game
        user.setSave(Game.HANGMAN, game);
        UserManager.saveUserState(user, this);

        // Notify user if they won/lost on this move
        if (game.isGameOver()) {
            String result = game.processGameOver(this, user);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
