package fall2018.csc2017.hangman;

import java.util.List;

import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.HighscoresActivity;

/**
 * Activity for displaying hangman highscores
 * No unit tests since this class mainly deals with displaying views in android
 */
public class HangmanHSActivity extends HighscoresActivity {
    /**
     * Return list of hangman scores to display
     */
    @Override
    protected List<Score> getScoresToDisplay() {
        boolean allUsers = btnUser.getText() == btnUser.getTextOff();
        String fileName = HangmanGameActivity.HANGMAN_HS_FILE;
        return allUsers ?
                GameScoreboard.getScores(this, fileName, HangmanGame.getComparator()) :
                GameScoreboard.getScoresByUser(this, fileName, user, HangmanGame.getComparator());
    }

    @Override
    protected String getTitleText() {
        return "Hangman Highscores";
    }
}
