package com.decard.x2cdemo.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;

import com.decard.x2cdemo.R;

public class CentralTractionButton extends android.support.v7.widget.AppCompatRadioButton {
    public static final String TAG = CentralTractionButton.class.getSimpleName();
    //四个图片的id
    private int normalexternalbackground = 0;
    private int normalinsidebackground= 0;
    private int selectedinsidebackground= 0;
    private int selectedexternalbackground = 0;
    //文字
    private float textdimension = 0f;
    private String text = "";
    //绘制图形的画笔
    private Paint bmPaint = null;
    //图形偏移距离
    private float offsetDistanceLimit = 0f;

    //组件宽高
    private float mWidth = 0f;
    private float mHeight= 0f;
    //中心点坐标,相较于屏幕
    private float centerX = 0f;
    private float centerY = 0f;
    //中心点坐标,相较于组件内
    private float centerx = 0f;
    private float centery = 0f;

    private Rect mExternalSrcRect= null;
    private Rect mExternalDestRect = null;
    private Rect mInsideSrcRect = null;
    private Rect mInsideDestRect = null;

    BitmapDrawable externalBD = null;
    BitmapDrawable insidelBD = null;


    public CentralTractionButton(Context context) {
        super(context);
        init();
    }

    public CentralTractionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        init();
    }

    public CentralTractionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypeArray(context, attrs);
        init();
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ctattrs);
        text = ta.getString(R.styleable.ctattrs_text);
        textdimension = ta.getDimension(R.styleable.ctattrs_textdimension, 1f);
        normalexternalbackground = ta.getResourceId(R.styleable.ctattrs_normalexternalbackground, 0);
        normalinsidebackground = ta.getResourceId(R.styleable.ctattrs_normalinsidebackground, 0);
        selectedinsidebackground = ta.getResourceId(R.styleable.ctattrs_selectedinsidebackground, 0);
        selectedexternalbackground = ta.getResourceId(R.styleable.ctattrs_selectedexternalbackground, 0);

        //打印所有的属性
        int count = attrs.getAttributeCount();
        for (int i=0;i<count - 1;i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            Log.d(TAG,"attrName = $attrName , attrVal = $attrVal");
        }
        ta.recycle();
    }

    //轨迹圆外径的半径mR = ob
    float mR = 0f;
    //背景图图形的半径 = 长宽(这里类似于直径)/2 = ob/2
    float mr = 0f;
    private void init() {
        initPaint();
        //得到组件宽高中的较小值,再/2得到ob的距离
        if (mHeight > mWidth){ mR = mHeight / 2; }else {mR = mWidth / 2;}
        Log.d(TAG,"ob的距离:" + mR);
        mr = mR / 2;

        // 背景图绘制区域
        mExternalDestRect = new Rect((int)(centerx - mr), (int)(centery - mr),
                (int)(centerx + mr),
                (int)(centery + mr));
        //初始化: 75 75 225 225

        // 中心图绘制区域
        mInsideDestRect = new Rect((int)(centerx - mr), (int)(centery - mr), (int)(centerx + mr), (int)(centery + mr));


        // 内外的图形
        externalBD = (BitmapDrawable) getResources().getDrawable(normalexternalbackground);
        mExternalSrcRect = new Rect(0, 0, externalBD.getIntrinsicWidth(), externalBD.getIntrinsicHeight());

        insidelBD = (BitmapDrawable) getResources().getDrawable(normalinsidebackground);
        mInsideSrcRect = new Rect(0, 0, insidelBD.getIntrinsicWidth(), insidelBD.getIntrinsicHeight());

        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    externalBD = (BitmapDrawable) getResources().getDrawable(selectedexternalbackground);
                    insidelBD = (BitmapDrawable) getResources().getDrawable(selectedinsidebackground);

                    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.1f,
                            1f);
                    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.1f,
                            1f);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhX, pvhY);
                    objectAnimator.setDuration(500);
                    OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.2f);
                    objectAnimator.setInterpolator(overshootInterpolator);
                    objectAnimator.start();
                    postInvalidate();
                } else {
                    externalBD = (BitmapDrawable) getResources().getDrawable(normalexternalbackground);
                    insidelBD = (BitmapDrawable) getResources().getDrawable(normalinsidebackground);
                    postInvalidate();
                }
            }
        });
    }

    private void initPaint() {
        //绘制图形的画笔
        bmPaint = new Paint();
        bmPaint.setAntiAlias(true);//抗锯齿功能
        bmPaint.setStyle(Paint.Style.FILL); //设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d(TAG,"onLayout: $mWidth $mHeight");
        //可供位移的距离
        offsetDistanceLimit = mWidth / 6;
        centerY = ((getBottom() + getTop()) / 2);
        centerX = ((getRight() + getLeft()) / 2);
        centerx = mWidth / 2;
        centery = mHeight / 2;
        Log.d(TAG,"中心点坐标: $centerX $centerY");
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //暂时画个边框表示范围
        Paint bianKuanPaint = new Paint();
        bianKuanPaint.setAntiAlias(true);
        bianKuanPaint.setStrokeWidth(2f);
        bianKuanPaint.setStyle(Paint.Style.STROKE);
        bianKuanPaint.setColor(getResources().getColor(R.color.black));
        canvas.drawRect(0f, 0f, this.getWidth(), this.getHeight(), bianKuanPaint);


        //绘制默认状态下背景图
        Bitmap externalBM = externalBD.getBitmap();
        canvas.drawBitmap(externalBM, mExternalSrcRect, mExternalDestRect, bmPaint);


        //绘制默认状态下中心图
        Bitmap insidelBM = insidelBD.getBitmap();
        canvas.drawBitmap(insidelBM, mInsideSrcRect, mInsideDestRect, bmPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //相较于视图的XY
        float mx1 = event.getX();
        float my1 = event.getY();
        float mx2 = event.getX();
        float my2 = event.getY();  //需要减掉标题栏高度
        Log.d(TAG,"---onTouchEvent---");
        Log.d(TAG," 点击坐标：$mx1 $my1");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"ACTION_DOWN");
                //TODO 弹动一下的动画效果
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"ACTION_MOVE:" + getScaleX() + " " + getScaleY());

                //判断点击位置距离中心的距离
                float distanceToCenter = getDistanceTwoPoint(mx1, my1, centerx, centery);

                float mExternalOffesetLimit = mr / 4;

                float mInsideOffesetLimit = mr / 2;


                //如果区域在轨迹圆内则移动
                if (distanceToCenter > mExternalOffesetLimit) {
                    //如果点击位置在组件外，则获取点击位置和中心点连线上的一点(该点满足矩形在组件内)为中心作图
                    // oc/oa = od/ob
                    float od = mx1 - centerx;
                    float ob = getDistanceTwoPoint(centerx, centery, mx1, my1);
                    float oc = od / ob * mExternalOffesetLimit;
                    // ca/oa = db/ob
                    float db = centery - my1;
                    float ac = db / ob * mExternalOffesetLimit;
                    //得到ac和oc判断得出a点的位置
                    mx1 = centerx + oc;
                    my1 = centery - ac;

                    od = mx2 - centerx;
                    ob = getDistanceTwoPoint(centerx, centery, mx2, my2);
                    oc = od / ob * mInsideOffesetLimit;
                    // ca/oa = db/ob
                    db = centery - my2;
                    ac = db / ob * mInsideOffesetLimit;
                    //得到ac和oc判断得出a点的位置
                    mx2 = centerx + oc;
                    my2 = centery - ac;
                } else {
                    //获得与中点的距离，*2,如图3

                    float ab = my2 - centery;
                    float bo = mx2 - centerx;
                    Log.d(TAG,"ab:" + ab + "  bo:" + bo);
                    mx2 = centerx + 2f * bo;
                    my2 = centery + 2f * ab;
                    distanceToCenter = getDistanceTwoPoint(mx1, my1, centerx, centery);
                    if (distanceToCenter > mExternalOffesetLimit) {
                        return super.onTouchEvent(event);
                    }
                }


                int left = (int) (mx1 - mr);
                int right = (int) (mx1 + mr);
                int top = (int) (my1 - mr);
                int bottom = (int) (my1 + mr);
                //更新背景图绘制区域
                mExternalDestRect = new Rect(left, top, right, bottom);

                left = (int) (mx2 - mr);
                right = (int) (mx2 + mr);
                top = (int) (my2 - mr);
                bottom = (int) (my2 + mr);
                //更新中心图绘制区域
                mInsideDestRect = new Rect(left, top, right, bottom);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP :
                Log.d(TAG,"ACTION_UP");

                //复原背景图绘制区域
                mExternalDestRect = new Rect((int)(centerx - mr), (int)(centery - mr),
                        (int)(centerx + mr),
                        (int)(centery + mr));
                //复原中心图绘制区域
                mInsideDestRect = new Rect((int)(centerx - mr), (int)(centery - mr),
                        (int)(centerx + mr),
                        (int)(centery + mr));
                postInvalidate();
                break;
            }

        Log.d(TAG,"---end---");
        return super.onTouchEvent(event);
    }

    //得到两点之间的距离
    private float getDistanceTwoPoint(float x1, float y1, float x2,float y2) {
        return (float) Math.sqrt((double) (Math.pow((x1 - x2), 2) +
                Math.pow((y1 - y2), 2)));
    }
}
