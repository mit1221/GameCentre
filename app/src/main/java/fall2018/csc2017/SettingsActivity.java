package fall2018.csc2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fall2018.csc2017.slidingtiles.R;


/**
 * A settings page.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * The profile picture being displayed.
     */
    public ImageView imgView;

    /**
     * An integer representing the profile picture
     */
    public static int current_img;

    /**
     * An array containing all profile pictures.
     */
    public static int imageNo[] = {R.drawable.gand,  R.drawable.yodawg, R.drawable.super_mario_bros_face_embroidery_design_319_0, R.drawable.smartboy, R.drawable.bitm, R.drawable.willshortz};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonClick();
    }


    /**
     * Cycle through all profile pictures.
     */
    public void buttonClick(){
            imgView = findViewById(R.id.imageView);
        Button ppBtn = findViewById(R.id.btnPP);
            ppBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            current_img++;
                            System.out.println(current_img);
                            current_img=current_img % imageNo.length;
                            imgView.setImageResource(imageNo[current_img]);


                        }
                    }
            );

        }




    }
