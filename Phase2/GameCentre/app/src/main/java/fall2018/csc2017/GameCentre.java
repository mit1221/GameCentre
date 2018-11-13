package fall2018.csc2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesMenuActivity;

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
                launchSlidingTiles();
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
        if (SettingsActivity.current_img == 0){
            img.setImageResource(SettingsActivity.imageNo[0]);
        }
        if (SettingsActivity.current_img == 1){
            img.setImageResource(SettingsActivity.imageNo[1]);}
        if (SettingsActivity.current_img == 2){
            img.setImageResource(SettingsActivity.imageNo[2]);}
        if (SettingsActivity.current_img == 3){
            img.setImageResource(SettingsActivity.imageNo[3]);}
        if (SettingsActivity.current_img == 4){
            img.setImageResource(SettingsActivity.imageNo[4]);}
        if (SettingsActivity.current_img == 5){
            img.setImageResource(SettingsActivity.imageNo[5]);}
    }

    /**
     * Start the SlidingTiles game for the user
     */
    private void launchSlidingTiles(){
        Intent intent = new Intent(GameCentre.this, SlidingTilesMenuActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

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
            System.out.println(e);
        }

    }

}
