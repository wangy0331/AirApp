package com.sohu110.airapp.ui.device;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.kit.DpKit;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.utils.Const;
import com.sohu110.airapp.widget.PagerSlidingTabStrip;

/**
 * 设备详情
 * Created by Aaron on 2016/4/13.
 */
public class DeviceDetailActivity extends BaseActivity {

    ViewPager mViewPager;
    DeviceDetialAdapter mAdapter;
    PagerSlidingTabStrip mIndicator;

    //device
    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        setTitle(R.string.device_detial);

        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.device_viewpager_container);

        if (getIntent().hasExtra(Const.EXTRA_DEVICE)) {
            mDevice = (Device) getIntent().getSerializableExtra(Const.EXTRA_DEVICE);
        }

        mAdapter = new DeviceDetialAdapter(getSupportFragmentManager(), mDevice.getJiqiSn());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mIndicator = (PagerSlidingTabStrip)findViewById(R.id.device_indicator);
        mIndicator.setTextSize(DpKit.dip2px(this, 16));
        mIndicator.setViewPager(mViewPager);

    }
}
