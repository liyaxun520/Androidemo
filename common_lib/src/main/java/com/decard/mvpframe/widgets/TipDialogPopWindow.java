package com.decard.mvpframe.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.decard.mvpframe.R;


/**
 * Created by hizha on 2017/12/27.
 */

public class TipDialogPopWindow {

    private static Activity activity;
    private static TipDialogPopWindow popWindow;
    private PopupWindow mPopWindow;
    private boolean autoDismiss;
    private View showRootView;

    private static final String TAG = "---TipDialogPopWindow";

    private TipDialogPopWindow(Activity activity) {
        this.activity = activity;
    }

    public static TipDialogPopWindow getPopWindow(Activity activity) {
        if (popWindow == null) {
            synchronized (TipDialogPopWindow.class) {
                if (popWindow == null) {
                    popWindow = new TipDialogPopWindow(activity);
                }
            }
        }
        return popWindow;
    }


    private static boolean isLiving(Activity mActivity) {

        if (mActivity == null || mActivity.isFinishing()) {
            return false;
        }
        return true;
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Log.d(TAG, "handleMessage: " + what);
            switch (what) {
                case 9:
                    View viewRel = (View) msg.obj;
                    RelativeLayout contentRelView = (RelativeLayout) mPopWindow.getContentView();
                    contentRelView.removeAllViews();
                    contentRelView.addView(viewRel);
                    if (autoDismiss) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mPopWindow != null) {
                                    mPopWindow.dismiss();
                                }
                            }
                        }, 4000);
                    }
                    break;
                case 10:
                    View view = (View) msg.obj;
                    if (mPopWindow != null) {
                        LinearLayout contentView = (LinearLayout) mPopWindow.getContentView();
                        contentView.removeAllViews();
                        contentView.addView(view);
                        if (autoDismiss) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mPopWindow != null) {
                                        mPopWindow.dismiss();
                                    }
                                }
                            }, 4000);
                        }
                    }

                    break;
                case 12:

                    if (mPopWindow != null) {
                        backgroundAlpha(0.5f);//设置屏幕透明度
                        mPopWindow.showAtLocation(showRootView, Gravity.TOP, 0, 0);
                    }
                    break;
                case 15:
                    backgroundAlpha(1f);
                    break;
            }
        }
    };


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public void showGeneralPopWindowFromButtom(Activity activity, View view, int rootViewid, final int anim, final boolean isAutoDismiss) {

        autoDismiss = isAutoDismiss;
        if (!isLiving(activity)) {
            return;
        }
        showRootView = LayoutInflater.from(activity).inflate(rootViewid, null);
        if (mPopWindow != null && mPopWindow.isShowing()) {
            View view1 = mPopWindow.getContentView();
            if (view1 instanceof LinearLayout) {
                Message message = Message.obtain();
                message.what = 10;
                message.obj = view;
                handler.sendMessage(message);
            } else if (view1 instanceof RelativeLayout) {
                Message message = Message.obtain();
                message.what = 9;
                message.obj = view;
                handler.sendMessage(message);
            }
        } else {
            mPopWindow = null;
            //  创建PopupWindow对象，指定宽度和高度
            mPopWindow = new PopupWindow(activity);

            mPopWindow.setContentView(view);
            mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            // 设置动画
            mPopWindow.setAnimationStyle(anim);
            //设置背景颜色
            mPopWindow.setBackgroundDrawable(new BitmapDrawable());
            // 置可以获取焦点
            mPopWindow.setFocusable(true);
            //设置可以触摸弹出框以外的区域
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.setTouchable(true);
            // 设置显示的位置
            handler.sendEmptyMessage(12);
            mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    handler.sendEmptyMessage(15);
                }
            });
        }

        mPopWindow.update();
        if (autoDismiss) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mPopWindow != null) {
                        mPopWindow.dismiss();
                        handler.sendEmptyMessage(14);
                    }
                }
            }, 2000);
        }
    }


    public void showGeneralPopWindowFromTop(Activity mActivity, View view, int rootViewid, boolean isAutoDismiss) {
        autoDismiss = isAutoDismiss;
        activity = mActivity;
        showGeneralPopWindowFromButtom(activity, view, rootViewid, R.style.top_popwindow_show_anim, isAutoDismiss);
    }

    public void dismiss() {
        if (mPopWindow != null) {
            mPopWindow.dismiss();
            mPopWindow = null;
        }
    }

    public void clearHandlerTask() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
