package fall2018.csc2017;

import java.io.Serializable;

/**
 * Score object which stores username and score
 */
public class Score implements Serializable{
    private String userName;
    private Integer value;

    /**
     * Create a new scrore.
     *
     * @param userName username of the user
     * @param value    user's score
     */
    public Score(String userName, Integer value) {
        this.userName = userName;
        this.value = value;

    }

    public String getUserName() {
        return userName;
    }



    public Integer getValue() {
        return value;
    }


}



