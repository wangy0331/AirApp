package com.dz.airapp.ui.device;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.dz.airapp.R;
import com.dz.airapp.kit.DpKit;
import com.dz.airapp.ui.BaseActivity;
import com.dz.airapp.widget.PagerSlidingTabStrip;

/**
 * 设备详情
 * Created by Aaron on 2016/4/13.
 */
public class DeviceDetailActivity extends BaseActivity {

    ViewPager mViewPager;
    DeviceDetialAdapter mAdapter;
    PagerSlidingTabStrip mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        setTitle(R.string.device_detial);

        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.device_viewpager_container);


        mAdapter = new DeviceDetialAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);

        mIndicator = (PagerSlidingTabStrip)findViewById(R.id.device_indicator);
        mIndicator.setTextSize(DpKit.dip2px(this, 16));
        mIndicator.setViewPager(mViewPager);

    }
}
