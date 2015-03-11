package rj.hive.tastery.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditTextBrandon extends EditText{

    private Context c;
    private static final String fontlocation = "fonts/brandon/Brandon_reg.otf";
    public MyEditTextBrandon(Context c) {
        super(c);
        this.c = c;
        Typeface tfs = Typeface.createFromAsset(c.getAssets(),
        		fontlocation);
        setTypeface(tfs);

    }
    public MyEditTextBrandon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = context;
        Typeface tfs = Typeface.createFromAsset(c.getAssets(),
        		fontlocation);
        setTypeface(tfs);
        // TODO Auto-generated constructor stub
    }

    public MyEditTextBrandon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        Typeface tfs = Typeface.createFromAsset(c.getAssets(),
        		fontlocation);
        setTypeface(tfs);

    }


}