package fall2018.csc2017.hangman;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import fall2018.csc2017.Score;
import fall2018.csc2017.slidingtiles.R;

/**
 * Represents a game of hangman
 * Keep track of number of guesses made by user and return score if game is over
 */

public class HangmanGame implements Serializable{
    /**
     * Current state of letters in the game
     */
    private HangmanLetters hmLetters;

    /**
     * Return the answer to the game
     */
    public String getAnswer(){
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
    public int getNumLives(){return numLives;}
    private int numLives;

    /**
     * Create a new Hangman game
     */
    public HangmanGame(String answer, String category){
        hmLetters = new HangmanLetters(answer);
        this.category = category;
        numAnswerGuesses = 0;
        numLives = 3;
    }

    /**
     * Make a guess for the entire solution
     * @param guess
     * @return is the guess correct
     */
    public boolean makeAnswerGuess(String guess){
        boolean isGuessCorrect =  guess.toUpperCase().equals(hmLetters.getAnswer());
        if(isGuessCorrect){
            revealAnswer();
        }
        else{
            numLives -= 1;
            numAnswerGuesses += 1;
        }
        return isGuessCorrect;
    }

    /**
     * Make a letter guess.
     * @param guess
     * @return If the guess was valid (not used before)
     */
    public boolean makeLetterGuess(Character guess){
        boolean unused = hmLetters.getLetterState(guess) == HangmanLetters.LETTER_STATE.UNUSED;
        if(unused){
            if(hmLetters.makeGuess(guess)){
                numCorrectLetters += 1;
            }
            else{
                numWrongLetters += 1;
                numLives -= 1;
            }
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
    public void revealAnswer(){
        for(Character letter : hmLetters.getLetters().keySet()){
            if(hmLetters.getAnswer().indexOf(letter) != -1){
                hmLetters.makeGuess(letter);
            }
        }
    }

    /**
     * Returns the state of letters in the game, with correctly guessed letters shown, "_" otherwise
     */
    public String getGameState(){
        StringBuilder strState = new StringBuilder();
        for(int i = 0; i < hmLetters.getAnswer().length(); i++){
            Character c = hmLetters.getAnswer().charAt(i);
            // Display letter if correctly guessed or is just a space
            if(c == ' ' || hmLetters.getLetterState(c) == HangmanLetters.LETTER_STATE.CORRECT){
                strState.append(c);
            }
            else // keep it hidden otherwise
                strState.append("_");
        }
        return strState.toString();
    }

    /**
     * Return if the user has won
     */
    public boolean didUserWin(){
        return numLives > 0 && hmLetters.isSolved();
    }

    /**
     * Return if the game is over
     */
    public boolean isGameOver(){
        return didUserWin() || numLives <= 0;
    }

    /**
     * Returns the score of the game if it is over.
     * If it is not over, then returns -1
     *
     */
    public int getScore(){
        if(didUserWin()){
            return getAnswer().length()*2 - numWrongLetters * 2 - numCorrectLetters - numAnswerGuesses;
        }
        return -1;
    }

    /**
     * Returns the comparator used to compare hangman scores
     */
    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getValue() - o1.getValue();
            }
        };
    }
}
