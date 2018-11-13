package fall2018.csc2017;

import java.io.Serializable;
import java.util.HashMap;

/**
 * User object which stores user information and saved games
 */
public class User implements Serializable {
    /**
     * this user's username
     */
    private String userName;
    /**
     * this user's hashed password
     */
    private int password;

    // private Board board; // this user's saves. keeping around incase we need to revert
    /**
     * This user's saves (k, v) = (game name, save)
     */
    private HashMap<String, Object> saves = new HashMap<>();


    public User(String userName, int password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return the name of this score's owner.
     */
    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }


    /**
     * @param game
     * @return if this user has a save for game
     */
    public boolean hasSave(String game) {
        return this.saves.get(game) != null;
    }

    /**
     * @param game the game whose save is being set
     * @param save the save to be saved for game.
     */
    public void setSave(String game, Object save) {
        this.saves.put(game, save);
    }

    /**
     * @param game
     * @return the save for game.
     */
    public Object getSave(String game) {
        return this.saves.get(game);
    }


    /**
     * Returns whether parameter credentials match this User's credentials.
     *
     * @param userName username of user
     * @param password password of user
     * @return return true iff credentials are correct
     */
    public boolean checkCredentials(String userName, String password) {
        return userName.equals(this.getUserName()) && password.hashCode() == this.getPassword();
    }


    /**  keeping this around in case we need to revert
     public boolean hasSavedGame(){return board != null;}


     public void setBoard(Board board){ this.board = board;}



     public Board getBoard(){return board;}
     **/


}



