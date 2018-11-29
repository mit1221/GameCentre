package fall2018.csc2017.slidingtiles;

import android.view.View;


public class SlidingTilesMenuController {

    public SlidingTilesMenuController() {
    }

    public void setBoardSize(View view, SlidingTilesGameOptions gameOptions) {
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
        gameOptions.setSize(boardSize);
    }

}
