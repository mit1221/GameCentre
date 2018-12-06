package fall2018.csc2017.hangman;

import android.content.Context;
import android.util.Pair;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.Map;
import java.util.Observable;

import fall2018.csc2017.GameScoreboard;
import fall2018.csc2017.Score;
import fall2018.csc2017.User;
import fall2018.csc2017.slidingtiles.R;

import static fall2018.csc2017.hangman.HangmanGameActivity.HANGMAN_HS_FILE;

/**
 * Represents a game of hangman
 * Keep track of number of guesses made by user and return score if game is over
 */

public class HangmanGame extends Observable implements Serializable {
    /**
     * Current state of letters in the game
     */
    private HangmanLetters hmLetters;

    /**
     * Return the answer to the game
     */
    public String getAnswer() {
        return hmLetters.getAnswer();
    }

    /**
     * The category the answer belongs to.
     */
    public String getCategory() {
        return category;
    }

    private String category;

    /**
     * Keep track of the number of times the user tries to guess the entire word/answer
     */
    private int numAnswerGuesses;

    /**
     * Number of times the user guessed the wrong letter
     */
    private int numWrongLetters;

    /**
     * Number of times user guessed correct letter
     */
    private int numCorrectLetters;

    /**
     * The number of lives the user has
     */
    public int getNumLives() {
        return numLives;
    }

    private int numLives;

    /**
     * Create a new Hangman game
     */
    public HangmanGame(String answer, String category) {
        hmLetters = new HangmanLetters(answer);
        this.category = category;
        numAnswerGuesses = 0;
        numLives = 6;
    }

    /**
     * Make a letter guess in hangman
     *
     * @param letter The user's guess
     * @return returns if the user makes a correct guess.
     */
    private boolean makeGuess(Character letter) {
        letter = Character.toUpperCase(letter);
        if (hmLetters.getLetters().containsKey(letter)) {
            if (getAnswer().contains(letter.toString())) {
                hmLetters.getLetters().put(letter, HangmanLetters.LETTER_STATE.CORRECT);
                return true;
            } else {
                hmLetters.getLetters().put(letter, HangmanLetters.LETTER_STATE.INCORRECT);
                return false;
            }
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

    /**
     * Make a guess for the entire solution
     *
     * @param guess the guess made
     * @return is the guess correct
     */
    public boolean makeAnswerGuess(String guess) {
        boolean isGuessCorrect = guess.toUpperCase().equals(hmLetters.getAnswer());
        if (isGuessCorrect) {
            revealAnswer();
        } else {
            numLives -= 1;
            numAnswerGuesses += 1;
        }
        afterMoveMade();
        return isGuessCorrect;
    }

    /**
     * Make a letter guess.
     *
     * @param guess
     * @return If the guess was valid (not used before)
     */
    public boolean makeLetterGuess(Character guess) {
        boolean unused = hmLetters.getLetterState(guess) == HangmanLetters.LETTER_STATE.UNUSED;
        if (unused) {
            if (makeGuess(guess)) {
                numCorrectLetters += 1;
            } else {
                numWrongLetters += 1;
                numLives -= 1;
            }
            afterMoveMade();
        }
        return unused;
    }

    /**
     * Returns a hashmap mapping each letter to its hmLetters(i.e. if unused or guess was correct)
     */
    public Map<Character, HangmanLetters.LETTER_STATE> getLetters() {
        return hmLetters.getLetters();
    }

    /**
     * Sets all correct letters in the game to reveal the answer
     */
    private void revealAnswer() {
        for (Character letter : hmLetters.getLetters().keySet()) {
            if (hmLetters.getAnswer().indexOf(letter) != -1) {
                makeGuess(letter);
            }
        }
    }

    /**
     * Nofify observers and reveal answer if game is over
     */
    private void afterMoveMade() {
        if (isGameOver() && !didUserWin()) {
            revealAnswer();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the state of letters in the game, with correctly guessed letters shown, "_" otherwise
     */
    public String getGameState() {
        StringBuilder strState = new StringBuilder();
        for (int i = 0; i < hmLetters.getAnswer().length(); i++) {
            Character c = hmLetters.getAnswer().charAt(i);
            // Display letter if correctly guessed or is just a space
            if (c == ' ' || hmLetters.getLetterState(c) == HangmanLetters.LETTER_STATE.CORRECT) {
                strState.append(c);
            } else // keep it hidden otherwise
                strState.append("_");
        }
        return strState.toString();
    }

    /**
     * Return if the game has been solved.
     *
     * @return if the game has been solved
     */
    public boolean isSolved() {
        for (Character c : hmLetters.getLetters().keySet()) {
            // If the user didn't use a letter that is in the answer, not solved
            if (hmLetters.getLetterState(c) == HangmanLetters.LETTER_STATE.UNUSED
                    && hmLetters.getAnswer().contains(c.toString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return if the user has won
     * @return if the user has won
     */
    public boolean didUserWin() {
        return numLives > 0 && isSolved();
    }

    /**
     * Return if the game is over
     * @return if the game is over
     */
    public boolean isGameOver() {
        return didUserWin() || numLives <= 0;
    }

    /**
     * Add score to the scoreboard if the user won the game.
     *
     * @param c    context
     * @param user the user
     * @return result of the game
     */
    public String processGameOver(Context c, User user) {
        String result;
        if (didUserWin()) {
            Score score = new Score(user.getUserName(), getScore());
            GameScoreboard.addScore(c, HANGMAN_HS_FILE, score);
            result = "YOU WIN !!!";
        } else {
            result = "GAME OVER";
        }
        return result;
    }

    /**
     * Returns the score of the game.
     * @return the score of the game if it is over, else -1
     */
    public int getScore() {
        if (didUserWin()) {
            return Math.max(0, getAnswer().length() * 2 - numWrongLetters * 2 - numCorrectLetters - numAnswerGuesses);
        }
        return -1;
    }

    /**
     * Returns the comparator used to compare hangman scores
     * @return the comparator used to compare hangman scores
     */
    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getValue() - o1.getValue();
            }
        };
    }

    /**
     * Returns the state of letters in the game, with correctly guessed letters shown, "_" otherwise
     * Extra spaces are inserted to fix the string, such that words are not cut off (if possible) when displayed.
     *
     * @param maxWidth The maximum number of characters allowed per line
     * @return the fixed string representing the state of the game
     */
    public String getFixedGameState(int maxWidth) {
        String[] words = getGameState().split(" ");
        StringBuilder out = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            int available = maxWidth - (out.length() % maxWidth);

            if (available == maxWidth) { // is a new line
                out.append(word); // no need to add a space before
            } else if (word.length() + 1 <= available) { // no longer a new line; need to add a space before
                out.append(" ").append(word);
            } else { // word.length + 1 > available
                // if the word is longer than maxNumColumns, just add it
                out.append(" ");
                if (word.length() <= maxWidth) {
                    while (out.length() % maxWidth != 0) { // add spaces until you get to a new line
                        out.append(" ");
                    }
                }
                // now at a new line, just add the word
                out.append(word);
            }
        }
        return out.toString();
    }

    /**
     * Returns the length of the longest word in the puzzle
     * @return length of the longest word in the puzzle
     */
    public int getLongestWordLength() {
        int max = 0;
        // set to max length word
        for (String word : getAnswer().split(" ")) {
            if (word.length() > max) {
                max = word.length();
            }
        }
        return max;
    }
}
