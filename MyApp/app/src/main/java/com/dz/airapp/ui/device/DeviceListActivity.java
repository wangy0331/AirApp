package com.dz.airapp.ui.device;

import android.os.Bundle;

import com.dz.airapp.R;
import com.dz.airapp.ui.BaseActivity;

/**
 * 设备列表
 * Created by Aaron on 2016/4/12.
 */
public class DeviceListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        setTitle(R.string.device_list);

        initView();
    }

    private void initView() {

    }
}
