package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;

import static fall2018.csc2017.hangman.WordManager.*;

public class WordManagerTest {

    public static ArrayList<String> ts = new ArrayList<>();

    @Test
    public void returnsRandomWord() {
        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        org.junit.Assert.assertTrue(a.indexOf(randWordFromList(a)) != -1);

    }
}
