package com.sohu110.airapp.ui.device;

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
 * 设备日志
 * Created by Aaron on 2016/7/3.
 */
public class Fragment5 extends Fragment  {

    private static String GUID = "guid";

    //列表
    private ListView mListView;
    //适配器
    private DeviceLogItemAdapter mAdapter;

    private String guid;

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

    //是否退出fragment了
    private boolean isCancel = false;

    public static Fragment5 newInstance(String guid) {
        Fragment5 fragment = new Fragment5();
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
    public void onStop() {
        super.onStop();
        isCancel = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.device_log_fragment,null );
        mListView = (ListView) view.findViewById(R.id.device_log_list);


        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_log);

        sday =  (RadioButton) view.findViewById(R.id.zhou_log);
        moon =  (RadioButton) view.findViewById(R.id.yue_log);
        halfyear =  (RadioButton) view.findViewById(R.id.bannian_log);
        year =  (RadioButton) view.findViewById(R.id.nian_log);

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

        mAdapter = new DeviceLogItemAdapter(getActivity());
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
                return ServiceCenter.getDeviceLog(jiqiSn,mCondition);
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
                    if (!isCancel) {
                        Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (!isCancel) {
                    Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
