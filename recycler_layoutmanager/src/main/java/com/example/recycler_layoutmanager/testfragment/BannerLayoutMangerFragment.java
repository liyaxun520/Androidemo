package com.example.recycler_layoutmanager.testfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recycler_layoutmanager.R;
import com.example.recycler_layoutmanager.base.banner.BannerLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class BannerLayoutMangerFragment extends Fragment {

    private RecyclerView horizentalRecv;
    private RecyclerView verticalRecv;
    private List<String> mdatas = new ArrayList<>();
    private int mLastSelectPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","BannerLayoutMangerFragment");
        View view = inflater.inflate(R.layout.fragment_bannerlayout, container, false);
        horizentalRecv = view.findViewById(R.id.horizental);
        verticalRecv = view.findViewById(R.id.vertical);
        initData();

        BannerLayoutManager bannerLayoutManager = new BannerLayoutManager(getActivity(), horizentalRecv,10, OrientationHelper.HORIZONTAL);
        bannerLayoutManager.setTimeSmooth(100f);
        horizentalRecv.setLayoutManager(bannerLayoutManager);
        HorizentalAdapter horizentalAdapter = new HorizentalAdapter();
        horizentalRecv.setAdapter(horizentalAdapter);
        bannerLayoutManager.setOnSelectedViewListener(new BannerLayoutManager.OnSelectedViewListener() {
            @Override
            public void onSelectedView(View view, int position) {
                changeUI(position);
            }
        });
        changeUI(0);


        BannerLayoutManager bannerLayoutManager2 = new BannerLayoutManager(getActivity(), verticalRecv, 10,OrientationHelper.VERTICAL);
        bannerLayoutManager2.setTimeSmooth(100f);
        verticalRecv.setLayoutManager(bannerLayoutManager2);
        HorizentalAdapter horizentalAdapter2 = new HorizentalAdapter();
        verticalRecv.setAdapter(horizentalAdapter2);

        return view;
    }
    private void changeUI(int position){
        if (position != mLastSelectPosition) {
            mLastSelectPosition = position;
        }

    }
    private void initData() {

        for (int i = 0; i < 10; i++) {
            mdatas.add("条目  "+i);
        }
    }

    class HorizentalAdapter extends RecyclerView.Adapter<HorizentalAdapter.MyViweHolder>{

        @NonNull
        @Override
        public HorizentalAdapter.MyViweHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_horizental,viewGroup, false);
            return new MyViweHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HorizentalAdapter.MyViweHolder viweHolder, int i) {
            viweHolder.textView.setText(mdatas.get(i%10));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
        class MyViweHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public MyViweHolder(@NonNull View itemView) {
                super(itemView);
                textView =  itemView.findViewById(R.id.tv_banner);
            }
        }
    }
}
