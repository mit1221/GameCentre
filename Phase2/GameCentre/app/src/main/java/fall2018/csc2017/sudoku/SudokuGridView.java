package fall2018.csc2017.sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class SudokuGridView extends GridView {

    private final Context context;

    public SudokuGridView(Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    public SudokuGridView(final Context context, AttributeSet attrs, Context context1) {
        super(context, attrs);
        this.context = context1;

        SudokuAdapter gridAdapter = new SudokuAdapter(context);
        setAdapter(gridAdapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int row = position % 9;
                int col = position / 9;

                Toast.makeText(context, "Row: " + row + " Column: " + col, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
