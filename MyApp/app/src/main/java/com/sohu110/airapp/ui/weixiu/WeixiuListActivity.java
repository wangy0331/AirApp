package com.sohu110.airapp.ui.weixiu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * Created by Aaron on 2016/4/23.
 */
public class WeixiuListActivity extends BaseActivity{

    private EditText mEditText;
    private RadioGroup mRadioGroup;
    private RadioButton customerBtn;
    private RadioButton equipmentBtn;
    private RadioButton areaBtn;
    private Button searchBtn;
    private TextView mText;
    //列表
    private ListView mListView;
    //适配器
    private WeixiuAdapter mAdapter;
    //默认客户
    private String condition = "cust";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        setTitle(R.string.weixiu_list);


        initView();
        initData();
    }

    private void initData() {
        mAdapter = new WeixiuAdapter(WeixiuListActivity.this);
        if (LibApplication.getInstance().isNetworkConnected()) {
//            new DeviceListTask(mEditText.getText().toString(), condition).execute();
        } else {
            LibToast.show(WeixiuListActivity.this, R.string.not_network);
        }
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.device_content);
        searchBtn = (Button) findViewById(R.id.device_search);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        customerBtn = (RadioButton) findViewById(R.id.device_kehu);
        equipmentBtn = (RadioButton) findViewById(R.id.device_shebeihao);
        areaBtn = (RadioButton) findViewById(R.id.device_quyu);
        mListView = (ListView) findViewById(R.id.device_list_view);
        mText = (TextView) findViewById(R.id.wbyj_text);

        mEditText.setHint(R.string.search_cust);
        customerBtn.setTextColor(getResources().getColor(R.color.blue));
        equipmentBtn.setTextColor(getResources().getColor(R.color.grey));
        areaBtn.setTextColor(getResources().getColor(R.color.grey));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_refresh);

        mListView.setVisibility(View.GONE);
        mText.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if (LibApplication.getInstance().isNetworkConnected()) {
//                    new DeviceListTask(mEditText.getText().toString(), condition).execute();
//                } else {
//                    LibToast.show(WeixiuListActivity.this, R.string.not_network);
//                }
                mSwipeRefreshLayout.setRefreshing(true);
                mListView.setVisibility(View.GONE);
                mText.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == customerBtn.getId()) {
                    condition = "cust";
                    mEditText.setHint(R.string.search_cust);
                    customerBtn.setTextColor(getResources().getColor(R.color.blue));
                    equipmentBtn.setTextColor(getResources().getColor(R.color.grey));
                    areaBtn.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == equipmentBtn.getId()) {
                    condition = "setno";
                    mEditText.setHint(R.string.search_hit);
                    customerBtn.setTextColor(getResources().getColor(R.color.grey));
                    equipmentBtn.setTextColor(getResources().getColor(R.color.blue));
                    areaBtn.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == areaBtn.getId()) {
                    condition = "area";
                    mEditText.setHint(R.string.search_area);
                    customerBtn.setTextColor(getResources().getColor(R.color.grey));
                    equipmentBtn.setTextColor(getResources().getColor(R.color.grey));
                    areaBtn.setTextColor(getResources().getColor(R.color.blue));
                }

                mListView.setVisibility(View.GONE);
                mText.setVisibility(View.VISIBLE);
//                if (LibApplication.getInstance().isNetworkConnected()) {
//                    new DeviceListTask(mEditText.getText().toString(), condition).execute();
//                } else {
//                    LibToast.show(WeixiuListActivity.this, R.string.not_network);
//                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mEditText.getText().toString();
                if (LibApplication.getInstance().isNetworkConnected()) {
//                    new DeviceListTask(searchText, condition).execute();
                    mListView.setVisibility(View.GONE);
                    mText.setVisibility(View.VISIBLE);
                } else {
                    LibToast.show(WeixiuListActivity.this, R.string.not_network);
                }
            }
        });

//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    Device device = (Device) parent.getItemAtPosition(position);
//                    Intent intent = new Intent(WeixiuListActivity.this,
//                            DeviceDetailActivity.class);
//                    intent.putExtra(Const.EXTRA_DEVICE, device);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Logger.e("", "", e);
//                }
//            }
//        });

    }


    class DeviceListTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        String mCondition,mContent;
        LoadProcessDialog mLoadDialog;

        public DeviceListTask(String content, String condition) {
            mContent = content;
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(WeixiuListActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.deviceList(mCondition, mContent);
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
//                    if (mListView.getAdapter() == null) {
//                        mListView.setAdapter(mAdapter);
//                    }

                    mAdapter.clear();
                    if (result.getData() != null) {
                        mAdapter.addAll(result.getData());
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(WeixiuListActivity.this, R.string.device_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(WeixiuListActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
