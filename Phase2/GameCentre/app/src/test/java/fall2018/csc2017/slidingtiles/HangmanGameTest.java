package fall2018.csc2017.slidingtiles;
import org.junit.Test;

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
}
