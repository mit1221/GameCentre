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
    private static String saveDirectory = "users.txt";

    /**
     * Prevent instantiation of the class.
     */
    private UserManager() {
    }

    /**
     * Register a new user with the given userName and password.
     *
     * @param userName the new user's name
     * @param password the new user's password(hashed)
     */
    static void registerNewUser(String userName, String password, Context c) throws
            UserException, EmptyFieldException {
        if (userName.equals("") || password.equals("")) {
            throw new EmptyFieldException("Username or password cannot be empty");
        }
        if (userExists(userName, c)) { // userName already exists in the stored users
            throw new UserException("User with userName " + userName + " already exists!");
        } else {
            User newUser = new User(userName, password.hashCode()); // hash the password
            users.add(newUser);
            saveUsers(c);
        }
    }

    /**
     * @param userName name of the user to check for.
     * @param c        context to read files in
     * @return whether the user already exists
     */
    private static boolean userExists(String userName, Context c) {
        return getUser(userName, c) != null;
    }

    /**
     * @param userName name of the user to look for.
     * @return User object if it exists, null otherwise
     */
    public static User getUser(String userName, Context c) {
        loadUsers(c);
        for (User u : users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        return null;
    }

    /**
     * @param userName the username of the user trying to login
     * @param password the password of the user trying to login
     * @param c        the context from which to login.
     * @return the user if the login was successful, else null.
     */
    public static User login(String userName, String password, Context c) {
        loadUsers(c);
        for (User u : users) {
            if (u.checkCredentials(userName, password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Saves users to text file.
     *
     * @param c the context in which to save users
     */
    private static void saveUsers(Context c) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    c.openFileOutput(saveDirectory, MODE_PRIVATE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Loads users from text file.
     *
     * @param c the context in which to load users
     */
    @SuppressWarnings("unchecked")
    private static void loadUsers(Context c) {

        try {
            InputStream inputStream = c.openFileInput(saveDirectory);
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
     * Save the user's state; replaces the state if user already exists
     *
     * @param user User object to save
     * @param c    Context to use for saving files
     */
    public static void saveUserState(User user, Context c) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUserName().equals(user.getUserName())) { // replace existing user data if exists
                users.remove(i);
                break;
            }
        }
        users.add(user);
        saveUsers(c);
    }
}
