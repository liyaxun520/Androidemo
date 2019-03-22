package com.decard.piechart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {

    private Paint mChartPaint;                    //画扇形画笔

    private Paint mCirclePaint;                    // 中心圆画笔

    private Paint mTextPaint;                    // 中心文字 画笔

    private RectF mRectF;

    private int padding ;                        //圆半径

    private List<PieModel> mPieModelList;

    private float mAnimaAngle;

    private RectF mSelectedRectF = new RectF();

    private double nowPercent = 0;     //当前百分比


    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mChartPaint.setDither(true);
        mChartPaint.setStrokeWidth(50);
        mChartPaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.WHITE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#FF272418"));
        mTextPaint.setTextSize(70.0f);
        mTextPaint.setAntiAlias(true);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mTextPaint.setTypeface( font );
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPieModelList == null || mPieModelList.isEmpty()) {
            return;
        }
        for (int i = 0; i < mPieModelList.size(); i++) {
            double percent = mPieModelList.get(i).percent;

            if (percent > 0 && percent <100) {
                if (mAnimaAngle >= mPieModelList.get(i).startAngle && mAnimaAngle <= (mPieModelList.get(i).startAngle + mPieModelList.get(i).sweepAngle)) {

                    drawColor(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mAnimaAngle - mPieModelList.get(i).startAngle);

                } else if (mAnimaAngle >= (mPieModelList.get(i).startAngle + mPieModelList.get(i).sweepAngle)) {
                    drawColor(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mPieModelList.get(i).sweepAngle);
                }
                if (mPieModelList.get(i).selected) {
                    drawSelectedView(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mPieModelList.get(i).sweepAngle);
                }
            }else{
                drawColor(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mAnimaAngle - mPieModelList.get(i).startAngle);
            }
        }

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, padding, mCirclePaint);
        if(!TextUtils.isEmpty(String.valueOf(nowPercent))) {

            Paint.FontMetrics fontMetrics1 = mTextPaint.getFontMetrics();
            float top = fontMetrics1.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics1.bottom;//为基线到字体下边框的距离,即上图中的bottom

            int baseLineY = (int) (mRectF.centerY() - top/2 - bottom/2);//基线中间点的y轴计算公式

            String myPercent = myPercent(nowPercent, 100);
            Log.d("onDraw  ",myPercent+"    "+nowPercent);
            canvas.drawText(myPercent,mRectF.centerX(),baseLineY,mTextPaint);
        }
    }

    private void drawColor(Canvas canvas, int color, float startAngle, float sweepAngle) {
        mChartPaint.setColor(color);
        mChartPaint.setAlpha(255);
        canvas.drawArc(mRectF, startAngle, sweepAngle, true, mChartPaint);

    }

    public void setPieChartPercent(double percent){
        nowPercent = percent;
    }

    private void drawSelectedView(Canvas canvas, int color, float startAngle, float sweepAngle) {
        mChartPaint.setColor(color);
        mChartPaint.setAlpha(255);
        //画弧度
        /**
         * 首先来说说 RectF ,这里我们用它的四个参数的构造方法
         *float startAngle 这个参数的意义是弧线的起点的角度，下面分别是 0 和 90 的情况
         * 参数三：sweepAngle  这个参数就是所画的弧线的长度。没什么可说的
         * 这个呢代表的是是否画出半径的意思（应该是叫半径吧）
         */
        canvas.drawArc(mSelectedRectF, startAngle, sweepAngle, true, mChartPaint);
    }

    public void startAnima() {
        final ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0f, 360f);
        mValueAnimator.setDuration(3 * 1000);

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimaAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    public void setData(List<PieModel> pieModelList) {
        this.mPieModelList = pieModelList;

        int count = 0;
        for (int i = 0; i < mPieModelList.size(); i++) {
            count +=mPieModelList.get(i).count;
            Log.e("setData",mPieModelList.get(i).count+"");
        }

        Log.d("setData count  ",String.valueOf(count));

        for (int i = 0; i < mPieModelList.size(); i++) {
            PieModel model = mPieModelList.get(i);
            if (i == 0) {
                model.startAngle = 0;   //第一块起始角度
            } else {
                model.startAngle = mPieModelList.get(i - 1).startAngle + mPieModelList.get(i - 1).sweepAngle;
            }

            //计算百分比
            model.percent = model.count *100 / count;

            Log.d("setData  ",model.percent+"");
            model.sweepAngle = (float) ((model.percent * 360) /100);
        }
    }

    public static String myPercent(double y, double z) {
        String baifenbi = "";// 接受百分比的值
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        if(y >0) {
            double fen = baiy / baiz;
            // nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
            DecimalFormat df1 = new DecimalFormat("##.00%");
            // ##.00%
            // 百分比格式，后面不足2位的用0补齐
            // baifenbi=nf.format(fen);
            baifenbi = df1.format(fen);
        }else {
            baifenbi = "0.00%";
        }
        System.out.println(baifenbi);
        return baifenbi;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        padding = w / 5;
        mRectF = new RectF(padding, padding, w - padding, w - padding);   //
        mSelectedRectF.set(mRectF);

        Log.d("onSizeChanged ",mRectF.left+"   "+mRectF.top+"    "+mRectF.right+"    "+mRectF.bottom);
        //通过（dx，dy）插入矩形。如果 dx 是正的，则两边向内移动，使矩形变窄。如果 dx 是负的，则两边向外移动，使矩形更宽。这同样适用于 dy 和顶部和底部。
        mSelectedRectF.inset(-10, -10);
    }

}
