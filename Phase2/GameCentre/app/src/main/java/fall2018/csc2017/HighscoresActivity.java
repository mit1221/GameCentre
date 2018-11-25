package fall2018.csc2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.Board;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.R;

/**
 * Activity for displaying users' highscores.
 */
public abstract class HighscoresActivity extends AppCompatActivity{

    /**
     * Keep track of current user
     */
    protected User user;

    /**
     * Button for toggling between all and current user scores
     */
    protected ToggleButton btnUser;

    /**
     * Textview for display scores
     */
    protected TextView tvScores;

    /**
     * Textview for display users
     */
    protected TextView tvUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        // store references to ui variables
        btnUser = findViewById(R.id.tgBtnUsers);
        tvScores = findViewById(R.id.tvScores);
        tvUsers = findViewById(R.id.tvUsers);

        // Get the current user
        user = (User)getIntent().getSerializableExtra("User");
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateDisplayedScores();
    }

    /**
     * Toggle scores displayed based on users when button is clicked
     * @param view  View that was clicked on
     */
    public void onBtnUserClick(View view){
        updateDisplayedScores();
    }


    /**
     * Update the displayed scores from the user's input
     */
    protected void updateDisplayedScores(){
        String userDisplay = "User";
        String scoreDisplay = "Score";
        List<Score> scores = getScoresToDisplay();
        for(Score s: scores){
            userDisplay += "\n" + s.getUserName();
            scoreDisplay += "\n" + s.getValue();
        }
        tvUsers.setText(userDisplay);
        tvScores.setText(scoreDisplay);
    }

    /**
     * Returns the list of scores to display in the activity;
     */
    protected abstract List<Score> getScoresToDisplay();

}
