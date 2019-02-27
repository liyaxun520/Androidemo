package com.decard.pie_demo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.decard.piechart.PieModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<PieModel> mDatas = null;
    //先声明一个int成员变量
    private int thisPosition = -1;

    public MyAdapter(List<PieModel> mdatas) {
        mDatas = mdatas;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        int color = mDatas.get(position).color;
        myViewHolder.ivColor.setBackgroundColor(color);
        myViewHolder.tvEat.setText(mDatas.get(position).eatName);
        myViewHolder.tvPercent.setText((mDatas.get(position).percent) + "%");

        if (position == getthisPosition()) {
            myViewHolder.rootView.setBackgroundColor(Color.parseColor("#FFF6F4F3"));
        } else {
            myViewHolder.rootView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setThisPosition(position);

                mOnItemClickListener.onItemClick(v, position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivColor;
        TextView tvEat;
        TextView tvPercent;
        RelativeLayout rootView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivColor = itemView.findViewById(R.id.ivColor);
            tvEat = itemView.findViewById(R.id.tvEat);
            rootView = itemView.findViewById(R.id.rootView);
            tvPercent = itemView.findViewById(R.id.percent);
        }
    }

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }


    //再定义一个int类型的返回值方法
    public int getthisPosition() {
        return thisPosition;
    }

    //其次定义一个方法用来绑定当前参数值的方法
    //此方法是在调用此适配器的地方调用的，此适配器内不会被调用到
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
}
