package fall2018.csc2017.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import fall2018.csc2017.Game;
import fall2018.csc2017.MenuActivity;
import fall2018.csc2017.User;
import fall2018.csc2017.UserManager;
import fall2018.csc2017.slidingtiles.R;

/**
 * Menu Activity for the Hangman game
 */
public class HangmanMenuActivity extends AppCompatActivity implements MenuActivity {

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
        startGame();
    }

    /**
     * Handle what happens when user clicks Load Game
     * @param view - clicked button
     */
    public void onBtnLoadGameClick(View view){
        loadSavedGame();
    }

    /**
     * Handle what happens when user clicks Highscores
     * @param view - clicked button
     */
    public void onBtnHighScoresClick(View view){
        Intent intent = new Intent(this, HangmanHSActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    /**
     * Start the hangman game with the given game
     * @param game game to use
     */
    private void gotoHangmanGameActivity(HangmanGame game){
        Intent intent = new Intent(this, HangmanGameActivity.class);
        intent.putExtra("HangmanGame", game);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    @Override
    public void startGame() {
        // Choose a random category and word
        Object[] categories = WordManager.getCategories().toArray();
        Random rand = new Random();
        String category = (String) categories[rand.nextInt(categories.length)];
        String answer = WordManager.getWord(category, this);
        HangmanGame game = new HangmanGame(answer, category);
        gotoHangmanGameActivity(game);
    }

    @Override
    public void loadSavedGame() {
        if(user.hasSave(Game.HANGMAN)){
            gotoHangmanGameActivity((HangmanGame)user.getSave(Game.HANGMAN));
        }
        else{
            Toast.makeText(this, "No game to load",Toast.LENGTH_SHORT ).show();
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
