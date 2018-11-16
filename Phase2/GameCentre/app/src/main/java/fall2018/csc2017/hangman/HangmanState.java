package fall2018.csc2017.hangman;

import java.util.HashMap;

/**
 * Represents the state of a HangmanGame (i.e. letters used, # failed guesses)
 */
public class HangmanState {

    /**
     * Solution to the game
     */
    private String answer;

    /**
     * Keep track of letters and if they were used by the user:
     * 0 => Unused
     * 1 => correct guess
     * 2 => wrong guess
     */
    private HashMap<Character, Integer> letters;


    /**
     * Create a new HangmanState
     * @param answer The solution
     */
    public HangmanState(String answer){
        this.answer = answer;
        letters = new HashMap<>();
        // put all the letters as unused
        for(int i = (int)'a';  i < (int)'z' + 1; i++){
            letters.put((char)i, 0);
        }
    }

}
