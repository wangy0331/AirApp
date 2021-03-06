package com.sohu110.airapp.ui.weixiu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceLog;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.weibao.WeibaoDetailItemAdapter;

import java.util.List;

/**
 * 历史记录
 * Created by Aaron on 2016/5/29.
 */
public class WeixiuFragment1 extends Fragment {
    private static String GUID = "guid";

    private String guid;

    //列表
    private ListView mListView;
    //适配器
    private WeibaoDetailItemAdapter mAdapter;

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
    public void onResume() {
        super.onResume();
        new DeviceDetailTask(guid).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weixiu_lishi,null );

        mListView = (ListView) view.findViewById(R.id.weixiu_lishi_list);
        mAdapter = new WeibaoDetailItemAdapter(getActivity());

        return view;
    }

    /**
     * 请求历史数据
     */
    class DeviceDetailTask extends AsyncTask<Void, Void, Result<List<DeviceLog>>> {

        private String jiqiSn;

        public DeviceDetailTask(String guid) {
            jiqiSn = guid;
        }

        @Override
        protected Result<List<DeviceLog>> doInBackground(Void... params) {
            try{
                return ServiceCenter.getDetailLishiWX(jiqiSn);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<DeviceLog>> result) {
            super.onPostExecute(result);
//            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                if (result.isSuceed()) {

                    if (mListView.getAdapter() == null) {
                        mListView.setAdapter(mAdapter);
                    }

                    mAdapter.clear();
                    if (result.getData() != null) {
                        mAdapter.addAll(result.getData());
                    }
                    mAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
