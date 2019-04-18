package com.makerspace.smart_ccad_text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 2017/12/12 9:53
 */

public class MyText extends TextView {

    public MyText(Context context) {
        super(context);
    }

    public MyText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    public boolean isFocused() {
        return true;
    }
}
