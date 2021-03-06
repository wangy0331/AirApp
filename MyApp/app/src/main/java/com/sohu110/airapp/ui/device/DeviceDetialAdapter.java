package com.sohu110.airapp.ui.device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * 适配器
 * Created by Aaron on 2016/4/14.
 */
public class DeviceDetialAdapter extends FragmentPagerAdapter {

    String[] names = new String[] {"实时监测", "实时曲线", "周边地图", "设备信息", "设备日志"};

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;

    private String deviceId;

    public DeviceDetialAdapter(FragmentManager fm, String id) {
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
                fragment1 = Fragment1.newInstance(deviceId);
                return fragment1;
            case 1:
                fragment2 = Fragment2.newInstance(deviceId);
                return fragment2;
            case 2:
                fragment3 = Fragment3.newInstance(deviceId);
                return fragment3;
            case 3:
                fragment4 = Fragment4.newInstance(deviceId);
                return fragment4;
            case 4:
                fragment5 = Fragment5.newInstance(deviceId);
                return fragment5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return names.length;
    }
}
