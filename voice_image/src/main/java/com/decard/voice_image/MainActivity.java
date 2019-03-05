package com.decard.voice_image;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 适用于被动刷新
         * 通常通过一个子线程来进行画面更新
         * 在底层实现中就实现了双缓冲机制
         */
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(callback);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("surfaceCreated","创建画布");
            if (holder == null) {
                return;
            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);

            //获取到bitmap
            Bitmap bitmap = BitmapUtils.getImageFromAssetsFile(MainActivity.this,"a.png");

            if (bitmap==null) {
                return;
            }
            //先锁定surfaceView画布
            Canvas canvas = holder.lockCanvas();

            //执行绘制操作
            canvas.drawBitmap(bitmap,0,0,paint);
            // 解除锁定并显示在界面上
            holder.unlockCanvasAndPost(canvas);
            bitmap.recycle();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d("surfaceChanged","画布改变   "+width+"    "+height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("surfaceDestroyed","画布销毁   ");
        }
    };

    public void gotoDrawPic(View view) {
        startActivity(new Intent(this,DrawPicActivity.class));
    }

}
