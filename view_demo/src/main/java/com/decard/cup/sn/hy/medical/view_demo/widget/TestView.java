package com.decard.cup.sn.hy.medical.view_demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.decard.cup.sn.hy.medical.view_demo.R;

public class TestView extends View {

    private String tvText;
    private int size;
    private Context context;
    private Paint paint;

    /**
     * 在java代码里new的时候会用到
     * @param context
     */

    public TestView(Context context) {
        this(context,null);
    }

    /**
     * 在xml布局文件中使用时自动调用
     * @param context
     */
    public TestView(Context context,  @androidx.annotation.Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    /**
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */

    public TestView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        tvText = typedArray.getString(R.styleable.TestView_text);
        size = typedArray.getIndex(R.styleable.TestView_textAttr);
        Log.d("TAG","获取到自定义属性值  "+ tvText +"    "+ size);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100,100,400,500,paint);
        canvas.drawText(tvText,100,100,paint);
    }
}
