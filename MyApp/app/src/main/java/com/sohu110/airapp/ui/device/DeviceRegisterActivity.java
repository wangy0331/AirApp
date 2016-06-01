package com.sohu110.airapp.ui.device;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceThree;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;
import com.zxing.activity.CaptureActivity;

/**
 * 设备注册
 * Created by Aaron on 2016/4/8.
 */
public class DeviceRegisterActivity extends BaseActivity {

    private WebView mWebView;
    private Button mBtn;

    public static final int SCAN_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_register);
        setTitle(R.string.device);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.start);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceRegisterActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SCAN_CODE);
            }
        });
        mWebView = (WebView) findViewById(R.id.gif);

        WebSettings webSettings = mWebView.getSettings();

        webSettings= mWebView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        mWebView.loadUrl("file:///android_asset/gif.html");





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCAN_CODE:
//                TextView scanResult = (TextView) findViewById(R.id.scan_result);
                if (resultCode == RESULT_OK) {
//                    String result = data.getStringExtra("scan_result");
//                    scanResult.setText(result);
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");

                    new DeviceTask(scanResult).execute();


//                    startActivity(new Intent(DeviceRegisterActivity.this, DeviceRegisterDetialActivity.class));

                    Log.e("扫描", "扫描成功");
                } else if (resultCode == RESULT_CANCELED) {
//                    scanResult.setText("没有扫描出结果");
                    Log.e("扫描", "扫描失败");
                }
                break;
            default:
                break;
        }
    }

    class DeviceTask extends  AsyncTask<Void, Void, Result<DeviceThree>> {

        private String scan;
        LoadProcessDialog mLoadDialog;

        public DeviceTask(String scanResult) {
            scan = scanResult;
            mLoadDialog = new LoadProcessDialog(DeviceRegisterActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<DeviceThree> doInBackground(Void... params) {
            try {
                return ServiceCenter.deviceRegister(scan);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Result<DeviceThree> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if(result != null) {
                if(result.isSuceed()) {
                    LibToast.show(DeviceRegisterActivity.this, R.string.member_register_success);
                    //关闭本页面，显示登录页面
                    Intent intent = new Intent(DeviceRegisterActivity.this, DeviceRegisterDetialActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("deviceThree", result.getData());
                    intent.putExtras(bundle);
                    showDialog(intent);
//                    DeviceRegisterActivity.this.finish();
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(DeviceRegisterActivity.this, result.getMessage());
                } else {
                    LibToast.show(DeviceRegisterActivity.this, R.string.member_register_failure);
                }
            } else {
                LibToast.show(DeviceRegisterActivity.this, R.string.member_register_network);
            }
        }

    }


    private void showDialog(final Intent intent) {
        new AlertDialog.Builder(DeviceRegisterActivity.this).setTitle(R.string.tishi)
                .setMessage(R.string.wanshan_info).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startActivity(intent);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }
}
