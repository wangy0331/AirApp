package com.sohu110.airapp.ui.weixiu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * 维保详情
 * Created by Aaron on 2016/5/29.
 */
public class WeixiuDetailAdapter extends FragmentPagerAdapter {

    String[] names = new String[] {"预警详情", "历史记录"};

    private WeixiuFragment fragment;
    private WeixiuFragment1 fragment1;

    private String deviceId;

    public WeixiuDetailAdapter(FragmentManager fm, String id) {
        super(fm);
        deviceId = id;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = WeixiuFragment.newInstance(deviceId);
                return fragment;
            case 1:
                fragment1 = WeixiuFragment1.newInstance(deviceId);
                return fragment1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return names.length;
    }
}
