package com.example.recycler_layoutmanager.base.scale;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycler_layoutmanager.R;


/**
 * Created by jun on 17/11/27.
 */

public class ScaleLayoutManager extends RecyclerView.LayoutManager {

    public static final String TAG = ScaleLayoutManager.class.getSimpleName();
    private static final float MIN_SCALE = 0.7f;

    //RecyclerView高度
    private int mHeight;
    //最大item顶部或底部的高度
    private int mSurplusHeight;
    //item交叉的高度
    private int mInterval;
    //item高度
    private int mItemHeight;
    //item宽度
    private int mItemWidth;

    //当前滚动偏移量
    private int mOffsetY;
    //最大滚动偏移量
    private int mMaxOffsetY;

    private int mTopBackgroundRes = R.color.colorPrimaryDark;
    private int mBottomBackgroundRes = R.color.colorAccent;

    private boolean isScrollEnabled;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        setScrollEnabled(false);
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }




    public void setScrollEnabled(boolean flag) {
        isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            mOffsetY = 0;
            return;
        }

        mHeight = getHeight();
        if (getChildCount() == 0) {
            View scrap = recycler.getViewForPosition(0);
            measureChildWithMargins(scrap, 0, 0);
            mItemHeight = getDecoratedMeasuredHeight(scrap);
            mItemWidth = getDecoratedMeasuredWidth(scrap);
            mSurplusHeight = (mHeight - mItemHeight) / 2;

            //最大item顶部区域可显示数量
            int mSpaceCount = mSurplusHeight / mItemHeight + 1;
            mInterval = (mSpaceCount * mItemHeight - mSurplusHeight) / mSpaceCount;

            mMaxOffsetY = getItemCount() * (mItemHeight - mInterval) - mHeight + mInterval;

            Log.d("","mHeight = " + mHeight + "  mItemHeight = " + mItemHeight + "  mItemWidth = " + mItemWidth +
                    "\nmSurplusHeight = " + mSurplusHeight + "  mInterval = " + mInterval);
        }

        detachAndScrapAttachedViews(recycler);
        fill(recycler);
    }

    /**
     * item层次排布方式:
     * 1,Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
     * 通过itemView{@link View#setElevation(float)}方式
     * <p>
     * 2,Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
     * 通过添加itemView的顺序来实现,
     */
    private void fill(RecyclerView.Recycler recycler) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            detachAndScrapAttachedViews(recycler);
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                final View view = getChildAt(i);
                final int top = view.getTop();
                if (top < mInterval - mItemHeight || top > mHeight) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }

        final int itemCount = getItemCount();
        int start = mOffsetY / (mItemHeight - mInterval);
        int end = (mOffsetY + mHeight) / (mItemHeight - mInterval) + 1;

        Log.d(TAG,"start = " + start + " end = " + end);

        start = start < 0 ? 0 : start;
        end = end > itemCount ? itemCount : end;

        for (int i = start; i < end; i++) {
            if (findViewByPosition(i) == null) {
                final View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                resetViewProperty(scrap);

                int targetY = i * (mItemHeight - mInterval) - mOffsetY;
                layoutScrap(scrap, targetY);

                if (targetY > mHeight / 2) {
                    addView(scrap, 0);
                } else {
                    addView(scrap);
                }
            }
        }
    }

    /**
     * 填充itemView
     *
     * @param scrap   itemView
     * @param targetY itemView Y坐标
     */
    private void layoutScrap(View scrap, int targetY) {

        layoutDecorated(scrap, 0, targetY, mItemWidth, targetY + mItemHeight);
        setItemViewProperty(scrap, targetY);
    }

    /**
     * 重置itemView属性
     *
     * @param v itemView
     */
    private void resetViewProperty(View v) {
        v.setScaleX(1f);
        v.setScaleY(1f);
    }

    /**
     * itemView缩放、背景修改
     *
     * @param itemView
     * @param targetY  itemView Y坐标
     */
    private void setItemViewProperty(View itemView, int targetY) {
        float scale = calculateScale(targetY);
        Log.d(TAG, "scale = " + scale + " sdk = " + Build.VERSION.SDK_INT);
        itemView.setScaleX(scale);
//        itemView.setScaleY(scale);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            itemView.setElevation(scale * 10);
            itemView.setElevation(scale * 5);
        }

//        if (targetY > Math.floor(mHeight / 2) - Math.floor(mItemHeight / 2)) {
//            itemView.setBackgroundResource(mBottomBackgroundRes);
//        } else {
//            itemView.setBackgroundResource(mTopBackgroundRes);
//        }
    }

    private float calculateScale(int targetY) {
        targetY = targetY > mHeight / 2 ? targetY + mItemHeight / 2 : targetY;
        float deltaY = Math.abs(targetY - mSurplusHeight);
        float scale = (1 - MIN_SCALE) * (1 - deltaY / mSurplusHeight) + MIN_SCALE;

        Log.d(TAG,"deltaY = " + deltaY + "scale = " + scale);

        return scale;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
        if (mOffsetY + dy < 0) {
            dy = -mOffsetY;
        } else if (mOffsetY + dy > mMaxOffsetY) {
            dy = mMaxOffsetY - mOffsetY;
        }
        mOffsetY += dy;

        Log.d(TAG, "scrollVerticallyBy " + " dy = " + dy + "  mOffsetY = " + mOffsetY);

        for (int i = 0; i < getChildCount(); i++) {
            final View scrap = getChildAt(i);
            final int targetY = scrap.getTop() - dy;
            layoutScrap(scrap, targetY);
        }

        fill(recycler);

        return dy;
    }

    /**
     * 距离中点的偏移量
     *
     * @return
     */
    public int getOffsetToCenter() {
        int height = mItemHeight - mInterval;
        int deltaY = mOffsetY % height;

        if (deltaY > height / 2) {
            deltaY = height - deltaY;
        } else if (deltaY > 0) {
            deltaY = -deltaY;
        }
        return deltaY;
    }

    /**
     * 设置顶部itemView背景
     *
     * @param topBackgroundRes 背景资源id
     */
    public void setTopBackgroundRes(int topBackgroundRes) {
        this.mTopBackgroundRes = topBackgroundRes;
        requestLayout();
    }

    /**
     * 设置底部itemView背景
     *
     * @param bottomBackgroundRes 背景资源id
     */
    public void setBottomBackgroundRes(int bottomBackgroundRes) {
        this.mBottomBackgroundRes = bottomBackgroundRes;
        requestLayout();
    }
}
