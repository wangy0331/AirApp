package com.sohu110.airapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.device.DeviceDetailActivity;
import com.sohu110.airapp.ui.guanzhu.GuanzhuAdapter;
import com.sohu110.airapp.utils.Const;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * 关注列表
 * Created by Aaron on 2016/3/28.
 */
public class CustomerFragment extends Fragment{

    // view
    private View view;

//    private EditText mEditText;
//    private RadioGroup mRadioGroup;
//    private RadioButton customerBtn;
//    private RadioButton equipmentBtn;
//    private RadioButton areaBtn;
//    private Button searchBtn;
    //列表
    private ListView mListView;
    //适配器
    private GuanzhuAdapter mAdapter;
    //默认客户
    private String condition = "setno";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_soucang_list, container, false);

//        mEditText = (EditText) view.findViewById(R.id.shou_device_content);
//        searchBtn = (Button) view.findViewById(R.id.shou_device_search);
//        mRadioGroup = (RadioGroup) view.findViewById(R.id.shou_radio_group);
//        customerBtn = (RadioButton) view.findViewById(R.id.shou_device_kehu);
//        equipmentBtn = (RadioButton) view.findViewById(R.id.shou_device_shebeihao);
//        areaBtn = (RadioButton) view.findViewById(R.id.shou_device_quyu);
        mListView = (ListView) view.findViewById(R.id.shou_device_list_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.shou_list_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (LibApplication.getInstance().isNetworkConnected()) {
                    new DeviceListTask().execute();
                } else {
                    LibToast.show(getActivity(), R.string.not_network);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Device device = (Device) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(),
                            DeviceDetailActivity.class);
                    intent.putExtra(Const.EXTRA_DEVICE, device);
                    startActivity(intent);
                } catch (Exception e) {
                    Logger.e("", "", e);
                }
            }
        });
//        mEditText.setHint(R.string.search_cust);

        initData();

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
////        Log.e("", mEditText.getText().toString());
//        initData();
//
//
//
////        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                if (checkedId == customerBtn.getId()) {//客户
////                    condition = "cust";
////                    mEditText.setHint(R.string.search_cust);
////                } else if (checkedId == equipmentBtn.getId()) {//设备号
////                    condition = "setno";
////                    mEditText.setHint(R.string.search_hit);
////                } else if (checkedId == areaBtn.getId()) {//地区
////                    condition = "area";
////                    mEditText.setHint(R.string.search_area);
////                }
////
////                new DeviceListTask(mEditText.getText().toString(), condition).execute();
////            }
////        });
//
////        searchBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String searchText = mEditText.getText().toString();
////                new DeviceListTask(searchText, condition).execute();
////            }
////        });
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    Device device = (Device) parent.getItemAtPosition(position);
//                    Intent intent = new Intent(getActivity(),
//                            DeviceDetailActivity.class);
//                    intent.putExtra(Const.EXTRA_DEVICE, device);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Logger.e("", "", e);
//                }
//            }
//        });
//    }

    private void initData() {
        mAdapter = new GuanzhuAdapter(getActivity());
        if (LibApplication.getInstance().isNetworkConnected()) {
            new DeviceListTask().execute();
        } else {
            LibToast.show(getActivity(), R.string.not_network);
        }
    }


    class DeviceListTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        LoadProcessDialog mLoadDialog;

        public  DeviceListTask() {
//            mContent = content;
//            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.guanzhuList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<Device>> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
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
                    Toast.makeText(getActivity(), R.string.device_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
