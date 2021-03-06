package com.decard.mvpframe.widgets;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.decard.mvpframe.R;

public class OrderKeyBoard extends KeyboardView implements KeyboardView.OnKeyboardActionListener {
    //数字键盘
    private Keyboard keyboard;
    //对应的输入框
    private EditText editText;


    public OrderKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 关联自定义键盘与输入框，以及输入框所在的根布局
     * 需要注意此方法需要在输入框的OnTouchListener中当MotionEvent为MotionEvent.ACTION_UP时调用，
     * 否则无法正确阻止系统软键盘的弹出
     */
    public void setAttachToEditText(EditText et) {

        if (keyboard == null) {
            keyboard = new Keyboard(getContext(), R.xml.orderkeyboard);
        }
        this.editText = et;
        editText.setCursorVisible(true);
        editText.requestFocus();
        hideSystemSoftInput();
        showMyKeyBoard();
    }

    /**
     * 显示自定随机数键盘
     */
    private void showMyKeyBoard() {


        setKeyboard(keyboard);
        setEnabled(true);
        setPreviewEnabled(false);

        setVisibility(VISIBLE);
        setOnKeyboardActionListener(this);
    }


    /**
     * 隐藏系统键盘
     */
    private void hideSystemSoftInput() {

        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        Editable editable = editText.getText();
        //获取焦点光标的所在位置
        int start = editText.getSelectionStart();

        switch (primaryCode) {

            case Keyboard.KEYCODE_DELETE://删除
                if (editable != null && editable.length() > 0 && start > 0) {
                    editable.delete(start - 1, start);
                }
                break;
            case Keyboard.KEYCODE_DONE://完成
                break;
            case Keyboard.KEYCODE_CANCEL://取消、隐藏

                break;
            case 46:
                if (editText.getText().toString().endsWith(".")) {
                    editable.insert(start, "00");
                }
                if (!editText.getText().toString().contains(".")) {
                    editable.insert(start, Character.toString((char) primaryCode));
                }
                break;
            default://插入数字
                if (editText.getText().toString().contains(".") && !editText.getText().toString().endsWith(".")) {
                    String str = editText.getText().toString();
                    String[] s = str.split("\\.");
                    Log.d("========", "onKey: " + s[1].length());
                    if (s[1].length() < 2) {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                } else {
                    editable.insert(start, Character.toString((char) primaryCode));
                }

                break;
        }
    }


    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
