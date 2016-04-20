package com.dz.airapp.ui.device;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dz.airapp.R;
import com.dz.airapp.bean.Device;
import com.dz.airapp.bean.Result;
import com.dz.airapp.log.Logger;
import com.dz.airapp.service.ServiceCenter;
import com.dz.airapp.ui.BaseActivity;
import com.dz.airapp.utils.Const;
import com.dz.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * 设备列表
 * Created by Aaron on 2016/4/12.
 */
public class DeviceListActivity extends BaseActivity {

    private EditText mEditText;
    private RadioGroup mRadioGroup;
    private RadioButton customerBtn;
    private RadioButton equipmentBtn;
    private RadioButton areaBtn;
    private Button searchBtn;
    //列表
    private ListView mListView;
    //适配器
    private DeviceListAdapter mAdapter;
    //默认客户
    private String condition = "cust";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        setTitle(R.string.device_list);

        initView();
        initData();
    }

    private void initData() {
        mAdapter = new DeviceListAdapter(DeviceListActivity.this);
        new DeviceListTask(mEditText.getText().toString(), condition).execute();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.device_content);
        searchBtn = (Button) findViewById(R.id.device_search);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        customerBtn = (RadioButton) findViewById(R.id.device_kehu);
        equipmentBtn = (RadioButton) findViewById(R.id.device_shebeihao);
        areaBtn = (RadioButton) findViewById(R.id.device_quyu);
        mListView = (ListView) findViewById(R.id.device_list_view);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == customerBtn.getId()) {
                    condition = "cust";
                } else if (checkedId == equipmentBtn.getId()) {
                    condition = "setno";
                } else if (checkedId == areaBtn.getId()) {
                    condition = "area";
                }

                new DeviceListTask(mEditText.getText().toString(), condition).execute();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mEditText.getText().toString();
                new DeviceListTask(searchText, condition).execute();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Device device = (Device) parent.getItemAtPosition(position);
                    Intent intent = new Intent(DeviceListActivity.this,
                            DeviceDetailActivity.class);
                    intent.putExtra(Const.EXTRA_DEVICE, device);
                    startActivity(intent);
                } catch (Exception e) {
                    Logger.e("", "", e);
                }
            }
        });

    }


    class DeviceListTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        String mCondition,mContent;
        LoadProcessDialog mLoadDialog;

        public DeviceListTask(String content, String condition) {
            mContent = content;
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(DeviceListActivity.this);
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
                    Toast.makeText(DeviceListActivity.this, R.string.device_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(DeviceListActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
