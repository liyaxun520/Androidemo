package com.ndk.realmdb_demo.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.ndk.realmdb_demo.R;
import com.ndk.realmdb_demo.adapter.TimelineAdapter;
import com.ndk.realmdb_demo.anims.LandingAnimator;
import com.ndk.realmdb_demo.anims.ScaleInAnimationAdapter;
import com.ndk.realmdb_demo.app.AppApplication;
import com.ndk.realmdb_demo.base.BaseFragment;
import com.ndk.realmdb_demo.bean.TimeInfo;
import com.ndk.realmdb_demo.home.ui.EditActivity;
import com.ndk.realmdb_demo.utils.RealmOperationHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by lvr on 2017/4/8.
 */

public class TodayFragment extends BaseFragment implements TimelineAdapter.OnItemClickListener{
    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    private TimelineAdapter mAdapter;
    private List<TimeInfo> mInfos = new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today;
    }

    @Override
    protected void initView() {
        mAdapter = new TimelineAdapter(getActivity(),mInfos);
        mRvRecord.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter.setOnItemClickListener(this);
        mRvRecord.setItemAnimator(new LandingAnimator());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setFirstOnly(false);
        scaleAdapter.setDuration(500);
        mRvRecord.setAdapter(scaleAdapter);
        mRvRecord.getItemAnimator().setAddDuration(300);
        mRvRecord.getItemAnimator().setChangeDuration(300);
        mRvRecord.getItemAnimator().setMoveDuration(300);
        mRvRecord.getItemAnimator().setRemoveDuration(300);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        coverData();
    }

    /**
     * 恢复数据库中数据
     */
    private void coverData() {
        final RealmResults<TimeInfo> results = (RealmResults<TimeInfo>) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryAllAsync(TimeInfo.class);
        // TODO: 2017/4/20 开启加载提示
        results.addChangeListener(new RealmChangeListener<RealmResults<TimeInfo>>() {
            @Override
            public void onChange(RealmResults<TimeInfo> element) {
                results.removeAllChangeListeners();
                // TODO: 2017/4/20 关闭加载提示
                Toast.makeText(getActivity(), "加载数据完毕", Toast.LENGTH_SHORT).show();
                System.out.println("从数据库中查找到的条目：" + element.size());
                if (element == null) {
                    return;
                }
                mInfos.addAll(element);
                System.out.println("复制后的数据条目:" + mInfos.size());
                mAdapter.notifyDataSetChanged();
            }
        });



    }

    @Subscribe
    public void onTimeInfoEvent(final TimeInfo info) {
        if(info.isNew()){
            mInfos.add(info);
            mAdapter.notifyItemRangeInserted(mInfos.size(),1);
            RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).add(info);
            System.out.println("添加一个新数据");
        }else{
           if(info.getPosition()!=-1){
               mInfos.remove(info.getPosition());
               mInfos.add(info.getPosition(),info);
               mAdapter.notifyItemChanged(info.getPosition());
           }
        }

    }


    @Override
    public void onItemClick(TimeInfo mInfo,int position) {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("TimeInfo",mInfo);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppApplication.REALM_INSTANCE.close();
    }
}
