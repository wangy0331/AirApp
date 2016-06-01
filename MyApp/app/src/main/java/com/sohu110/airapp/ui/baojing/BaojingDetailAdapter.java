package com.sohu110.airapp.ui.baojing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 报警详情
 * Created by Aaron on 2016/5/29.
 */
public class BaojingDetailAdapter extends FragmentPagerAdapter {

    String[] names = new String[] {"报警详情", "历史记录"};

    private BaojingFragment fragment;
    private BaojingFragment1 fragment1;

    private String deviceId;


    public BaojingDetailAdapter(FragmentManager fm, String id) {
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
                fragment = BaojingFragment.newInstance(deviceId);
                return fragment;
            case 1:
                fragment1 = BaojingFragment1.newInstance(deviceId);
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
