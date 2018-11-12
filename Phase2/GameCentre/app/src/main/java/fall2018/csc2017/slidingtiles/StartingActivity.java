package fall2018.csc2017.slidingtiles;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * Current user
     */
    private User user;

    /**
     * Customizable options for the game
     */
    private HashMap<String, Object> gameOptions = new HashMap<>();

    /**
     * Dialog for choosing board image options.
     */
    private AlertDialog imageSelectDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the user who is currently logged in
        user = (User) getIntent().getSerializableExtra("User");
        setContentView(R.layout.activity_starting_);
    }

    /**
     * Button event handler for the 3 starting game buttons.
     *
     * @param view View that was clicked
     */
    public void onBtnStartClick(View view) {
        int boardSize;
        switch (view.getId()) {
            case R.id.StartButton3:
                boardSize = 3;
                break;
            case R.id.StartButton4:
                boardSize = 4;
                break;
            case R.id.StartButton5:
                boardSize = 5;
                break;
            default:
                boardSize = 3;
                break;
        }
        gameOptions.put("size", boardSize);
        openDialog1();
    }

    /**
     * The Load button event handler
     *
     * @param view View that was clicked
     */
    public void onBtnLoadClick(View view) {
        loadSavedGame();
    }

    /**
     * The Highscores button event handler
     *
     * @param view View that was clicked
     */
    public void onBtnHighscoresClick(View view) {
        Intent intent = new Intent(this, HighscoresActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    /**
     * Open the first dialog to ask user for max undos allowed.
     */
    private void openDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // input in the dialog for specifying max # of undos allowed
        final EditText input = new EditText(this);
        input.setText("3");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        // setup the alert builder
        builder.setView(input);
        builder.setTitle("Choose the maximum number of moves to undo");

        // adding a checkbox for allowing unlimited undos
        final String[] items = {"Unlimited"};
        final boolean[] unlimitedUndoMoves = {false};

        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        unlimitedUndoMoves[0] = isChecked;

                        // disabling text field if checkbox is checked
                        input.setEnabled(!isChecked);
                        input.setFocusable(!isChecked);
                        input.setFocusableInTouchMode(!isChecked);
                    }
                });

        // add Next and Cancel buttons
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int undoMoves = Integer.parseInt(String.valueOf(input.getText()));

                if (undoMoves == 0) {
                    Toast.makeText(getApplicationContext(), "Specify a number greater than 0",
                            Toast.LENGTH_SHORT).show();
                } else {
                    undoMoves = unlimitedUndoMoves[0] ? -1 : undoMoves;
                    gameOptions.put("undoMoves", undoMoves);
                    openDialog2();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Open the second dialog to ask user for board image options.
     */
    private void openDialog2() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View imageSelectView = factory.inflate(R.layout.dialog_image_select, null);
        imageSelectDialog = new AlertDialog.Builder(this).create();
        int padding = 45;
        imageSelectView.setPadding(padding, padding, padding, padding);
        imageSelectDialog.setView(imageSelectView);
        imageSelectView.findViewById(R.id.StartGameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = imageSelectDialog.findViewById(R.id.textURL);
                String url = input.getText().toString();

                if (url.equals("")) {
                    startGame();
                    imageSelectDialog.dismiss();
                    return;
                }
                // call asynchronous task
                new ImageFromUrlTask(new ImageFromUrlTask.AsyncResponse() {

                    @Override
                    public void processFinish(Bitmap output) {
                        if (output == null) {
                            Toast.makeText(getApplicationContext(),
                                    "No image at given URL. Try again with another URL.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            gameOptions.put("image", output);
                            try {
                                startGame();
                                imageSelectDialog.dismiss();
                            } catch (RuntimeException ex) {
                                Toast.makeText(getApplicationContext(),
                                        "Image is too big. Try again with another URL.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).execute(url);
            }
        });
        imageSelectView.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectDialog.dismiss();
            }
        });

        imageSelectDialog.show();
    }

    /**
     * Button event handler for clicking on thumbnail.
     *
     * @param view View that was clicked
     */
    public void onBtnChooseImage(View view) {
        ImageView imgView = (ImageView) view;
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();

        Bitmap bitmap = drawable.getBitmap();
        gameOptions.put("image", bitmap);
        startGame();
        imageSelectDialog.dismiss();
    }

    /**
     * Button event handler for clicking upload.
     *
     * @param view View that was clicked
     */
    public void onBtnUploadImage(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        imageSelectDialog.dismiss();
    }

    /**
     * Add all necessary info to the intent and go to the game.
     */
    private void startGame() {
        int boardSize = (int) gameOptions.get("size");
        Board.NUM_COLS = boardSize;
        Board.NUM_ROWS = boardSize;
        Intent intent = new Intent(StartingActivity.this, GameActivity.class);
        intent.putExtra("User", user);
        intent.putExtra("LoadGame", false);

        int undoMoves = (int) gameOptions.get("undoMoves");
        intent.putExtra("MaxUndoMoves", undoMoves);

        Bitmap image = (Bitmap) gameOptions.get("image");

        if (image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 10, stream);

            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image", byteArray);
        }
        startActivity(intent);
    }

    public static final int GET_FROM_GALLERY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                gameOptions.put("image", bitmap);
                startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the user's saved game if there is one.
     */
    private void loadSavedGame() {
        if (user != null && user.hasSave("slidingtiles")) {
            Intent intent = new Intent(StartingActivity.this, GameActivity.class);
            intent.putExtra("User", user);
            intent.putExtra("LoadGame", true);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No game to load", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When activity resumes, reload the user's data in case it has changed
     */
    @Override
    protected void onResume() {
        super.onResume();
        user = UserManager.getUser(user.getUserName(), this); //reload the user data
    }
}
