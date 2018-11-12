package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

/**
 * Methods controlling scoreboards for games. Not meant to be instantiated.
 */
public abstract class GameScoreboard implements Serializable {

    /**
     * Add score s to scoreboard stored at dir and resort scoreboard for later use.
     * @param c
     * @param dir the directory containing the scoreboard to add the scores to
     * @param s the score to be added
     */
    public static void addScore(Context c, String dir, Score s) {
        ArrayList<Score> sBoard = getScores(c, dir, Board.getComparator());
        sBoard.add(s);
        saveScores(c, sBoard, dir);
    }

    /**
     * Return the scores for user from the scoreboard stored at directory dir.
     * @param c
     * @param dir the directory containing the desired scoreboard
     * @param user the user whose scores will be filtered for
     * @param comp the comparator by which the scores in the scoreboard should be sorted
     * @return a sub scoreboard containing only the scores possessed by user
     */
    public static ArrayList<Score> getScoresByUser(Context c, String dir, User user, Comparator<Score> comp) {
        ArrayList<Score> ret = new ArrayList<>();
        ArrayList<Score> sBoard = getScores(c, dir, comp);
        for (Score s: sBoard) {
            if (s.getUserName().equals(user.getUserName()))
                ret.add(s);
        }
        return ret;
    }

    /**
     * Save scoreboard to directory dir.
     * @param c
     * @param scoreBoard the scoreboard to save
     * @param dir the directory to save the scoreboard to
     */
    private static void saveScores(Context c, ArrayList<Score> scoreBoard, String dir) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    c.openFileOutput(dir, MODE_PRIVATE));
            outputStream.writeObject(scoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the scores stored in directory dir.
     * @param c
     * @param dir the directory to get the scores from.
     * @param comp the comparator by which the scores in the scoreboard should be sorted
     * @return the scoreboard stored at directory dir.
     */
    public static ArrayList<Score> getScores(Context c, String dir, Comparator<Score> comp) {
        try {
            InputStream inputStream = c.openFileInput(dir);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                ArrayList<Score> scores = (ArrayList<Score>) input.readObject();
                scores.sort(comp);
                inputStream.close();
                return scores;
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            Log.e("login activity", "Cannot read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return new ArrayList<>();
    }

}












