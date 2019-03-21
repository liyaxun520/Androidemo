package com.example.screenmatchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 最小宽度适配
 */
public class ScreenMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_match);
        getDeviceSmallsWidth();

        dynamicSetSize();
    }

    private void dynamicSetSize() {
        /**
         * 代码动态设置
         *
         */

        /*获取sp值*/
        float pxValue = getResources().getDimension(R.dimen.sp_15);//获取对应资源文件下的sp值
        int spValue = ConvertUtils.px2sp(this, pxValue);//将px值转换成sp值

        /*获取dp值*/
        float pxValue2 = getResources().getDimension(R.dimen.dp_360);//获取对应资源文件下的dp值
        int dpValue = ConvertUtils.px2dp(this, pxValue2);//将px值转换成dp值
    }

    private void getDeviceSmallsWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if(widthDP < heightDP) {
            smallestWidthDP = widthDP;
        }else {
            smallestWidthDP = heightDP;
        }
        Log.d("最小宽度  ",smallestWidthDP+"");
    }


}
