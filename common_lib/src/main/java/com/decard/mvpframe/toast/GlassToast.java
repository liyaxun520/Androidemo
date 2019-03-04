package com.decard.mvpframe.toast;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.decard.mvpframe.R;


/**
 * Created by Vanhel on 2017/10/30.
 */

public class GlassToast extends Toast {

    private static final String GENERAL = "GENERAL";
    private static final String WEAK = "WEAK";
    private static GlassToast glassToast;
    private static String currentType;
    private static String oldType;
    private View toastVeiw;

    private static Handler handler = new Handler();
    private static Runnable runnable = new Runnable() {
        public void run() {
            glassToast.cancel();
        }
    };
    public GlassToast(Context context) {
        super(context);
    }


    public static void showGengralLong(Context context, String message) {
        makeToast(context, GENERAL, message, Toast.LENGTH_LONG, Gravity.CENTER, 0, 0);
    }

    public static void showGengralShort(Context context, String message) {
        makeToast(context, GENERAL, message, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0);
    }

    public static void showWeakLong(Context context, String message) {
        makeToast(context, WEAK, message, Toast.LENGTH_LONG, Gravity.CENTER, 0, 0);
    }

    public static void showWeakShort(Context context, String message) {
        makeToast(context, WEAK, message, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 200);
    }

    private static void makeToast(Context context, String type, String message, int duration, int gravity, int xOffset, int yOffset) {
        handler.removeCallbacks(runnable);
        currentType = type;
        if (glassToast != null && currentType.equals(oldType)) {
            glassToast.setText(message);
        } else {
            glassToast = new GlassToast(context);
            glassToast.setGravity(gravity, xOffset, yOffset);
            glassToast.setDuration(duration);
            glassToast.showToast(context, type, message);
            handler.postDelayed(runnable,5000);
        }
        oldType = currentType;
    }

    private void showToast(Context context, String type, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (type.equals(GENERAL)) {
            toastVeiw = inflater.inflate(R.layout.toast_general, null);
        } else if (type.equals(WEAK)) {
            toastVeiw = inflater.inflate(R.layout.toast_weak, null);
        }

        setText(message);

    }

    private void setText(String message) {
        if (toastVeiw != null) {
            TextView tvMessage = toastVeiw.findViewById(R.id.tv_message);
            tvMessage.setText(message);
            glassToast.setView(toastVeiw);
            glassToast.show();
        }
    }


}
