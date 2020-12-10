package com.decard.cup.sn.hy.medical.view_demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.decard.cup.sn.hy.medical.view_demo.R;

public class CustomTitleView extends FrameLayout implements View.OnClickListener {

    private ImageView btnBack;
    private TextView tvTitle;
    private  OnLeftBackClickListener onLeftBackClickListener;
    private ImageView btnMenu;

    public void setOnLeftBackClickListener(OnLeftBackClickListener onLeftBackClickListener) {
        this.onLeftBackClickListener = onLeftBackClickListener;
    }

    public CustomTitleView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_customtitle, this);
        btnBack = ((ImageView) view.findViewById(R.id.btnBack));
        tvTitle = ((TextView) view.findViewById(R.id.tvTitle));
        btnMenu = ((ImageView) view.findViewById(R.id.btnMenu));
        btnMenu.setVisibility(GONE);
        btnBack.setOnClickListener(this);
    }

    public void setTitle(String title){
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void showMenu(boolean show){
        if (show) {
            btnMenu.setVisibility(VISIBLE);
        }else{
            btnMenu.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.btnBack == id){
            if (onLeftBackClickListener != null) {
                onLeftBackClickListener.onBack();
            }
        }
    }

    public interface OnLeftBackClickListener{
        void onBack();
    }
}
