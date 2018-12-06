package fall2018.csc2017.hangman;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the state of letters in a game of Hangman.
 */
public class HangmanLetters implements Serializable {

    /**
     * Represents the state of a letter in hangman.
     * Can be unused, correct, or incorrect
     */
    public enum LETTER_STATE implements Serializable {
        UNUSED, CORRECT, INCORRECT
    }

    /**
     * Answer to the game. Not case sensitive
     */
    public String getAnswer() {
        return answer;
    }

    private String answer;

    /**
     * Keep track of letters and their state
     */
    private Map<Character, LETTER_STATE> letters;

    /**
     * Returns a hashmap mapping each letter to its state(i.e. if unused or guess was correct)
     */
    public Map<Character, LETTER_STATE> getLetters() {
        return letters;
    }

    /**
     * Create a new HangmanLetters
     *
     * @param answer The answer. The game will not be case sensitive; answer can only contain alphabetic letters
     */
    public HangmanLetters(String answer) {
        // Check if answer makes a valid game (letters and space)
        answer = answer.toUpperCase();
        Pattern p = Pattern.compile("^[A-Z ]+$");
        Matcher m = p.matcher(answer);
        if (!m.find())
            throw new InvalidParameterException("Hangman game can only have alphabetic letters, and at least one letter");

        this.answer = answer;
        letters = new HashMap<>();

        // put all the letters as unused
        for (int i = (int) 'A'; i < (int) 'Z' + 1; i++) {
            letters.put((char) i, LETTER_STATE.UNUSED);
        }
    }

    /**
     * Retrieves the state of the letter in the HangmanLetters
     *
     * @param letter Letter to check. Must be alphabetic letter
     * @return The state of the letter
     */
    public LETTER_STATE getLetterState(Character letter) {
        letter = Character.toUpperCase(letter);
        if (letters.containsKey(letter)) {
            return letters.get(letter);
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

}
