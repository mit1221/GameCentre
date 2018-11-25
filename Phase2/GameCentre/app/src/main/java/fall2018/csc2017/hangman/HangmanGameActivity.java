package fall2018.csc2017.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.Board;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * Activity for playing Hangman
 */
public class HangmanGameActivity extends AppCompatActivity implements View.OnClickListener {

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
     * Adapter for gridLetterButtons
     */
    private LetterButtonsAdapter letterButtonsAdapter;


    /**
     *  Current game of hangman
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
    private int[] hangmanImages = new int[]{R.drawable.gand, R.drawable.flower, R.drawable.cat, R.drawable.yodawg};

    /**
     * The imageview for the hanged man
     */
    private ImageView imgHangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        game = (HangmanGame)getIntent().getSerializableExtra("HangmanGame");
        user = (User)getIntent().getSerializableExtra("User");

        //test
        //game = (HangmanGame)user.getSave(Game.HANGMAN);

        // Setup grid views
        gridLetters = findViewById(R.id.gridLetters);
        gridLetters.setNumColumns(Math.min(game.getAnswer().length(), 12));
        lettersAdapter = new LettersAdapter(game.getGameState());
        gridLetters.setAdapter(lettersAdapter);

        gridLetterButtons = findViewById(R.id.gridLetterButtons);
        letterButtonsAdapter = new LetterButtonsAdapter(this, game.getLetters());
        gridLetterButtons.setAdapter(letterButtonsAdapter);

        // display the category
        TextView tvCategory = findViewById(R.id.tvCategory);
        tvCategory.setText(game.getCategory());

        // Set the image for the hangman lives
        imgHangman = findViewById(R.id.imgHangman);
        imgHangman.setImageResource(hangmanImages[game.getNumLives()]);
    }


    /**
     * Handle the click event for the letter buttons
     * @param v  View that was clicked
     */
    @Override
    public void onClick(View v) {
        // Ignore letter buttons if game is already over
        if(game.isGameOver()){
            return;
        }
        Button btn = (Button)v;

        // Get the letter that was clicked on
        Character letter = btn.getText().toString().charAt(0);

        // Let user make the guess if letter was never used
        if(game.makeLetterGuess(letter)){
            // Notify user if they won/lost on this move
            if(game.isGameOver()){
                if(game.didUserWin()) {
                    Toast.makeText(this, "YOU WIN !!!", Toast.LENGTH_SHORT).show();
                    Score score = new Score(user.getUserName(), game.getScore());
                    GameScoreboard.addScore(this, HANGMAN_HS_FILE, score);
                }
                else{
                    Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
                }
            }

            // update the solved/unsolved letters
            lettersAdapter.setLetters(game.getGameState());
            gridLetters.invalidateViews();

            // update the correct/incorrect guesses
            gridLetterButtons.invalidateViews();

            // update the image
            imgHangman.setImageResource(hangmanImages[game.getNumLives()]);

            // Save the game
            user.setSave(Game.HANGMAN, game);
            UserManager.saveUserState(user, this);
        }
        else{ // Tell user letter has already been used
            Toast.makeText(this, "Letter has already been used", Toast.LENGTH_SHORT).show();
        }
    }
}
