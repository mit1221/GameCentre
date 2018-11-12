package fall2018.csc2017.slidingtiles;

/**
 * For throwing exceptions when username already exists
 */

class UserException extends Exception {

    UserException(String message) {
        super(message);
    }
}