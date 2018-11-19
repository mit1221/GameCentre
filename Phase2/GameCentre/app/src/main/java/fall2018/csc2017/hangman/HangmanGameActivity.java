package fall2018.csc2017.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

/**
 * Activity for playing Hangman
 */
public class HangmanGameActivity extends AppCompatActivity implements View.OnClickListener {

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
    private HangmanState game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        game = (HangmanState)getIntent().getSerializableExtra("HangmanState");

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

    }


    /**
     * Handle the click event for the letter buttons
     * @param v  View that was clicked
     */
    @Override
    public void onClick(View v) {
        Button btn = (Button)v;

        // Get the letter that was clicked on
        Character letter = btn.getText().toString().charAt(0);

        // Let user make the guess if letter was never used
        if(game.getLetterState(letter) == HangmanState.LETTER_STATE.UNUSED){
            game.makeGuess(letter);

            // update the solved/unsolved letters
            lettersAdapter.setLetters(game.getGameState());
            gridLetters.invalidateViews();

            // update the correct/incorrect guesses
            gridLetterButtons.invalidateViews();

            // Notify user if they won on this move
            if(game.isSolved()){
                Toast.makeText(this, "YOU WIN !!!", Toast.LENGTH_SHORT).show();
            }
        }
        else{ // Tell user letter has already been used
            Toast.makeText(this, "Letter has already been used", Toast.LENGTH_SHORT).show();
        }
    }
}
