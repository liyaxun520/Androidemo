package com.example.recycler_layoutmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.recycler_layoutmanager.base.NoPreloadViewPager;
import com.example.recycler_layoutmanager.testfragment.BannerLayoutMangerFragment;
import com.example.recycler_layoutmanager.testfragment.EchelonMangerFragment;
import com.example.recycler_layoutmanager.testfragment.PickerMangerFragment;
import com.example.recycler_layoutmanager.testfragment.SKidMangerFragment;
import com.example.recycler_layoutmanager.testfragment.ScaleMangerFragment;
import com.example.recycler_layoutmanager.testfragment.SlidMangerFragment;
import com.example.recycler_layoutmanager.testfragment.ViewPagerMangerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private  Fragment  currentFragment=new Fragment();
    BannerLayoutMangerFragment bannerLayoutMangerFragment = new BannerLayoutMangerFragment();
    EchelonMangerFragment echelonMangerFragment = new EchelonMangerFragment();
    PickerMangerFragment pickerMangerFragment = new PickerMangerFragment();
    ScaleMangerFragment scaleMangerFragment = new ScaleMangerFragment();
    SKidMangerFragment sKidMangerFragment = new SKidMangerFragment();
    SlidMangerFragment slidMangerFragment = new SlidMangerFragment();
    ViewPagerMangerFragment viewPagerMangerFragment = new ViewPagerMangerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = ((ViewPager) findViewById(R.id.viewPager));
        radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));
        init();
        initFragments();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"选中位置   "+position);
                if(position == 0){
                    radioGroup.check(R.id.banner);
                }else if(position == 1){
                    radioGroup.check(R.id.echelon);
                }else if(position == 2){
                    radioGroup.check(R.id.picker);
                }else if(position == 3){
                    radioGroup.check(R.id.scale);
                }else if(position == 4){
                    radioGroup.check(R.id.skidright);
                }else if(position == 5){
                    radioGroup.check(R.id.slide);
                }else if(position == 6){
                    radioGroup.check(R.id.viewpager);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.banner:
                        Log.d(TAG,checkedId+"  banner  "+R.id.banner);
                        viewPager.setCurrentItem(0,true);
//                        switchFragment(bannerLayoutMangerFragment);
                        break;
                    case R.id.echelon:
                        viewPager.setCurrentItem(1,true);
                        Log.d(TAG,checkedId+"  echelon  "+R.id.echelon);
//                        switchFragment(echelonMangerFragment);
                        break;
                    case R.id.picker:
                        viewPager.setCurrentItem(2,true);
                        Log.d(TAG,checkedId+"  picker  "+R.id.picker);
//                        switchFragment(pickerMangerFragment);
                        break;
                    case R.id.scale:
                        viewPager.setCurrentItem(3,true);
                        Log.d(TAG,checkedId+"  scale  "+R.id.scale);
//                        switchFragment(scaleMangerFragment);
                        break;
                    case R.id.skidright:
                        viewPager.setCurrentItem(4,true);
                        Log.d(TAG,checkedId+"  skidright  "+R.id.skidright);
//                        switchFragment(sKidMangerFragment);
                        break;
                    case R.id.slide:
                        viewPager.setCurrentItem(5,true);
                        Log.d(TAG,checkedId+"  slide  "+R.id.slide);
//                        switchFragment(slidMangerFragment);
                        break;
                    case R.id.viewpager:
                        viewPager.setCurrentItem(6,true);
                        Log.d(TAG,checkedId+"   viewpager "+R.id.viewpager);
//                        switchFragment(viewPagerMangerFragment);
                        break;
                }
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void init() {
        radioGroup.check(R.id.banner);
    }

    private void initFragments() {

        fragments.add(bannerLayoutMangerFragment);
        fragments.add(echelonMangerFragment);
        fragments.add(pickerMangerFragment);
        fragments.add(scaleMangerFragment);
        fragments.add(sKidMangerFragment);
        fragments.add(slidMangerFragment);
        fragments.add(viewPagerMangerFragment);

    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.viewPager, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }
}


