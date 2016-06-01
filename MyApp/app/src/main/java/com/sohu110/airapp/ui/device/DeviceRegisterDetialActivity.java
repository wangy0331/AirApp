package com.sohu110.airapp.ui.device;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceInfo;
import com.sohu110.airapp.bean.DeviceThree;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.widget.LibToast;

import java.util.Calendar;

/**
 * 设备注册
 * Created by Aaron on 2016/5/9.
 */
public class DeviceRegisterDetialActivity extends BaseActivity {

    private TextView pp;
    private TextView gl;
    private TextView xlh;

    //客户名称
    private EditText mCustomer;
    //客户地址
    private EditText mCustomerAdd;
    //空压机品牌
    private EditText mBrand;
    //空压机编码
    private EditText mSn;
    //空压机出厂日期
    private TextView mDate;
    //紧急电话
    private EditText mEmsPhone;
    //维护电话
    private EditText mWhPhone;
    private Button mSubmitBtn;
    private Button mEditBtn;

    private DeviceThree deviceThree;

    private final static int DATE_DIALOG = 0;
    private Calendar c = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_device_info);
        setTitle(R.string.wanshan);

        Intent intent = this.getIntent();
        deviceThree = (DeviceThree) intent.getSerializableExtra("deviceThree");

        pp = (TextView) findViewById(R.id.pp);
        gl = (TextView) findViewById(R.id.gl);
        xlh = (TextView) findViewById(R.id.xlh);
        mCustomer = (EditText) findViewById(R.id.khmc);
        mCustomerAdd = (EditText) findViewById(R.id.khdz);
        mBrand = (EditText) findViewById(R.id.kyjpp);
        mSn = (EditText) findViewById(R.id.kyjbh);
        mDate = (TextView) findViewById(R.id.kyjccrq);
        mEmsPhone = (EditText) findViewById(R.id.jjdh);
        mWhPhone = (EditText) findViewById(R.id.whdh);
        mEditBtn = (Button) findViewById(R.id.edit_device_info);
        mSubmitBtn = (Button) findViewById(R.id.edit_device_info_submit);

        mEditBtn.setVisibility(View.GONE);
        mSubmitBtn.setVisibility(View.VISIBLE);

        if (deviceThree != null) {
            pp.setText(deviceThree.getBrand());
            gl.setText(deviceThree.getKw());
            xlh.setText(deviceThree.getSetNum());
        }

        /**
         * 创建日期及时间选择对话框
         */
        final Calendar c = Calendar.getInstance();
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(DeviceRegisterDetialActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        mDate.setText(DateFormat.format("yyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceInfo info = new DeviceInfo();
                info.setCustName(mCustomer.getText().toString());
                info.setCustAdd(mCustomerAdd.getText().toString());
                info.setAirBrand(mBrand.getText().toString());
                info.setAirSn(mSn.getText().toString());
                info.setEmeTel(mEmsPhone.getText().toString());
                info.setWhTel(mWhPhone.getText().toString());
                info.setAirOd(mDate.getText().toString());
                new DeviceEditTask(info).execute();
            }
        });

    }

    /**
     * 提交设备信息
     */
    class DeviceEditTask extends AsyncTask<Void, Void, Result<DeviceInfo>> {

        private DeviceInfo mInfo;

        public DeviceEditTask(DeviceInfo info) {
            mInfo = info;
        }

        @Override
        protected Result<DeviceInfo> doInBackground(Void... params) {
            try{
                return ServiceCenter.saveDeviceInfo(mInfo);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<DeviceInfo> result) {
            super.onPostExecute(result);
            if (result != null) {
                if (result.isSuceed()) {
                    DeviceRegisterDetialActivity.this.finish();
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(DeviceRegisterDetialActivity.this, result.getMessage());
                } else {
                    LibToast.show(DeviceRegisterDetialActivity.this, R.string.member_detail_failure);
                }
            } else {
                LibToast.show(DeviceRegisterDetialActivity.this, R.string.member_register_network);
            }
        }
    }

}
