package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for letting users login and access their games.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Text input for username
     */
    private EditText txtUserName;

    /**
     * Text input for password
     */
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Keep variables to refer to android input ui
        txtUserName = findViewById(R.id.user);
        txtPassword = findViewById(R.id.password);
    }


    /**
     * Try to validate the user; redirect to GameCentre if user exists, otherwise notify user of
     * incorrect credentials.
     * @param userName supplied username
     * @param password supplied password
     */
    public void validate(String userName, String password) {
        User user = UserManager.login(userName, password, this);
        if (user != null) {
            // Login was successful, start the GameCentre and give the activity the user object
            Intent intent = new Intent(LoginActivity.this, GameCentre.class);
            intent.putExtra("User", user);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handle the button click for signing up
     * @param view
     */
    public void onBtnRegisterClick(View view){
        Intent regSwitch = new Intent(this, RegisterActivity.class);
        startActivity(regSwitch );
    }

    /**
     * Handle the button click for logging in
     * @param view
     */
    public void onBtnLoginClick(View view){
        validate(txtUserName.getText().toString(), txtPassword.getText().toString());
    }
}
