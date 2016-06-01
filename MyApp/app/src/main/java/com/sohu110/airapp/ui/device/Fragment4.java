package com.sohu110.airapp.ui.device;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceInfo;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LibToast;

import java.util.Calendar;

/**
 * 设备信息
 * Created by Aaron on 2016/5/22.
 */
public class Fragment4 extends Fragment {

    private static String GUID = "guid";

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

    private String guid;

    public static Fragment4 newInstance(String guid) {
        Fragment4 fragment = new Fragment4();
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

        new DeviceInfoTask(guid).execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device_info, null);
        pp = (TextView) view.findViewById(R.id.pp);
        gl = (TextView) view.findViewById(R.id.gl);
        xlh = (TextView) view.findViewById(R.id.xlh);
        mCustomer = (EditText) view.findViewById(R.id.khmc);
        mCustomerAdd = (EditText) view.findViewById(R.id.khdz);
        mBrand = (EditText) view.findViewById(R.id.kyjpp);
        mSn = (EditText) view.findViewById(R.id.kyjbh);
        mDate = (TextView) view.findViewById(R.id.kyjccrq);
        mEmsPhone = (EditText) view.findViewById(R.id.jjdh);
        mWhPhone = (EditText) view.findViewById(R.id.whdh);
        mEditBtn = (Button) view.findViewById(R.id.edit_device_info);
        mSubmitBtn = (Button) view.findViewById(R.id.edit_device_info_submit);


        mCustomer.setEnabled(false);
        mCustomerAdd.setEnabled(false);
        mBrand.setEnabled(false);
        mSn.setEnabled(false);
        mDate.setEnabled(false);
        mEmsPhone.setEnabled(false);
        mWhPhone.setEnabled(false);


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceInfo info = new DeviceInfo();
                info.setJiqiSn(guid);
                info.setCustName(mCustomer.getText().toString());
                info.setCustAdd(mCustomerAdd.getText().toString());
                info.setAirBrand(mBrand.getText().toString());
                info.setAirSn(mSn.getText().toString());
                info.setEmeTel(mEmsPhone.getText().toString());
                info.setWhTel(mWhPhone.getText().toString());
                Log.e("shijian", mDate.getText().toString());
                info.setAirOd(mDate.getText().toString());
                new DeviceEditTask(info).execute();
            }
        });

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomer.setEnabled(true);
                mCustomerAdd.setEnabled(true);
                mBrand.setEnabled(true);
                mSn.setEnabled(true);
                mDate.setEnabled(true);
                mEmsPhone.setEnabled(true);
                mWhPhone.setEnabled(true);
                mEditBtn.setVisibility(View.GONE);
                mSubmitBtn.setVisibility(View.VISIBLE);
            }
        });


        /**
         * 创建日期及时间选择对话框
         */
        final Calendar c = Calendar.getInstance();
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        mDate.setText(DateFormat.format("yyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        return view;
    }

    /**
     * 查询设备信息
     */
    class DeviceInfoTask extends AsyncTask<Void, Void, Result<DeviceInfo>>{

        private String jiqiSn;

        public DeviceInfoTask(String guid) {
            jiqiSn = guid;
        }

        @Override
        protected Result<DeviceInfo> doInBackground(Void... params) {
            try{
                return ServiceCenter.getDeviceInfo(jiqiSn);
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
                    pp.setText(result.getData().getJiqiBrand());
                    gl.setText(result.getData().getJiqiKw());
                    xlh.setText(result.getData().getJiqiSn());
                    mCustomer.setText(result.getData().getCustName());
                    mCustomerAdd.setText(result.getData().getCustAdd());
                    mBrand.setText(result.getData().getAirBrand());
                    mSn.setText(result.getData().getAirSn());
                    mDate.setText(result.getData().getAirOd());
                    mEmsPhone.setText(result.getData().getEmeTel());
                    mWhPhone.setText(result.getData().getWhTel());
                    mSubmitBtn.setVisibility(View.GONE);
                    mEditBtn.setVisibility(View.VISIBLE);
                }
            } else {
                pp.setText("");
                gl.setText("");
                xlh.setText("");
                mCustomer.setText("");
                mCustomerAdd.setText("");
                mBrand.setText("");
                mSn.setText("");
                mDate.setText("");
                mEmsPhone.setText("");
                mWhPhone.setText("");
                mSubmitBtn.setVisibility(View.GONE);
                mEditBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 提交设备信息
     */
    class DeviceEditTask extends AsyncTask<Void, Void, Result<DeviceInfo>>{

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
                    mCustomer.setEnabled(false);
                    mCustomerAdd.setEnabled(false);
                    mBrand.setEnabled(false);
                    mSn.setEnabled(false);
                    mDate.setEnabled(false);
                    mEmsPhone.setEnabled(false);
                    mWhPhone.setEnabled(false);
                    mSubmitBtn.setVisibility(View.GONE);
                    mEditBtn.setVisibility(View.VISIBLE);
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(getActivity(), result.getMessage());
                } else {
                    LibToast.show(getActivity(), R.string.member_detail_failure);
                }
            } else {
                LibToast.show(getActivity(), R.string.member_register_network);
            }
        }
    }

}
