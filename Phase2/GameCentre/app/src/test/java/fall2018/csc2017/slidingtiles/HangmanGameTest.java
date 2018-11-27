package fall2018.csc2017.slidingtiles;
import org.junit.Test;

import java.util.Map;

import fall2018.csc2017.hangman.HangmanGame;
import fall2018.csc2017.hangman.HangmanLetters;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Test for HangmanGame class
 */
public class HangmanGameTest {

    /**
     * Game object to use for testing
     */
    private HangmanGame game;

    private final String CATEGORY = "someCategory";

    private void setup(String answer){
        game = new HangmanGame(answer, CATEGORY);
    }

    @Test
    public void testConstructorValid(){
        String answer = "Some answer";
        setup(answer);
        assertEquals(answer.toUpperCase(), game.getAnswer());
        assertEquals(CATEGORY, game.getCategory());
        assertEquals("____ ______", game.getGameState());
    }

    @Test
    public void testConstructorInvalid(){
        String answer = "Some invalid! answer";
        try {
            setup(answer);
        }
        catch(Exception e){
            assertEquals("Hangman game can only have alphabetic letters, and at least one letter", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testMakeAnswerGuessWrong(){
        String answer = "Some answer";
        setup(answer);
        int initalLives = game.getNumLives();
        assertEquals(false, game.makeAnswerGuess("wrong answer"));
        assertEquals(initalLives-1 , game.getNumLives());
        assertEquals("____ ______", game.getGameState());
    }

    @Test
    public void testMakeAnswerGuessCorrect(){
        String answer = "Some answer";
        setup(answer);
        int initalLives = game.getNumLives();
        assertEquals(true, game.makeAnswerGuess(answer));
        assertEquals(initalLives , game.getNumLives());
        assertEquals(answer.toUpperCase(), game.getGameState());
        assertEquals(true, game.didUserWin());
        assertEquals(true, game.isGameOver());
        assertEquals(true, game.getScore() > 0);
    }

    @Test
    public void testMakeLetterGuessWrong(){
        String answer = "Some answer";
        setup(answer);
        int initalLives = game.getNumLives();
        assertEquals(true, game.makeLetterGuess('z'));
        assertEquals(initalLives-1 , game.getNumLives());
        assertEquals("____ ______", game.getGameState());

        assertEquals(false, game.makeLetterGuess('z'));
        assertEquals(initalLives-1 , game.getNumLives());
        assertEquals("____ ______", game.getGameState());
    }

    @Test
    public void testMakeLetterGuessCorrect(){
        String answer = "Some answer";
        setup(answer);
        int initalLives = game.getNumLives();
        assertEquals(true, game.makeLetterGuess('a'));
        assertEquals(initalLives , game.getNumLives());
        assertEquals("____ A_____", game.getGameState());
        assertEquals(false, game.didUserWin());
        assertEquals(false, game.isGameOver());
    }

    @Test
    public void testGetLetters(){
        setup("some answer");
        Map<Character, HangmanLetters.LETTER_STATE> letters = game.getLetters();
        for(Character c : letters.keySet()){
            assertEquals(HangmanLetters.LETTER_STATE.UNUSED, letters.get(c));
        }
    }

    @Test
    public void testGet(){
        setup("ab a cdefg abc");
        String fixed = game.getFixedGameState(5);
        assertEquals("__ _ ________", fixed);
    }

}
