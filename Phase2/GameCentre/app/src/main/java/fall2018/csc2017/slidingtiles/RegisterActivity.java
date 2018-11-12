package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity to register a new user
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Text input for username
     */
    private EditText Username;

    /**
     * Text input for password
     */
    private EditText Password;


    /**
     * Handle Click event for register button
     * @param view
     */
    public void onRegisterClick(View view){
        String userName = Username.getText().toString(); // get input from the user
        String password = Password.getText().toString();

        try{
            UserManager.registerNewUser(userName, password, this.getBaseContext()); // not sure if context is correct here
            Toast.makeText(this, "Registered " + userName + "!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } catch(UserException | EmptyFieldException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Username = findViewById(R.id.user);
        Password = findViewById(R.id.password);
    }


}
