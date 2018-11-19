package fall2018.csc2017.hangman;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the state of a game of Hangman.
 */
public class HangmanState implements Serializable{

    /**
     * Represents the state of a letter in hangman.
     * Can be unused, correct, or incorrect
     */
    public enum LETTER_STATE implements Serializable{
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
     * The category the answer belongs to.
     */
    public String getCategory() {
        return category;
    }
    private String category;

    /**
     * Keep track of letters and their state
     */
    private HashMap<Character, LETTER_STATE> letters;

    /**
     * Returns a hashmap mapping each letter to its state(i.e. if unused or guess was correct)
     */
    public HashMap<Character, LETTER_STATE> getLetters() {
        return letters;
    }

    /**
     * Keep track of the number of times the user tries to guess the entire word/answer
     */
    private int numAnswerGuesses;

    /**
     * Create a new HangmanState
     * @param answer The answer. The game will not be case sensitive; answer can only contain alphabetic letters
     */
    public HangmanState(String answer, String category){
        // Check if answer makes a valid game (letters and space)
        answer = answer.toUpperCase();
        Pattern p = Pattern.compile("[A-Z ]+");
        Matcher m = p.matcher(answer);
        if (!m.find())
            throw new InvalidParameterException("Hangman game can only have alphabetic letters, and at least one letter");

        this.answer = answer;
        this.category = category;
        letters = new HashMap<>();
        numAnswerGuesses = 0;

        // put all the letters as unused
        for(int i = (int)'A';  i < (int)'Z' + 1; i++){
            letters.put((char)i, LETTER_STATE.UNUSED);
        }
    }

    /**
     * Retrieves the state of the letter in the HangmanState
     * @param letter Letter to check. Must be alphabetic letter
     * @return The state of the letter
     */
    public LETTER_STATE getLetterState(Character letter){
        letter = Character.toUpperCase(letter);
        if(letters.containsKey(letter)){
            return letters.get(letter);
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

    /**
     * Make a letter guess in hangman
     * @param letter The user's guess
     * @return returns if the user makes a correct guess.
     */
    public boolean makeGuess(Character letter){
        letter = Character.toUpperCase(letter);
        if(letters.containsKey(letter)) {
            if (getAnswer().contains(letter.toString())) {
                letters.put(letter, LETTER_STATE.CORRECT);
                return true;
            } else {
                letters.put(letter, LETTER_STATE.INCORRECT);
                return false;
            }
        }
        throw new InvalidParameterException("Alphabet letter expected");
    }

    /**
     * Make a guess for the entire solution
     * @param guess
     * @return is the guess correct
     */
    public boolean makeAnswerGuess(String guess){
        numAnswerGuesses += 1;
        if(guess.toUpperCase().equals(answer)){

            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the state of the game, with correctly guessed letters shown, "_" otherwise
     * @return
     */
    public String getGameState(){
        StringBuilder state = new StringBuilder();
        for(int i = 0; i < answer.length(); i++){
            Character c = answer.charAt(i);
            // Display letter if correctly guessed or is just a space
            if(c == ' ' || getLetterState(c) == LETTER_STATE.CORRECT){
                state.append(c);
            }
            else // keep it hidden otherwise
                state.append("_");
        }
        return state.toString();
    }

    /**
     * Return if the game has been solved.
     */
    public boolean isSolved(){
        for(Character c : letters.keySet()){
            // If the user didn't use a letter that is in the answer, not solved
            if(getLetterState(c) == LETTER_STATE.UNUSED && answer.contains(c.toString())){
                return false;
            }
        }
        return true;
    }

}
