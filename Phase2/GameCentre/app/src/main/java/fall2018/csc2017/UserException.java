package fall2018.csc2017;

/**
 * A UserException
 */

public class UserException extends Exception {

    /**
     * A new UserException with message message.
     * @param message the message to display when the exception is thrown.
     */
    public UserException(String message) {
        super(message);
    }
}