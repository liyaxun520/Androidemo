package com.decard.bottomsheetdialog.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.decard.bottomsheetdialog.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class BottomSheetActivity extends AppCompatActivity {
    BottomSheetBehavior behavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
    }
    public void doclick(View v)
    {
        switch (v.getId()) {
            case R.id.button0:
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.button1:
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
                View view = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.show();
                break;
            case R.id.button2:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                new FullSheetDialogFragment().show(fragmentTransaction, "TAG");
                break;
        }
    }
}
