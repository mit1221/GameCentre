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

    private void setup(String answer, Character ... letterGuesses){
        letters = new HangmanLetters(answer);
        for (Character letter : letterGuesses){
            letters.makeGuess(letter);
        }
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

    @Test
    public void testConstructorValidInput(){
        String answer = "Some answer text";
        setup(answer);
        assertEquals(letters.getAnswer(), answer.toUpperCase());
        assertEquals(false, letters.isSolved());
    }

    @Test
    public void testGetLetterStateInitial(){
        setup("some text");
        for( Character letter : letters.getLetters().keySet()){
            assertEquals(HangmanLetters.LETTER_STATE.UNUSED, letters.getLetterState(letter));
        }
    }

    @Test
    public void testGetLetterStateUsed(){
        setup("some text", 's', 'a');
        assertEquals(HangmanLetters.LETTER_STATE.CORRECT, letters.getLetterState('s'));
        assertEquals(HangmanLetters.LETTER_STATE.INCORRECT, letters.getLetterState('a'));
        assertEquals(HangmanLetters.LETTER_STATE.UNUSED, letters.getLetterState('c'));
    }

    @Test
    public void testMakeGuessInvalid(){
        setup("abcd a");
        try{
            letters.makeGuess('1');
        }
        catch(Exception e){
            assertEquals("Alphabet letter expected", e.getMessage());
        }

    }

    @Test
    public void testMakeGuessCorrect(){
        setup("abcd c");
        assertEquals(true, letters.makeGuess('a'));
        assertEquals(HangmanLetters.LETTER_STATE.CORRECT, letters.getLetterState('a'));
        assertEquals(false, letters.isSolved());
    }

    @Test
    public void testMakeGuessIncorrect(){
        setup("abcd c");
        assertEquals(false, letters.makeGuess('z'));
        assertEquals(HangmanLetters.LETTER_STATE.INCORRECT, letters.getLetterState('z'));
        assertEquals(false, letters.isSolved());
    }

    @Test
    public void testIsSolved(){
        setup("abc");
        assertEquals(false, letters.isSolved());

        letters.makeGuess('a');
        assertEquals(false, letters.isSolved());
        letters.makeGuess('b');
        letters.makeGuess('c');
        assertEquals(true, letters.isSolved());
    }

}

