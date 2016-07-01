package com.sohu110.airapp.ui.weibao;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.kit.DpKit;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.utils.Const;
import com.sohu110.airapp.widget.PagerSlidingTabStrip;

/**
 * 维保
 * Created by Aaron on 2016/5/29.
 */
public class WeibaoDetailActivity extends BaseActivity {
    ViewPager mViewPager;
    WeibaoDetailAdapter mAdapter;
    PagerSlidingTabStrip mIndicator;


    //device
    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        setTitle(R.string.wbxq);

        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.device_viewpager_container);

        if (getIntent().hasExtra(Const.EXTRA_DEVICE)) {
            mDevice = (Device) getIntent().getSerializableExtra(Const.EXTRA_DEVICE);
        }

        mAdapter = new WeibaoDetailAdapter(getSupportFragmentManager(), mDevice.getJiqiSn());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mIndicator = (PagerSlidingTabStrip)findViewById(R.id.device_indicator);
        mIndicator.setTextSize(DpKit.dip2px(this, 16));
        mIndicator.setViewPager(mViewPager);

    }

}
