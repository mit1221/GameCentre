package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import fall2018.csc2017.hangman.HangmanLetters;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Test for HangmanLetters class (keeps track of letters used in a hangman game)
 */
public class HangmanLettersTest {
    private HangmanLetters letters;

    private void setup(String answer){
        letters = new HangmanLetters(answer);
    }

    @Test
    public void testMakeGuessCorrect(){}{
        setup("abcd c");
        assertEquals(true, letters.makeGuess('a'));
        assertEquals(HangmanLetters.LETTER_STATE.CORRECT, letters.getLetterState('a'));
        assertEquals(false, letters.isSolved());
    }

    @Test
    public void testMakeGuessIncorrect(){}{
        setup("abcd c");
        assertEquals(false, letters.makeGuess('z'));
        assertEquals(HangmanLetters.LETTER_STATE.INCORRECT, letters.getLetterState('z'));
        assertEquals(false, letters.isSolved());
    }

    @Test
    public void testConstructorInvalidInput(){
        try{
            letters = new HangmanLetters("invalid123 input!");
            fail();
        }
        catch (Exception e){
            assertEquals("Hangman game can only have alphabetic letters, and at least one letter",
                    e.getMessage());
        }
    }

    @Test
    public void testConstructorEmptyInput(){
        try{
            letters = new HangmanLetters("");
            fail();
        }
        catch (Exception e){
            assertEquals("Hangman game can only have alphabetic letters, and at least one letter",
                    e.getMessage());
        }
    }
}
