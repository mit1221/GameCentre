package fall2018.csc2017.slidingtiles;
import org.junit.Assert;
import org.junit.Test;

import fall2018.csc2017.Game;
import fall2018.csc2017.User;

public class UserTest {

    /**
     * Instantiate a dummy user for testing.
     * @return a dummy user for testing.
     */
    private User setupUser(){
        return new User("user",  "1234".hashCode());
    }

    @Test
    public void testInstanceVariables(){
        User u = setupUser();

        Assert.assertEquals(u.getUserName(), "user");
        Assert.assertEquals(u.getPassword(), "1234".hashCode());
    }

    @Test
    public void testSaves(){
        User u = setupUser();
        Object save = new Object();
        Assert.assertFalse(u.hasSave(Game.HANGMAN));
        u.setSave(Game.HANGMAN, save);
        Assert.assertEquals(u.getSave(Game.HANGMAN), save);

    }

    @Test
    public void testCheckCredentials(){
        User u = setupUser();
        Assert.assertTrue(u.checkCredentials("user", "1234"));
        Assert.assertFalse(u.checkCredentials("user", "123"));
        Assert.assertFalse(u.checkCredentials("usr", "1234"));
        Assert.assertFalse(u.checkCredentials("usr", "123"));
    }


}
