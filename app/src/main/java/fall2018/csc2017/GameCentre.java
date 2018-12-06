package fall2018.csc2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import fall2018.csc2017.hangman.HangmanMenuActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesMenuActivity;
import fall2018.csc2017.sudoku.SudokuMenuActivity;

public class GameCentre extends AppCompatActivity {

    /**
     * The current user
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);

        // Retrieve the user currently logged in
        user = (User)getIntent().getSerializableExtra("User");

        // setup onclick events for buttons
        Button btnSliding = findViewById(R.id.btnSlidingTiles);
        btnSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame(SlidingTilesMenuActivity.class);
            }
        });

        findViewById(R.id.btnHangman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame(HangmanMenuActivity.class);
            }
        });

        findViewById(R.id.btnSudoku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame(SudokuMenuActivity.class);
            }
        });

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSettings();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView img = findViewById(R.id.profilePicture);
        if(SettingsActivity.current_img >= 0 && SettingsActivity.current_img < SettingsActivity.imageNo.length){
            img.setImageResource(SettingsActivity.imageNo[SettingsActivity.current_img]);
        }
    }

    /**
     * Launches the specified game
     * @param gameClass
     */
    private void launchGame(Class<?> gameClass){
        Intent intent = new Intent(GameCentre.this, gameClass);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    /** Refactored into launchGame
     * Start the SlidingTiles game for the user

    private void launchSlidingTiles(){
        Intent intent = new Intent(GameCentre.this, SlidingTilesMenuActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    } */

    /**
     * Open the settings page.
     */
    private void launchSettings(){
        try {
            Intent intent = new Intent(GameCentre.this, SettingsActivity.class);
            intent.putExtra(user.getUserName(), user);
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
