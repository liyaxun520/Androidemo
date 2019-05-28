//package com.example.vertical_view.widget
//
//import android.animation.Animator
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.view.View
//import android.widget.ImageView
//
//class VerticalDrawerAnimator{
//
//    private var mAnimatorSet: AnimatorSet? = null
//
//    private lateinit var mViewAnimFactory: ViewAnimFactory
//
//    private lateinit var mAnimators: MutableList<Animator>
//
//    private var mIndicatorView: ImageView? = null
//
//    private var mListener: OnAnimationListener? = null
//
//    fun VerticalDrawerAnimator(view: View) {
//
//        mAnimators = ArrayList()
//
//        mViewAnimFactory = ViewAnimFactory()
//        mViewAnimFactory.view = view
//
//        mAnimatorSet = AnimatorSet()
//        mAnimatorSet!!.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {
//                if (mListener != null) {
//                    mListener!!.onStart(animation)
//                }
//            }
//
//            override fun onAnimationEnd(animation: Animator) {
//                if (mListener != null) {
//                    mListener!!.onEnd(animation)
//                }
//            }
//
//            override fun onAnimationCancel(animation: Animator) {
//
//            }
//
//            override fun onAnimationRepeat(animation: Animator) {
//
//            }
//        })
//    }
//
//    fun setListener(listener: OnAnimationListener) {
//        mListener = listener
//    }
//
//    fun setIndicatorView(indicatorView: ImageView) {
//        mIndicatorView = indicatorView
//    }
//
//    fun addResizeAnimator(startHeight: Int, endHeight: Int, duration: Int): VerticalDrawerAnimator {
//
//        val resizeAnimator = ObjectAnimator.ofInt(mViewAnimFactory, "height", startHeight, endHeight)
//        resizeAnimator.duration = duration.toLong()
//
//        mAnimators.add(resizeAnimator)
//
//        return this
//    }
//
//    fun addRotationAnimator(startAngle: Float, endAngle: Float, duration: Int): VerticalDrawerAnimator {
//
//        val rotationAnimator = ObjectAnimator.ofFloat(mIndicatorView, "rotation", startAngle, endAngle)
//        rotationAnimator.duration = duration.toLong()
//
//        mAnimators.add(rotationAnimator)
//
//        return this
//    }
//
//    fun start(): VerticalDrawerAnimator {
//
//        if (mAnimatorSet != null) {
//            mAnimatorSet!!.cancel()
//            mAnimatorSet!!.playSequentially(mAnimators)
//            mAnimatorSet!!.start()
//        }
//
//        mAnimators.clear()
//
//        return this
//    }
//
//    /**
//     * 简单的回调监听动画完成事件
//     */
//    interface OnAnimationListener {
//        /**
//         * 动画开始
//         *
//         * @param animation Animator
//         */
//        fun onStart(animation: Animator)
//
//        /**
//         * 动画结束
//         *
//         * @param animation Animator
//         */
//        fun onEnd(animation: Animator)
//    }
//}