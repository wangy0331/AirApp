package com.sohu110.airapp.ui.yujing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceLog;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * 预警历史记录
 * Created by Aaron on 2016/5/29.
 */
public class YujingFragment1 extends Fragment {

    private static String GUID = "guid";

    //列表
    private ListView mListView;
    //适配器
    private YujingLishiItemAdapter mAdapter;

    //年、半年、月、周
    private RadioGroup mRadioGroup;
    private RadioButton sday;
    private RadioButton moon;
    private RadioButton halfyear;
    private RadioButton year;

    //默认周
    private String condition = "week";

    //
    private boolean shuaxin = false;

    private String guid;

    public static YujingFragment1 newInstance(String guid) {
        YujingFragment1 fragment = new YujingFragment1();
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
        new DeviceDetailTask(guid,condition).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yujing_lishi,null );
        mListView = (ListView) view.findViewById(R.id.yujing_lishi_list);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_yj_log);

        sday =  (RadioButton) view.findViewById(R.id.zhou_yj_log);
        moon =  (RadioButton) view.findViewById(R.id.yue_yj_log);
        halfyear =  (RadioButton) view.findViewById(R.id.bannian_yj_log);
        year =  (RadioButton) view.findViewById(R.id.nian_yj_log);

        sday.setTextColor(getResources().getColor(R.color.blue));
        moon.setTextColor(getResources().getColor(R.color.grey));
        halfyear.setTextColor(getResources().getColor(R.color.grey));
        year.setTextColor(getResources().getColor(R.color.grey));

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == sday.getId()) {//周
                    condition = "week";
                    sday.setTextColor(getResources().getColor(R.color.blue));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == moon.getId()) {//月
                    condition = "moon";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.blue));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == halfyear.getId()) {//半年
                    condition = "halfyear";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.blue));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == year.getId()) {//年
                    condition = "year";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.blue));
                }
                shuaxin = true;
                new DeviceDetailTask(guid,condition).execute();
            }
        });

        mAdapter = new YujingLishiItemAdapter(getActivity());
        return view;
    }

    /**
     * 请求历史数据
     */
    class DeviceDetailTask extends AsyncTask<Void, Void, Result<List<DeviceLog>>> {

        private String jiqiSn;
        private String mCondition;
        LoadProcessDialog mLoadDialog;

        public DeviceDetailTask(String guid, String condition) {
            jiqiSn = guid;
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (shuaxin) {
                mLoadDialog.show();
            }
        }

        @Override
        protected Result<List<DeviceLog>> doInBackground(Void... params) {
            try{
                return ServiceCenter.getDetailLishiYJ(jiqiSn,mCondition);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<DeviceLog>> result) {
            super.onPostExecute(result);
//            mSwipeRefreshLayout.setRefreshing(false);
            mLoadDialog.dismiss();
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
