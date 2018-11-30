package fall2018.csc2017.sudoku;

import java.util.List;

import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.HighscoresActivity;
import fall2018.csc2017.Score;
import fall2018.csc2017.hangman.HangmanGame;


public class SudokuHSActivity extends HighscoresActivity {
    @Override
    protected List<Score> getScoresToDisplay() {
        boolean allUsers = btnUser.getText() == btnUser.getTextOff();
//        String fileName = HangmanGameActivity.HANGMAN_HS_FILE;
        String fileName = SudokuGameActivity.SUDOKU_HS_FILE;
        return allUsers ?
                GameScoreboard.getScores(this, fileName, HangmanGame.getComparator()) :
                GameScoreboard.getScoresByUser(this, fileName, user, HangmanGame.getComparator());
    }

    @Override
    protected String getTitleText() {
        return "Sudoku Highscores";
    }
}
