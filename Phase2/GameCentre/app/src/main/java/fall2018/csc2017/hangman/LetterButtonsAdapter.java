package fall2018.csc2017.hangman;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
/**
 * An adapter to create a grid of alphabet letter buttons in a GridView
 */
public class LetterButtonsAdapter extends BaseAdapter {

    /**
     * Listener for when the buttons are clicked on
     */
    private View.OnClickListener listener;

    /**
     * Create a new LetterButtonsAdapter
     * @param listener Listener who is interested in the letter button clicks
     */
    public LetterButtonsAdapter(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btnLetter = new Button(parent.getContext());
        btnLetter.setOnClickListener(listener);
        Character letter = (char)('A' + position);
        btnLetter.setText(letter.toString());
        return btnLetter;
    }

    @Override
    public int getCount() {
        return 26;
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
