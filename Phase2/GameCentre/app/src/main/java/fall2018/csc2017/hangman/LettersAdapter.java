package fall2018.csc2017.hangman;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;

/**
 * Adapter to create a list of letters/empty slots for the hangman puzzles
 */
public class LettersAdapter extends BaseAdapter{

    /**
     * Letters to display
     */
    private String letters;

    /**
     * Set new letters to display
     * @param letters
     */
    public void setLetters(String letters){this.letters = letters;}


    public LettersAdapter(String letters){
        this.letters = letters;
    }

    @Override
    public int getCount() {
        return letters.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvLetter = new TextView(parent.getContext());
        Character c = letters.charAt(position);
        tvLetter.setText(c.toString());
        tvLetter.setBackgroundColor(Color.WHITE);
        tvLetter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        if(c != ' ' && c!= '_'){ // Underline the text
            tvLetter.setPaintFlags(tvLetter.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        }
        /*if(c == '_'){
            tvLetter.setText(" "); // don't draw both underline and underscore
        }*/
        return  tvLetter;
    }
}
