package fall2018.csc2017.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.R;

/**
 * Menu Activity for the Hangman game
 */
public class HangmanMenuActivity extends AppCompatActivity {

    /**
     * The current user
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_menu);

        // retrieve the logged in user
        user = (User)this.getIntent().getSerializableExtra("User");
    }

    /**
     * Handle what happens when user clicks New Game
     * @param view - clicked button
     */
    public void onBtnNewGameClick(View view){

    }

    /**
     * Handle what happens when user clicks New Game
     * @param view - clicked button
     */
    public void onBtnLoadGameClick(View view){

    }


}
