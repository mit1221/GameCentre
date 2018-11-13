package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;

/**
 * Activity for displaying user's highscore
 */
public class HighscoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * Keep track of current user
     */
    private User user;

    /**
     * Button for toggling between all and current user scores
     */
    private ToggleButton btnUser;

    /**
     * Spinner for choosing which size games to show
     */
    private Spinner spinGameOptions;

    /**
     * Textview for display scores
     */
    private TextView tvScores;

    /**
     * Textview for display users
     */
    private TextView tvUsers;

    /**
     * Maps the string displayed in the spinner to the size of the game
     */
    private final Map<String, Integer> gameOptions = new HashMap<String,Integer>(){{
        put("3x3", 3);
        put("4x4", 4);
        put("5x5", 5);
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        // store references to ui variables
        btnUser = findViewById(R.id.tgBtnUsers);
        tvScores = findViewById(R.id.tvScores);
        tvUsers = findViewById(R.id.tvUsers);

        //Setup the spinner options https://developer.android.com/guide/topics/ui/controls/spinner
        spinGameOptions = findViewById(R.id.spinGameOptions);
        String[]options = new String[gameOptions.size()];
        gameOptions.keySet().toArray(options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGameOptions.setAdapter(adapter);
        spinGameOptions.setOnItemSelectedListener(this);

        // Get the current user
        user = (User)getIntent().getSerializableExtra("User");

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
    private void updateDisplayedScores(){
        boolean allUsers = btnUser.getText() == btnUser.getTextOff();
        Integer selectedOption = gameOptions.get(spinGameOptions.getSelectedItem().toString());
        String fileName = Board.getHighScoreFile(selectedOption);
        ArrayList<Score> scores = allUsers?
                GameScoreboard.getScores(this, fileName, Board.getComparator()) :
                GameScoreboard.getScoresByUser(this, fileName, user,Board.getComparator());
        String userDisplay = "User";
        String scoreDisplay = "Score";
        for(Score s: scores){
            userDisplay += "\n" + s.getUserName();
            scoreDisplay += "\n" + s.getValue();
        }
        tvUsers.setText(userDisplay);
        tvScores.setText(scoreDisplay);
    }


    /**
     * When user changes the selected game option, update displayed scores
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateDisplayedScores();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
