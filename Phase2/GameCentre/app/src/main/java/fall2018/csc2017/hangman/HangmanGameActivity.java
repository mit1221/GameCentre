package fall2018.csc2017.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        // Setup grid views
        gridLetters = findViewById(R.id.gridLetters);
        lettersAdapter = new LettersAdapter("adsfadf");
        gridLetters.setAdapter(lettersAdapter);

        GridView gridLetterButtons = findViewById(R.id.gridLetterButtons);
        gridLetterButtons.setAdapter(new LetterButtonsAdapter(this));
    }


    /**
     * Handle the click event for the letter buttons
     * @param v  View that was clicked
     */
    @Override
    public void onClick(View v) {
        Log.wtf("asdf", "Hangman onItemClick occurred");
        String letter = ((Button)(v)).getText().toString();
        Toast.makeText(this, letter, Toast.LENGTH_SHORT).show();
        lettersAdapter.setLetters("abc" + letter);
        gridLetters.invalidateViews();
    }
}
