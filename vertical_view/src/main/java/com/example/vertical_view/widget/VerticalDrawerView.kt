//package com.example.vertical_view.widget
//
//import android.animation.Animator
//import android.content.Context
//import android.content.res.Resources
//import android.graphics.drawable.Drawable
//import android.support.annotation.NonNull
//import android.support.annotation.Nullable
//import android.util.AttributeSet
//import android.util.Log
//import android.widget.RelativeLayout
//import android.util.TypedValue.COMPLEX_UNIT_DIP
//import android.util.TypedValue.applyDimension
////import com.decard.mvpframe.utils.SnackbarUtils.addView
//import com.example.vertical_view.widget.VerticalDrawerView.PositionEnum
////import android.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
//import android.util.TypedValue
//import android.view.View
//import android.widget.ImageView
//
//
//class VerticalDrawerView@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
//    :RelativeLayout(context, attrs,defStyleAttr){
//
//    enum class PositionEnum{
//        // left
//        LEFT,
//        // right
//        RIGHT
//    }
//
//    /**
//     * 是否为展开状态，默认为false
//     */
//    private var mIsExpanded = false
//    /**
//     * 默认初始化高度
//     */
//    private var mInitialHeight: Int = 0
//    /**
//     * 展开时的高度
//     */
//    private var mExpandedHeight: Int = 0
//    /**
//     * 收起时的高度
//     */
//    private var mCollapsedHeight: Int = 0
//    /**
//     * 指示器容器
//     */
//    private var mIndicatorContainer: RelativeLayout? = null
//    /**
//     * 被折叠时显示的View
//     */
//    private var mCollapsedView: View? = null
//    /**
//     * 被展开时显示的View
//     */
//    private var mExpandedView: View? = null
//
//    /**
//     * 指示器处于左边还是右边
//     */
//    private val mPositionEnum = PositionEnum.RIGHT
//    /**
//     * 指示器Drawable
//     */
//    private var mIndicatorDrawable: Drawable? = null
//    /**
//     * Animator
//     */
//    private var mDrawerAnimator: VerticalDrawerAnimator? = null
//
//    private var mExpandedParams: RelativeLayout.LayoutParams? = null
//    private var mCollapsedParams: RelativeLayout.LayoutParams? = null
//    init {
//        init()
//    }
//
//    private fun init() {
//        Log.d("init","init方法已执行")
//        mDrawerAnimator = VerticalDrawerAnimator(this)
//        mDrawerAnimator!!.setListener(object : VerticalDrawerAnimator.OnAnimationListener {
//            override fun onStart(animation: Animator) {
//                beforeAnimation()
//            }
//
//            override fun onEnd(animation: Animator) {
//                afterAnimation()
//            }
//        })
//        mIndicatorContainer = RelativeLayout(context)
//        mIndicatorContainer!!.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View) {
//                onTrigger()
//            }
//        })
//    }
//
//    /**
//     * 设置展开收起的状态
//     */
//    fun setExpanded(expanded: Boolean) {
//        mIsExpanded = expanded
//    }
//
//    /**
//     * 设置指示器图标
//     */
//    fun setIndicator(drawable: Drawable, width: Int, height: Int) {
//        mIndicatorDrawable = drawable
//        mIndicatorDrawable!!.setBounds(0, 0, width, height)
//    }
//
//    /**
//     * 设置收起时对外展示的View
//     *
//     * @param collapsedView   收起时对外展示的View
//     * @param collapsedHeight 外部设置的固定高度
//     */
//    fun setCollapsedView(collapsedView: View, collapsedHeight: Int) {
//        setCollapsedView(collapsedView)
//        mCollapsedHeight = collapsedHeight
//    }
//
//    /**
//     * 设置收起时对外展示的View
//     *
//     * @param collapsedView 收起时对外展示的View
//     */
//    fun setCollapsedView(collapsedView: View) {
//        mCollapsedView = collapsedView
//        mCollapsedView!!.measure(0, 0)
//    }
//
//    /**
//     * 设置展开时对外展示的View
//     *
//     * @param expandedView   收起时对外展示的View
//     * @param expandedHeight 外部设置的固定高度
//     */
//    fun setExpandedView(expandedView: View, expandedHeight: Int) {
//        setExpandedView(expandedView)
//        mExpandedHeight = expandedHeight
//    }
//
//    /**
//     * 设置展开时对外展示的View
//     *
//     * @param expandedView 收起时对外展示的View
//     */
//    fun setExpandedView(expandedView: View) {
//        mExpandedView = expandedView
//        mExpandedView!!.measure(0, 0)
//    }
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//
//        initializeExpanded()
//        initializeCollapsed()
//
//        // 设置初始状态
//        if (mIsExpanded) {
//            if (mExpandedView != null) {
//                addView(mExpandedView, mExpandedParams)
//                mInitialHeight = mExpandedHeight
//            }
//        } else {
//            if (mCollapsedView != null) {
//                addView(mCollapsedView, mCollapsedParams)
//                mInitialHeight = mCollapsedHeight
//            }
//        }
//
//        // 设置指示器
//        val indicatorView = ImageView(context)
//
//        if (mIndicatorDrawable != null) {
//            indicatorView.setImageDrawable(mIndicatorDrawable)
//        }
//
//        val indicatorParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
//        indicatorParams.addRule(RelativeLayout.CENTER_IN_PARENT)
//
//        mIndicatorContainer!!.addView(indicatorView, indicatorParams)
//
//        mDrawerAnimator!!.setIndicatorView(indicatorView)
//
//        val containerParams = RelativeLayout.LayoutParams(mInitialHeight, mInitialHeight)
//        containerParams.addRule(if (mPositionEnum === PositionEnum.RIGHT) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
//        containerParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//
//        addView(mIndicatorContainer, containerParams)
//    }
//
//    private fun initializeCollapsed() {
//        if (mCollapsedHeight == 0) {
//            // 没有设置默认收起高度
//            if (mCollapsedView != null) {
//                // 取CollapsedView的高度作为收起高度
//                mCollapsedHeight = mCollapsedView!!.getMeasuredHeight()
//            } else {
//                // 取一个默认值作为收起高度
//                mCollapsedHeight = dp2px(30f)
//            }
//        }
//
//        mCollapsedParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mCollapsedHeight)
//        mCollapsedParams!!.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
//        mCollapsedParams!!.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//    }
//
//    private fun initializeExpanded() {
//        if (mExpandedHeight == 0) {
//            // 没有设置默认展开高度
//            if (mExpandedView != null) {
//                // 取ExpandedView的高度作为展开高度
//                mExpandedHeight = mExpandedView!!.getMeasuredHeight()
//            } else {
//                // 取一个默认值作为展开高度
//                mExpandedHeight = dp2px(100f)
//            }
//        }
//
//        mExpandedParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mExpandedHeight)
//        mExpandedParams!!.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
//        mExpandedParams!!.addRule(RelativeLayout.ALIGN_PARENT_TOP)
//    }
//
//    private fun onTrigger() {
//
//        if (mIsExpanded) {
//            // 收起动画
//            mDrawerAnimator!!.addResizeAnimator(mExpandedHeight, mCollapsedHeight, 500)
//                    .addRotationAnimator(180f, 0f, 150)
//                    .start()
//
//        } else {
//            // 展开动画
//            mDrawerAnimator!!.addResizeAnimator(mCollapsedHeight, mExpandedHeight, 500)
//                    .addRotationAnimator(0f, 180f, 150)
//                    .start()
//        }
//    }
//
//    private fun beforeAnimation() {
//
//        // 收起到展开的时候要先布局，不然会一片空白
//        if (!mIsExpanded) {
//
//            removeView(mCollapsedView)
//            addView(mExpandedView, mExpandedParams)
//        }
//
//        mIndicatorContainer!!.isEnabled = false
//    }
//
//    private fun afterAnimation() {
//
//        if (mIsExpanded) {
//
//            removeView(mExpandedView)
//            addView(mCollapsedView, mCollapsedParams)
//        }
//
//        mIsExpanded = !mIsExpanded
//
//        mIndicatorContainer!!.isEnabled = true
//    }
//
//    fun dp2px(dpVal: Float): Int {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dpVal, Resources.getSystem().displayMetrics).toInt()
//    }
//
//}