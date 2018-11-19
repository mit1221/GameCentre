package fall2018.csc2017.hangman;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * An adapter to create a grid of alphabet letter buttons in a GridView
 */
public class LetterButtonsAdapter extends BaseAdapter {

    /**
     * Listener for when the buttons are clicked on
     */
    private View.OnClickListener listener;

    /**
     * Hashmap keeping track of the state of each letter
     */
    private HashMap<Character, HangmanState.LETTER_STATE> letters;

    /**
     * Create a new LetterButtonsAdapter
     * @param listener Listener who is interested in the letter button clicks
     */
    public LetterButtonsAdapter(View.OnClickListener listener,
                                HashMap<Character, HangmanState.LETTER_STATE> letters){
        this.letters = letters;
        this.listener = listener;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Retrieve the correct letter to display (in alphabetic order)
        Set<Character> chars = letters.keySet();
        Character[] arrChars = chars.toArray(new Character[chars.size()]);
        Arrays.sort(arrChars);
        Character letter = arrChars[position];

        // Setup the button for the letter
        Button btnLetter = new Button(parent.getContext());
        btnLetter.setOnClickListener(listener);
        btnLetter.setText(letter.toString());
        btnLetter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        // Color the button if its correct/incorrect
        HangmanState.LETTER_STATE letterState = letters.get(letter);
        if(letterState != HangmanState.LETTER_STATE.UNUSED){
            btnLetter.setTextColor(Color.WHITE);
            btnLetter.setBackgroundColor(letterState == HangmanState.LETTER_STATE.CORRECT ? Color.GREEN : Color.MAGENTA);
        }
        return btnLetter;
    }

    @Override
    public int getCount() {
        return letters.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
