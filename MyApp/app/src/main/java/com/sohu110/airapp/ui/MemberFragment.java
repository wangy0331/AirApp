package com.sohu110.airapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohu110.airapp.R;

/**
 * Created by Aaron on 2016/3/28.
 */
public class MemberFragment extends Fragment {
    // view
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);
        return view;
    }
}
