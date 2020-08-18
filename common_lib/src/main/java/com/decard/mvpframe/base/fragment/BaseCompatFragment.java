package com.decard.mvpframe.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.decard.mvpframe.R;
import com.decard.mvpframe.baseapp.BaseApplication;
import com.decard.mvpframe.utils.AppUtils;
import com.decard.mvpframe.widgets.WaitPorgressDialog;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lison on 2017/9/26.
 * <p>
 */

public abstract class BaseCompatFragment extends SupportFragment {

    protected String TAG;
    protected Context mContext;
    protected Activity mActivity;
    protected BaseApplication mApplication;
    protected WaitPorgressDialog mWaitPorgressDialog;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        getBundle(getArguments());
        initData();
        initUI(view, savedInstanceState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @LayoutRes
    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {
    }

    /**
     * 初始化UI
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {
        mWaitPorgressDialog = new WaitPorgressDialog(mActivity);
        mContext = AppUtils.getContext();
        mApplication = (BaseApplication) mActivity.getApplication();
    }

    /**
     * 处理回退事件
     *
     * @return true 事件已消费
     * <p>
     * false 事件向上传递
     */
    @Override
    public boolean onBackPressedSupport() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                //如果当前存在fragment>1，当前fragment出栈
                pop();
            } else {
                //已经退栈到root fragment，交由Activity处理回退事件
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitPorgressDialog.isShowing()) {
            mWaitPorgressDialog.dismiss();
        }

        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

}
