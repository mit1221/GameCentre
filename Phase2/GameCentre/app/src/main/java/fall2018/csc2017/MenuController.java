package fall2018.csc2017;

/**
 * Controller for a general menu activity with a user.
 */
public class MenuController {

    /**
     * Current user
     */
    private User user;

    /**
     * Set the user
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the user object
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Check if user has a save.
     *
     * @return
     */
    public boolean userHasSave() {
        return user != null && user.hasSave(Game.SLIDING_TILES);
    }
}

