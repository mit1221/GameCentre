package fall2018.csc2017.slidingtiles;

import java.io.Serializable;

/**
 * Score object which stores username and score
 */
public class Score implements Serializable{
    private String userName;
    private Integer value;


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



