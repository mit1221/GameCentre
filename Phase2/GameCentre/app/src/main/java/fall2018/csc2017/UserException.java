package fall2018.csc2017;

/**
 * For throwing exceptions when username already exists
 */

public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }
}