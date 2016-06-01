package com.sohu110.airapp.ui.weixiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohu110.airapp.R;

/**
 * 历史记录
 * Created by Aaron on 2016/5/29.
 */
public class WeixiuFragment1 extends Fragment {
    private static String GUID = "guid";

    private String guid;

    public static WeixiuFragment1 newInstance(String guid) {
        WeixiuFragment1 fragment = new WeixiuFragment1();
        Bundle bundle = new Bundle();
        bundle.putString(GUID, guid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取预警类型编号
        guid = this.getArguments().getString(GUID);
        Log.e("fragment", guid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weixiu_lishi,null );


        return view;
    }
}
