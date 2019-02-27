package com.decard.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  lottieAnimationView = ((LottieAnimationView) findViewById(R.id.animation_view));
        //1. 开始的时候我们需要设置它的图片存储的地方
        lottieAnimationView.setImageAssetsFolder("images");

        lottieAnimationView.playAnimation();*/

        animationView = ((LottieAnimationView) findViewById(R.id.animation_view_02));
        animationView.setImageAssetsFolder("images");

        //硬件加速，开启之后瞬间丝滑

//       animationView.useHardwareAcceleration(true);

//            合并路径 默认是关闭的，根据自己需求调整
//        animationView.enableMergePathsForKitKatAndAbove(true);
        animationView.playAnimation();
    }
}
