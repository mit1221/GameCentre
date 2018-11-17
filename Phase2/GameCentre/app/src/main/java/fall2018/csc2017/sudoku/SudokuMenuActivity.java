package fall2018.csc2017.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fall2018.csc2017.MenuActivity;
import fall2018.csc2017.slidingtiles.R;

public class SudokuMenuActivity extends AppCompatActivity implements MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_menu);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void loadSavedGame() {

    }
}
