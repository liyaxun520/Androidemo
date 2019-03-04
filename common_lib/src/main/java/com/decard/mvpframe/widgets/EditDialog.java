package com.decard.mvpframe.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.decard.mvpframe.R;


/**
 * @Created SiberiaDante
 * @Describe： 带EditText的确认or取消弹窗
 * @Time: 2017/6/19
 * @Email: 994537867@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

public class EditDialog implements DialogInterface.OnKeyListener {
    private Context context;
    private Dialog dialog;
    private LinearLayout mLinear_layout;
    private TextView mTvTitle;
    private TextView mEdtContent;
    private TextView btn_neg;
    private TextView btn_pos;
    private ImageView img_line;
    private Display display;

    public EditDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public EditDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_edit, null);
        mLinear_layout = (LinearLayout) view.findViewById(R.id.linear_alert_dialog_bg);
        mTvTitle = view.findViewById(R.id.tv_alert_dialog_title);
        mEdtContent = view.findViewById(R.id.edt_alert_dialog_content);
        btn_neg = view.findViewById(R.id.btn_alert_dialog_cancel);
        btn_pos = view.findViewById(R.id.btn_alert_dialog_sure);
        img_line = (ImageView) view.findViewById(R.id.img_alert_dialog_line);
        dialog = new Dialog(context, R.style.ActionGeneralDialog);
        dialog.setContentView(view);
        dialog.setOnKeyListener(this);
        mLinear_layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), (int)(display.getHeight() * 0.25)));
//        mLinear_layout.setLayoutParams(new FrameLayout.LayoutParams(mLinear_layout.getWidth(), mLinear_layout.getHeight()));
        return this;
    }

    /**
     * @param title
     * @return
     */
    public EditDialog setTitle(String title) {
        if ("".equals(title)) {
            mTvTitle.setText("标题");
        } else {
            mTvTitle.setText(title);
        }
        return this;
    }

    /**
     * @param title
     * @param color
     * @param textSize
     * @return
     */
    public EditDialog setTitle(String title, int color, float textSize) {
        if ("".equals(title)) {
            mTvTitle.setText("标题");
        } else {
            mTvTitle.setText(title);
        }
        mTvTitle.setTextColor(color);
        mTvTitle.setTextSize(textSize);
        return this;
    }

    public TextView getDialogEditText(){
        return mEdtContent;
    }

    /**
     * @param cancelable
     * @return
     */
    public EditDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }


    /**
     * @param text
     * @param listener
     * @return
     */
    public EditDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * @param text
     * @param color
     * @param textSize
     * @param listener
     * @return
     */
    public EditDialog setPositiveButton(String text, int color, float textSize, final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        btn_pos.setTextColor(color);
        btn_pos.setTextSize(textSize);
        return this;
    }

    /**
     * @param text
     * @param listener
     * @return
     */
    public EditDialog setNegativeButton(String text,
                                        final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * @param text
     * @param color
     * @param textSize
     * @param listener
     * @return
     */
    public EditDialog setNegativeButton(String text, int color, float textSize,
                                        final View.OnClickListener listener) {
        if ("".equals(text)) {

            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        btn_neg.setTextColor(color);
        btn_neg.setTextSize(textSize);
        return this;
    }

    /**
     *
     */
    public void dismiss() {
        dialog.dismiss();
    }

    /**
     *
     */
    public void show() {
        dialog.show();
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_BACK){
            dialog.dismiss();
            return true;
        }
        return false;
    }
}
