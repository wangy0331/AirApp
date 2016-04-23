package com.sohu110.airapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.cache.CacheCenter;

/**
 * Created by Aaron on 2016/3/28.
 */
public class MemberFragment extends Fragment {
    // view
    private View view;

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);
        mTextView = (TextView) view.findViewById(R.id.phone_text_haoma);

        if (CacheCenter.getCurrentUser() != null) {
            mTextView.setText(CacheCenter.getCurrentUser().getUserid());
        }

        return view;
    }


}
