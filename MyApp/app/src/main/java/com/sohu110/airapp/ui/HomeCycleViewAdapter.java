package com.sohu110.airapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sohu110.airapp.HomeImage;

import java.util.List;

/**
 * Created by Aaron on 2016/3/31.
 */
public class HomeCycleViewAdapter extends FragmentPagerAdapter{

    private List<HomeImage> mHomeImgList;

    public HomeCycleViewAdapter(FragmentManager fm,
                                List<HomeImage> homeImgList) {
        super(fm);
        this.mHomeImgList = homeImgList;
    }

    public void setData(List<HomeImage> homeImgList) {
        this.mHomeImgList = homeImgList;
    }

    @Override
    public Fragment getItem(int arg0) {
//        HomeAdvertisementFragment fragment = new HomeAdvertisementFragment(
//                mHomeImgList.get(arg0));

        return null;
    }

    @Override
    public int getCount() {
        return mHomeImgList == null ? 0 : mHomeImgList.size();
    }

}
