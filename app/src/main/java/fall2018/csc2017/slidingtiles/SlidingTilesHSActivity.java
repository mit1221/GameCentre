package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.Board;
import fall2018.csc2017.Game;
import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.HighscoresActivity;
import fall2018.csc2017.Score;

/**
 * Activity for displaying Sliding Tiles highscores
 */
public class SlidingTilesHSActivity extends HighscoresActivity implements AdapterView.OnItemSelectedListener{

    /**
     * Maps the string displayed in the spinner to the size of the game
     */
    private final Map<String, Integer> gameOptions = new HashMap<String,Integer>(){{
        put("3x3", 3);
        put("4x4", 4);
        put("5x5", 5);
    }};

    /**
     * Spinner for choosing which size games to show
     */
    private Spinner spinGameOptions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rlRoot = findViewById(R.id.rlRoot);

        //Setup the spinner options https://developer.android.com/guide/topics/ui/controls/spinner
        spinGameOptions = new Spinner(this);
        String[]options = new String[gameOptions.size()];
        gameOptions.keySet().toArray(options);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(200, 250, 0, 0);
        spinGameOptions.setLayoutParams(params);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGameOptions.setAdapter(adapter);
        spinGameOptions.setOnItemSelectedListener(this);

        rlRoot.addView(spinGameOptions);
    }

    /**
     * Returns the list of scores to display in the activity;
     */
    protected List<Score> getScoresToDisplay(){
        boolean allUsers = btnUser.getText() == btnUser.getTextOff();
        Integer selectedOption = gameOptions.get(spinGameOptions.getSelectedItem().toString());
        String fileName = Board.getHighScoreFile(Game.SLIDING_TILES, selectedOption);
        return allUsers?
                GameScoreboard.getScores(this, fileName, Board.getComparator()) :
                GameScoreboard.getScoresByUser(this, fileName, user, Board.getComparator());
    }

    @Override
    protected String getTitleText() {
        return "Sliding Tiles Highscores";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateDisplayedScores();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
