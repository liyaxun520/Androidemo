package com.decard.wifidirect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.decard.mvpframe.base.fragment.BaseCompatFragment;
import com.decard.wifidirect.R;

public class MainFragment extends BaseCompatFragment implements View.OnClickListener {



    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initData() {
        super.initData();


    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                start(SendDataFragment.newInstance());
                break;
            case R.id.button2:
                start(RecvDataFragment.newInstance());
                break;
        }
    }
}
