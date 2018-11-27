package fall2018.csc2017;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Utility class to assist with managing users.
 */
public final class UserManager implements Serializable {
    private static ArrayList<User> users = new ArrayList<>();

    /**
     * A default directory for saving/loading to/from.
     */
    private static String defaultDir = "users.txt";

    /**
     * Prevent instantiation of the class.
     */
    private UserManager() {
    }

    /**
     * Register a new user with the given userName and password to directory dir.
     *
     * @param userName the new user's name
     * @param password the new user's password(hashed)
     * @param dir the directory to register to
     * @return the newly registered user
     */
    public static User registerNewUser(String userName, String password, Context c, String dir) throws
            UserException, EmptyFieldException {
        if (userName.equals("") || password.equals("")) {
            throw new EmptyFieldException("Username or password cannot be empty");
        }
        if (userExists(userName, c, dir)) { // userName already exists in the stored users
            throw new UserException("User with userName " + userName + " already exists!");
        } else {
            User newUser = new User(userName, password.hashCode()); // hash the password
            users.add(newUser);
            saveUsers(c, dir);
            return newUser;
        }
    }

    /**
     * Register a new user with the given userName and password to default directory.
     * @param userName the new user's name
     * @param password the new user's password(hashed)
     * @return the newly registered user
     */
    public static User registerNewUser(String userName, String password, Context c) throws
            UserException, EmptyFieldException{
        return registerNewUser(userName, password, c, defaultDir);
    }

    /**
     * Check if user exists in directory dir.
     * @param userName name of the user to check for.
     * @param c        context to read files in
     * @param dir the directory to check in
     * @return whether the user already exists
     */
    public static boolean userExists(String userName, Context c, String dir) {
        return getUser(userName, c, dir) != null;
    }

    /**
     * Check if user exists in default dir.
     * @param userName name of the user to check for.
     * @param c        context to read files in
     * @return whether the user already exists
     */
    public static boolean userExists(String userName, Context c) {
        return userExists(userName, c, defaultDir);
    }

    /**
     * Get user from directory dir if user exists.
     * @param userName name of the user to look for.
     * @param dir the directory to look in
     * @return User object if it exists, null otherwise
     */
    public static User getUser(String userName, Context c, String dir) {
        loadUsers(c, dir);
        for (User u : users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Get user from default dir if user exists.
     * @param userName
     * @return User object if it exists, null otherwise
     */
    public static User getUser(String userName, Context c) {
        return getUser(userName, c, defaultDir);
    }

    /**
     * @param userName the username of the user trying to login
     * @param password the password of the user trying to login
     * @param c        the context from which to login.
     * @param dir       the directory containing the user attempting to log in
     * @return the user if the login was successful, else null.
     */
    public static User login(String userName, String password, Context c, String dir) {
        loadUsers(c, dir);
        for (User u : users) {
            if (u.checkCredentials(userName, password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Version of login() utilizing the default directory.
     * @param userName the username of the user trying to login
     * @param password the password of the user trying to login
     * @param c        the context from which to login.
     * @return the user if the login was successful, else null.
     */
    public static User login(String userName, String password, Context c) {
        return login(userName, password, c, defaultDir);

    }
    /**
     * Saves users to text file.
     *
     * @param c the context in which to save users
     * @param dir the directory to save users to
     */
    private static void saveUsers(Context c, String dir) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    c.openFileOutput(dir, MODE_PRIVATE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save users to default text file
     * @param c the context in which to save users
     */
    private static void saveUsers(Context c) {
        saveUsers(c, defaultDir);
    }

    /**
     * Loads users from text file.
     *
     * @param c the context in which to load users
     * @param dir the directory to load users from
     */
    @SuppressWarnings("unchecked")
    private static void loadUsers(Context c, String dir) {

        try {
            InputStream inputStream = c.openFileInput(dir);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                users = (ArrayList<User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Cannot read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Loads users from default text file.
     * @param c the context in which to load users
     */
    private static void loadUsers(Context c){
        loadUsers(c, defaultDir);
    }

    /**
     * Save the user's state; replaces the state if user already exists. Utilize directory dir.
     *
     * @param user User object to save
     * @param c    Context to use for saving files
     * @param dir   the directory to use
     */
    public static void saveUserState(User user, Context c, String dir) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUserName().equals(user.getUserName())) { // replace existing user data if exists
                users.remove(i);
                break;
            }
        }
        users.add(user);
        saveUsers(c, dir);
    }
    /**
     * Save the user's state; replaces the state if user already exists. Utilize default directory.
     *
     * @param user User object to save
     * @param c    Context to use for saving files
     */
    public static void saveUserState(User user, Context c) {
        saveUserState(user, c, defaultDir);
    }
}
