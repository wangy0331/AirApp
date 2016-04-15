package com.dz.airapp.ui.device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 适配器
 * Created by Aaron on 2016/4/14.
 */
public class DeviceDetialAdapter extends FragmentPagerAdapter {

    String[] names = new String[]
            { "实时监测", "预警信息", "设备管理", "周边地图"};


    private List<Fragment> fragments;

    public DeviceDetialAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        for (String str : names)
        {
            fragments.add(new DeviceDetailFragment(str));
        }
    }

    public DeviceDetialAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }
}
