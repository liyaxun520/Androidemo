package com.decard.pie_demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.decard.piechart.PieModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<PieModel> mDatas =null;
    public MyAdapter(List<PieModel> mdatas) {
        mDatas = mdatas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        int color = mDatas.get(position).color;
        myViewHolder.ivColor.setBackgroundColor(color);
        myViewHolder.tvEat.setText(mDatas.get(position).eatName);
        myViewHolder.tvPercent.setText((mDatas.get(position).percent) +"%");
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas!=null?mDatas.size():0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

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
}
