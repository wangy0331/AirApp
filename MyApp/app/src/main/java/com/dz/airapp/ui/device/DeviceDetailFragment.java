package com.dz.airapp.ui.device;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dz.airapp.R;

/**
 * 设备详情fragment
 * Created by Aaron on 2016/4/14.
 */
public class DeviceDetailFragment extends Fragment {

//    private NewsAdapter adapter;

    String type = "";

    public DeviceDetailFragment(String string) {
        type = string;
        Log.d("TAG_TYPE", string);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_detail_fragment, container, false);


//        adapter = new NewsAdapter(getActivity());

        return view;
    }

}
