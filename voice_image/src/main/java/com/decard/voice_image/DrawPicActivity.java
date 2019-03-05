package com.decard.voice_image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DrawPicActivity extends AppCompatActivity {

    private DrawPictureTemplate pictureTemplate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawpic);
        pictureTemplate = new DrawPictureTemplate(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pictureTemplate.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictureTemplate.stopDraw();
    }
}
