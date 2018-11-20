package fall2018.csc2017.hangman;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents a game of hangman
 * Keep track of number of guesses made by user and return score if game is over
 */

public class HangmanGame implements Serializable{
    /**
     * Current state of letters in the game
     */
    private HangmanLetters hgLetters;

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
    private int numLives;

    /**
     * Create a new Hangman game
     */
    public HangmanGame(String answer, String category){
        hgLetters = new HangmanLetters(answer);
        this.category = category;
        numAnswerGuesses = 0;
        numLives = 5;
    }

    /**
     * Make a guess for the entire solution
     * @param guess
     * @return is the guess correct
     */
    public boolean makeAnswerGuess(String guess){
        numAnswerGuesses += 1;
        boolean isGuessCorrect =  guess.toUpperCase().equals(hgLetters.getAnswer());
        if(isGuessCorrect){
            revealAnswer();
        }
        return isGuessCorrect;
    }

    /**
     * Make a letter guess.
     * @param guess
     * @return If the guess was valid (not used before)
     */
    public boolean makeLetterGuess(Character guess){
        boolean unused = hgLetters.getLetterState(guess) == HangmanLetters.LETTER_STATE.UNUSED;
        if(unused){
            if(hgLetters.makeGuess(guess)){
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
     * Returns a hashmap mapping each letter to its hgLetters(i.e. if unused or guess was correct)
     */
    public Map<Character, HangmanLetters.LETTER_STATE> getLetters() {
        return hgLetters.getLetters();
    }

    /**
     * Sets all correct letters in the game hgLetters to reveal the answer
     */
    private void revealAnswer(){
        for(Character letter : hgLetters.getLetters().keySet()){
            if(hgLetters.getAnswer().indexOf(letter) != -1){
                hgLetters.makeGuess(letter);
            }
        }
    }

    /**
     * Return the answer to the game
     */
    public String getAnswer(){
        return hgLetters.getAnswer();
    }

    /**
     * Returns the hgLetters of the game, with correctly guessed letters shown, "_" otherwise
     */
    public String getGameState(){
        StringBuilder strState = new StringBuilder();
        for(int i = 0; i < hgLetters.getAnswer().length(); i++){
            Character c = hgLetters.getAnswer().charAt(i);
            // Display letter if correctly guessed or is just a space
            if(c == ' ' || hgLetters.getLetterState(c) == HangmanLetters.LETTER_STATE.CORRECT){
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
        return numLives > 0 && hgLetters.isSolved();
    }

    /**
     * Return if the game is over
     */
    public boolean isGameOver(){
        return didUserWin() || numLives == 0;
    }


    /**
     * Returns the score of the game if it is over.
     * If it is not over, then returns -1
     *
     */
    public int getScore(){
        if(isGameOver()){
            return getAnswer().length()*2 - numWrongLetters * 2 - numCorrectLetters;
        }
        return -1;
    }
}
