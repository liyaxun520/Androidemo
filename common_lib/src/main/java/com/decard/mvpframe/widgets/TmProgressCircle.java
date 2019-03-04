package com.decard.mvpframe.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.decard.mvpframe.R;

/**
 * Created by Vanhel on 2017/10/31.
 */

public class TmProgressCircle extends Dialog {


    private final Context context;
    private ImageView ivCircle;
    private TextView tvPrompt;
    private ObjectAnimator circleAnimator;

    public TmProgressCircle(@NonNull Context context) {
        this(context, R.style.TransparentDialog);
    }

    private TmProgressCircle(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                backEvent();
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_dialog_progress);
        initView();
        initAnimator();
        initEvent();

    }

    public void start(){
        if (circleAnimator != null && !circleAnimator.isRunning()) {
            circleAnimator.start();
        }
    }

    public void stop(){
        if (circleAnimator != null && circleAnimator.isRunning()) {
            circleAnimator.cancel();
        }
    }

    private void initView() {
        ivCircle = findViewById(R.id.iv_circle);
        tvPrompt = findViewById(R.id.tv_prompt);

    }

    private void initEvent() {
        setCancelable(false);
        circleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                Log.d("AnimatorListener","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.d("AnimatorListener","onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                Log.d("AnimatorListener","onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
//                Log.d("AnimatorListener","onAnimationRepeat");
            }
        });
    }


    private void initAnimator() {
        circleAnimator = ObjectAnimator
                .ofFloat(ivCircle, "rotation", 359)
                .setDuration(1500);
        circleAnimator.setRepeatMode(ValueAnimator.RESTART);
        circleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        circleAnimator.setInterpolator(new LinearInterpolator());
        circleAnimator.start();
    }

    private void backEvent() {
        if (context instanceof Activity && circleAnimator != null) {
            if (isShowing()) {
                circleAnimator.cancel();
                dismiss();
                ((Activity) context).finish();
            }
        }
    }


}
