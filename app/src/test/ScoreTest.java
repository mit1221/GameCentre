package fall2018.csc2017.slidingtiles;

import org.junit.Assert;
import org.junit.Test;


import fall2018.csc2017.Score;


public class ScoreTest {

    /**
     * Test instantiation of new Scores
     */
    @Test
    public void testNewScore() {
        Score s = new Score("user", 25);

    }

    /**
     * Test setters and getters.
     */
    @Test
    public void testSettersAndGetters() {
        Score s = new Score("user", 25);
        Assert.assertEquals(s.getUserName(), "user");
        Assert.assertEquals(s.getValue(), new Integer(25));

    }

}
