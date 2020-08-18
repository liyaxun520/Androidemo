package com.decard.cup.sn.hy.medical.view_demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontaiView extends ViewGroup {

    private Scroller scroller;
    private VelocityTracker tracker;

    private int currentIndex = 0;
    private int childWidth = 0;
    private int lastX = 0;
    private int lastY = 0;

    public HorizontaiView(Context context) {
        super(context);
        init(context);
    }

    public HorizontaiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontaiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        scroller = new Scroller(context);
        tracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高的测量模式以及测量值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量所有子View
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        if (getChildCount() == 0) {
            setMeasuredDimension(0,0);
        }else if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            View childOne = getChildAt(0);
            int childWidth  = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            //View的宽度=单个子View宽度*子View个数，View的高度=子View高度
            setMeasuredDimension(getChildCount() * childWidth,childHeight);
        }else if(widthMode == MeasureSpec.AT_MOST){
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            //View的宽度=单个子View宽度*子View个数，View的高度=xml当中设置的高度
            setMeasuredDimension(getChildCount() * childWidth, heightSize);
        }else if(heightMode == MeasureSpec.AT_MOST){
            View childOne = getChildAt(0);
            int childHeight = childOne.getMeasuredHeight();
            //View的宽度=xml当中设置的宽度，View的高度=子View高度
            setMeasuredDimension(widthSize, childHeight);
        }

    }
    /**
     * 3、接下来重写`onLayout`方法，对各个子View设置位置。
     * 设置子View位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if(child.getVisibility() !=View.GONE){
                childWidth = child.getMeasuredWidth();
                child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
                left += childWidth;
            }

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercrpt = false;
        //记录当前点击的坐标
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int delatY = x - lastY;
                //当X轴移动的绝对值大于Y轴移动的绝对值时，表示用户进行了横向滑动，对事件进行拦截
                if (Math.abs(deltaX) > Math.abs(delatY)) {
                    intercrpt = true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                return super.onInterceptTouchEvent(ev);
        }
        lastX = x;
        lastY = y;
        //intercrpt = true表示对事件进行拦截
        return intercrpt;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tracker.addMovement(event);
        //获取事件坐标(x,y)
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int delatY = y - lastY;
                //scrollBy方法将对我们当前View的位置进行偏移
                scrollBy(-deltaX, 0);
                break;
            //当产生ACTION_UP事件时，也就是我们抬起手指
            case MotionEvent.ACTION_UP:
                //getScrollX()为在X轴方向发生的便宜，childWidth * currentIndex表示当前View在滑动开始之前的X坐标
                //distance存储的就是此次滑动的距离
                int distance = getScrollX() - childWidth * currentIndex;
                //当本次滑动距离>View宽度的1/2时，切换View
                if (Math.abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        currentIndex++;
                    } else {
                        currentIndex--;
                    }
                } else {
                    //获取X轴加速度，units为单位，默认为像素，这里为每秒1000个像素点
                    tracker.computeCurrentVelocity(1000);
                    float xV = tracker.getXVelocity();
                    //当X轴加速度>50时，也就是产生了快速滑动，也会切换View
                    if (Math.abs(xV) > 50) {
                        if (xV < 0) {
                            currentIndex++;
                        } else {
                            currentIndex--;
                        }
                    }
                }
                //对currentIndex做出限制其范围为【0，getChildCount() - 1】
                currentIndex = currentIndex < 0 ? 0 : currentIndex > getChildCount() - 1 ? getChildCount() - 1 : currentIndex;
                //滑动到下一个View
                smoothScrollTo(currentIndex * childWidth, 0);
                tracker.clear();
                break;

        }
        lastX = x;
        lastY = y;
        return true;
    }

    private void smoothScrollTo(int destX, int destY) {
        //startScroll方法将产生一系列偏移量，从（getScrollX(), getScrollY()），destX - getScrollX()和destY - getScrollY()为移动的距离
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        //invalidate方法会重绘View，也就是调用View的onDraw方法，而onDraw又会调用computeScroll()方法
        invalidate();
    }

    //重写computeScroll方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        //当scroller.computeScrollOffset()=true时表示滑动没有结束
        if (scroller.computeScrollOffset()) {
            //调用scrollTo方法进行滑动，滑动到scroller当中计算到的滑动位置
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            //没有滑动结束，继续刷新View
            postInvalidate();
        }
    }

}
