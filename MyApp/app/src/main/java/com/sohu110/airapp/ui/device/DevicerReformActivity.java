package com.sohu110.airapp.ui.device;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceReform;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.utils.UploadUtil;
import com.sohu110.airapp.widget.LibConfig;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 设备改造
 * Created by Aaron on 2016/5/9.
 */
public class DevicerReformActivity extends BaseActivity {

//    private EditText

    private ImageButton pushPhoto;

    private EditText mTemp;
    private EditText mJzf;
    private EditText mCgq;
    private EditText mAzp;
    private EditText mXsq;
    private EditText mDj;
    private EditText mDy;
    private EditText mFj;

    private String pushImagePath;

    private Button devicePhone;

    private static String path;// sd路径
    private Bitmap head;// 头像Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_reform);
        setTitle(R.string.sbgz_btn);
        getBtnRight().setImageResource(R.drawable.btn_push);
        initView();
    }

    private void initView() {

        devicePhone = (Button) findViewById(R.id.device_phone);

        devicePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneDialog();
            }
        });

        pushPhoto = (ImageButton) findViewById(R.id.push_photo);

        mTemp = (EditText) findViewById(R.id.pqwdcgq);
        mJzf = (EditText) findViewById(R.id.jzf);
        mCgq = (EditText) findViewById(R.id.ylcgq);
        mAzp = (EditText) findViewById(R.id.azp);
        mXsq = (EditText) findViewById(R.id.xsq);
        mDj = (EditText) findViewById(R.id.djx);
        mDy = (EditText) findViewById(R.id.dyx);
        mFj = (EditText) findViewById(R.id.fj);

        pushPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        getBtnRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceReform info = new DeviceReform();
                info.setPaiqiTemp(mTemp.getText().toString());
                info.setJiazaifa(mJzf.getText().toString());
                info.setPressCgq(mCgq.getText().toString());
                info.setInstallChicun(mAzp.getText().toString());
                info.setDisplayChicun(mXsq.getText().toString());
                info.setDianjiChicun(mDj.getText().toString());
                info.setPowerChicun(mDy.getText().toString());
                info.setPicUrl(pushImagePath);
                new memberSubmitTask(info).execute();
            }
        });

    }


    /**
     * 调用照片
     */

    private String[] items = new String[]{"选择本地图片", "拍照"};
    /* 头像名称 */

    private void showDialog() {

        new AlertDialog.Builder(DevicerReformActivity.this).setTitle("上传照片").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    // 从相册里面取照片
                    case 0:
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1, 1);
                        break;
                    // 调用相机拍照
                    case 1:
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent2, 2);// 采用ForResult打开
                        break;
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == DevicerReformActivity.this.RESULT_OK) {

                    if (data != null) {
                        Uri uri = data.getData();
                        if (!StringKit.isEmpty(uri.getAuthority())) {
                            Cursor cursor = getContentResolver().query(uri,
                                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                            if (null == cursor) {
                                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            cursor.moveToFirst();
                            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                            cursor.close();
                        } else {
                            path = uri.getPath();
                        }
                        String imagePath = path.substring(0, path.lastIndexOf("/") + 1);
                        String imageName = path.substring(path.lastIndexOf("/") + 1, path.length());

                        new PushImageTask(imagePath, imageName).execute();
                    } else {
                        Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }

                break;
            case 2:
                if (resultCode == DevicerReformActivity.this.RESULT_OK) {

                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        Log.i("TestFile",
                                "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//                    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

                    FileOutputStream b = null;
                    //为什么不能直接保存在系统相册位置呢
                    path = LibConfig.getCacheImagePath();
                    Log.e("aaron", path);

                    File file = new File(path);
                    file.mkdirs();// 创建文件夹
                    String fileName = path + name;

                    try {
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    new PushImageTask(LibConfig.getCacheImagePath(), name).execute();

                }

                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class PushImageTask extends AsyncTask<Void, Void, String> {

        private String imagePath;
        private String imageName;

        public PushImageTask(String path, String imageFileName) {
            imagePath = path;
            imageName = imageFileName;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return UploadUtil.ftpUpload(imagePath, imageName);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.e("FTP_PATH", result);
                pushImagePath = result;
                LibToast.show(DevicerReformActivity.this, "长传成功");
            } else {
                pushImagePath = "";
                LibToast.show(DevicerReformActivity.this, "长传失败");
            }
        }
    }


    class memberSubmitTask extends AsyncTask<Void, Void, Result<DeviceReform>> {

        DeviceReform mInfo = new DeviceReform();

        LoadProcessDialog mLoadDialog;

        public memberSubmitTask(DeviceReform info) {
            mInfo = info;
            mLoadDialog = new LoadProcessDialog(DevicerReformActivity.this);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<DeviceReform> doInBackground(Void... params) {
            try {
                return ServiceCenter.submitDevice(mInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<DeviceReform> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if(result != null) {
                if(result.isSuceed()) {

                    LibToast.show(DevicerReformActivity.this, R.string.member_edit_success);
                    DevicerReformActivity.this.finish();

                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(DevicerReformActivity.this, result.getMessage());
                } else {
                    LibToast.show(DevicerReformActivity.this, R.string.member_detail_failure);
                }
            } else {
                LibToast.show(DevicerReformActivity.this, R.string.member_register_network);
            }
        }
    }


    public void showPhoneDialog() {
        new AlertDialog.Builder(DevicerReformActivity.this).setTitle(R.string.callPhone)
                .setMessage(R.string.service_phone).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intentPhone = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" + getString(R.string.service_phone)));
                        if (ActivityCompat.checkSelfPermission(DevicerReformActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intentPhone);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }
}
