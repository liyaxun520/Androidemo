package com.decard.pie_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.decard.piechart.ColorRandom;
import com.decard.piechart.ColorUtils;
import com.decard.piechart.PieChartView;
import com.decard.piechart.PieModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private PieChartView pieChart;
    private List<PieModel>	pieModelList	= new ArrayList<>();
    private RecyclerView rv;
    private MyAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.id_pie_chart);

        List<Integer> color = ColorUtils.getColor(7);
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                pieModelList.add(new PieModel("大蔬菜0",color.get(i), 20));
            } else if( i< 6){
                pieModelList.add(new PieModel("菠菜"+i,color.get(i), 10));
            }else{
                pieModelList.add(new PieModel("其他",color.get(i), 30));
            }
        }
        pieChart.setData(pieModelList);
        pieChart.startAnima();

//        //TODO 新增暂无交易时界面显示 重新创建柱状图数据
//        int color = ColorUtils.getDefaultColor();
//        List<PieModel> list = new ArrayList<>();
//        list.add(new PieModel("暂无交易",color, 100));
//        pieChart.setData(list);
//        pieChart.startAnima();

        rv = ((RecyclerView) findViewById(R.id.recyclerView));

        adapter = new MyAdapter(pieModelList);
        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(View view, int position) {
        PieModel pieModel = pieModelList.get(position);
        pieChart.setData(pieModelList);
        pieChart.invalidate();
        for (PieModel model : pieModelList) {
            if(model.eatName.equals(pieModel.eatName)){
                model.selected = true;
                pieChart.setPieChartPercent(model.percent);
            }else{
                model.selected = false;
            }
        }
        adapter.notifyDataSetChanged();

    }
}
