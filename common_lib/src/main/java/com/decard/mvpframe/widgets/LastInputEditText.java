package com.decard.mvpframe.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * File Description
 *
 * @author Dell
 * @date 2018/7/3
 */
public class LastInputEditText extends android.support.v7.widget.AppCompatEditText {

    public LastInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LastInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LastInputEditText(Context context) {
        super(context);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if (selStart == selEnd) {//防止不能多选
            setSelection(getText().length());
        }

    }
}
