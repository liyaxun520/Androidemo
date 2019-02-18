package com.decard.singlecheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.decard.singlecheck.widget.Selector;
import com.decard.singlecheck.widget.SelectorGroup;

public class MainActivity extends AppCompatActivity implements Selector.OnSelectorStateListener {

    private SelectorGroup selectorGroup = new SelectorGroup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Selector teenageSelector = findViewById(R.id.selector_10);
        Selector manSelector = findViewById(R.id.selector_20);
        Selector oldManSelector = findViewById(R.id.selector_30);

        teenageSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        manSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        oldManSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);

    }

    @Override
    public void onStateChange(Selector selector, boolean isSelect) {
        String tag = selector.getTag();
        if (isSelect) {
            Toast.makeText(this, tag + " is selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, tag + " is unselected", Toast.LENGTH_SHORT).show();
        }
    }

    public void showCheck(View view) {
        String tag = selectorGroup.getSelected().getTag();
        Log.d("选中tag---",tag);
    }
}
