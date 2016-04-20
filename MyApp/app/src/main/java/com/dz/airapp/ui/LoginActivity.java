package com.dz.airapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dz.airapp.R;
import com.dz.airapp.bean.Result;
import com.dz.airapp.bean.User;
import com.dz.airapp.cache.CacheCenter;
import com.dz.airapp.kit.StringKit;
import com.dz.airapp.service.ServiceCenter;
import com.dz.airapp.widget.LibToast;
import com.dz.airapp.widget.LoadProcessDialog;

/**
 * 登录
 * Created by Aaron on 2016/3/28.
 */
public class LoginActivity extends BaseActivity {

    private Button findPassword;
    private EditText mLoginPhone;
    private EditText mLoginPassword;
    private Button mLoginBtn;
    private CheckBox mRemember;

    private String userPhone;
    private String userPwd;

    //是否记住登录（0否，1是）
    private String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login));
        initView();
    }

    private void initView() {
        findPassword = (Button) findViewById(R.id.find_password);
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginBtn = (Button) findViewById(R.id.login_member);
        mRemember = (CheckBox) findViewById(R.id.remember);

        mRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemember.isChecked()) {
//                    LibToast.show("记住");
                    type = "1";
                } else {
//                    LibToast.show("忘记");
                    type = "0";
                }
            }
        });

        getBtnRight().setImageResource(R.drawable.btn_zhuce);
        getBtnRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userPhone = mLoginPhone.getText().toString().trim();
                userPwd = mLoginPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(userPhone) && userPhone.length() == 11) {
                    new Logintask(userPhone, userPwd).execute();
                } else {
                    LibToast.show(LoginActivity.this, R.string.invalid_phone_num);
                }
            }
        });
    }


    class Logintask extends AsyncTask<Void, Void, Result<User>> {

        String mUsername, mPassword;
        LoadProcessDialog mLoadDialog;

        public Logintask(String phone, String password) {
            mUsername = phone;
            mPassword = password;
            mLoadDialog = new LoadProcessDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<User> doInBackground(Void... params) {
            try {
                return ServiceCenter.login(mUsername, mPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<User> userResult) {
            super.onPostExecute(userResult);
            mLoadDialog.dismiss();
            if (userResult != null) {
                if (userResult.isSuceed()) {
                    Toast.makeText(LoginActivity.this, userResult.getMessage(), Toast.LENGTH_SHORT).show();

                    User user = new User();
                    user.setDatabaseid("AirApp");
                    user.setUserid(mUsername);
                    user.setType(type);

                    CacheCenter.cacheCurrentUser(user);


                    CacheCenter.getCurrentUser();



                    // 关闭页面，显示个人中心
                    LoginActivity.this.finish();
                } else if (StringKit.isNotEmpty(userResult.getMessage())) {
                    Toast.makeText(LoginActivity.this, userResult.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(LoginActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 打开登录页面
     *
     * @param mContext
     */
    public static void open(Activity mContext) {
        if (mContext != null) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            mContext.overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_stay);
        }
    }
}
