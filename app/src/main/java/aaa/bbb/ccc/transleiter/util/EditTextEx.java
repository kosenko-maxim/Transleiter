package aaa.bbb.ccc.transleiter.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextEx extends EditText {
    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextEx(Context context) {
        super(context);
        init();
    }

    public EditTextEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                dispatchKeyEvent(event);
                return true;
            }
        }
        return false;
    }

    public void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "gothic.ttf");
            setTypeface(tf);
        }
    }
}