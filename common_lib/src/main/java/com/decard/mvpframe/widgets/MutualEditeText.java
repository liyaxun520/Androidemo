package com.decard.mvpframe.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.decard.mvpframe.R;

/**
 * 一键清除的EditText
 *
 * @author yuhao
 *
 */
public class MutualEditeText extends AppCompatEditText implements TextWatcher,View.OnFocusChangeListener {

	private Drawable mClearDrawable;// 一键删除的按钮
	private int colorAccent;// 获得主题的颜色
	private int clearColor = 0;
	private boolean hasFocus;// 控件是否有焦点

	public MutualEditeText(Context context) {
		this(context, null);
	}

	public MutualEditeText(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.editTextStyle);
	}


	public MutualEditeText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		colorAccent = getColorAccent(context);
		TypedArray a2 = context.obtainStyledAttributes(attrs,R.styleable.MutualEditeText);
		clearColor = a2.getColor(R.styleable.MutualEditeText_clearColor, colorAccent);
		a2.recycle();
		initClearDrawable(context);
	}

	public int getColorAccent(Context context){
		TypedValue typedValue = new  TypedValue();
		context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
		return typedValue.data;
	}


	private void initClearDrawable(Context context) {
		mClearDrawable = getCompoundDrawables()[2];// 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
		if (mClearDrawable == null) {
			mClearDrawable = getResources().getDrawable(R.drawable.ic_clear);
		}
		final Drawable wrappedDrawable = DrawableCompat.wrap(mClearDrawable);
		DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
		mClearDrawable = wrappedDrawable;
//		if (clearColor > 0) {
//			DrawableCompat.setTint(wrappedDrawable, colorAccent);
//		} else {
//			DrawableCompat.setTint(wrappedDrawable,getCurrentHintTextColor());
//		}
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicHeight(), mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);
		// 设置焦点改变的监听
		setOnFocusChangeListener(this);
		// 设置输入框里面内容发生改变的监听
		addTextChangedListener(this);

	}

	/**
	 * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
	 *
	 * @param visible
	 */
	private void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("=============", "onTouchEvent: ");
		if (mClearDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			// 判断触摸点是否在水平范围内
			boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight()))
					&& (x < (getWidth() - getPaddingRight()));
			// 获取删除图标的边界，返回一个Rect对象
			Rect rect = mClearDrawable.getBounds();
			// 获取删除图标的高度
			int height = rect.height();
			int y = (int) event.getY();
			// 计算图标底部到控件底部的距离
			int distance = (getHeight() - height) / 2;
			// 判断触摸点是否在竖直范围内(可能会有点误差)
			// 触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
			boolean isInnerHeight = (y > distance) && (y < (distance + height));
			if (isInnerHeight && isInnerWidth) {
				this.setText("");
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 当输入框里面内容发生变化的时候回调的方法
	 */
	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		if (hasFocus) {
			setClearIconVisible(text.length() > 0);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}
//
//	@Override
//	public void onFocusChange(View v, boolean hasFocus) {
//
//
//	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		this.hasFocus = focused;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		try {
			this.hasFocus = hasFocus;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
