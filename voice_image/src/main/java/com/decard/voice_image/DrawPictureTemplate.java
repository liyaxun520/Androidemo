package com.decard.voice_image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawPictureTemplate extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Paint mPaint;
    //子线程标志位
    private boolean mIsDrawing = false;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //路径
    private Path mPath;
    public static final String TAG = "DrawPictureTemplate";

    public DrawPictureTemplate(Context context) {
        super(context);
        initView();
    }

    public DrawPictureTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DrawPictureTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPath=new Path();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start =System.currentTimeMillis();
                while(mIsDrawing){
                    draw();
                    long end = System.currentTimeMillis();
                    if(end-start<100){
                        try{
                            Thread.sleep(100-end+start);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void draw() {
        try{
            //锁定画布并返回画布对象
            mCanvas=mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            if (mCanvas != null) {
                mCanvas.drawColor(Color.WHITE);
                mCanvas.drawPath(mPath,mPaint);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if(mCanvas!=null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: down");
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: move");
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: up");
                break;
        }
        return true;
    }

    public boolean reDraw(){
        mPath.reset();
        return true;
    }

    public void stopDraw(){
        mIsDrawing = false;
    }
}
