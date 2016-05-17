package com.sohu110.airapp.ui.device;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.sohu110.airapp.R;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.widget.LibConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 设备改造
 * Created by Aaron on 2016/5/9.
 */
public class DevicerReformActivity extends BaseActivity {

//    private EditText

    private ImageButton pushPhoto;

    private static String path;// sd路径
    private Bitmap head;// 头像Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_reform);
        setTitle(R.string.sbgz_btn);


        initView();

    }

    private void initView() {

        pushPhoto = (ImageButton) findViewById(R.id.push_photo);

        pushPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }


    /**
     * 调用照片
     */

    private String[] items = new String[] { "选择本地图片", "拍照" };
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "gaizao.jpg";

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
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "gaizao.jpg")));
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
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == DevicerReformActivity.this.RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/gaizao.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        File file = setPicToView(head);// 保存在SD卡中
                        // mImageView.setImageBitmap(head);// 用ImageView显示出来
                        // Drawable drawable = new BitmapDrawable(head);

                        // mImageView.setImageDrawable(drawable);

                        Log.e("iamge", file.toString());

//                        new ChangeImageTask(user.getToken(), file).execute();
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private File setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return null;
        }
        FileOutputStream b = null;

        // 获取sd卡路径
        path = LibConfig.getCacheImagePath();
        Log.e("aaron", path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = path + "gaizao.jpg";// 图片名字

        Log.e("图片路径", fileName);
        try {
            b = new FileOutputStream(fileName);
            String abc = "";
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            return new File(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

}
