package com.sohu110.airapp.ui.weibao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohu110.airapp.R;

/**
 * 维保详情
 * Created by Aaron on 2016/5/29.
 */
public class WeibaoFragment extends Fragment {

    private static String GUID = "guid";

    private String guid;

    public static WeibaoFragment newInstance(String guid) {
        WeibaoFragment fragment = new WeibaoFragment();
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

        View view = inflater.inflate(R.layout.fragment_weibao,null );


        return view;
    }
}
