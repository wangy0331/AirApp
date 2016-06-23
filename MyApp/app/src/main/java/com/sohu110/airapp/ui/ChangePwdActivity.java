package com.sohu110.airapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.cache.CacheCenter;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

/**
 * 修改密码
 * Created by Aaron on 2016/5/3.
 */
public class ChangePwdActivity extends BaseActivity{

    private EditText oldPwd;

    private EditText newPwd;

    private EditText newPwdAga;

    private Button mChangePwd;

    private TextView findPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        setTitle(R.string.change_pwd);

        oldPwd = (EditText) findViewById(R.id.login_pwd_old);

        newPwd = (EditText) findViewById(R.id.login_pwd_new);

        newPwdAga = (EditText) findViewById(R.id.login_pwd_new_confirm);

        mChangePwd = (Button) findViewById(R.id.submit_ChangePwd);

        findPwd = (TextView) findViewById(R.id.find_password_change);

        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePwdActivity.this, ResetPwdActivity.class));
            }
        });

        mChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //旧密码
                String oldPassword = oldPwd.getText().toString().trim();
                //新密码
                String memberPassword = newPwd.getText().toString().trim();
                String memberPwdAgain = newPwdAga.getText().toString().trim();

                String pwd = CacheCenter.getCurrentUser().getUserpwd();

                //旧密码
                if (StringKit.isEmpty(oldPassword)) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_old_pwd_not);
                    return;
                } else if(!pwd.equals(oldPassword)) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_old_pwd_error);
                    return;
                }

                //新密码
                if (StringKit.isEmpty(memberPassword)) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_pwd_for_reset_password);
                    return;
                } else if (memberPassword.length() < 5) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_pwd_too_short);
                    return;
                }

                //重复新密码
                if (StringKit.isEmpty(memberPwdAgain)) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_new_pwd_for_reset_password);
                    return;
                } else if (!memberPwdAgain.equals(memberPassword)) {
                    LibToast.show(ChangePwdActivity.this, R.string.hint_pwd_not_match);
                    return;
                }

                new RegisterTask(memberPassword).execute();

            }
        });

    }

    class RegisterTask extends AsyncTask<Void, Void, Result> {

        String mPwd;
        LoadProcessDialog mLoadDialog;

        public RegisterTask(String memberPassword) {
            mPwd = memberPassword;
            mLoadDialog = new LoadProcessDialog(ChangePwdActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result doInBackground(Void... params) {
            try {
                return ServiceCenter.changePwd(mPwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if(result != null) {
                if(result.isSuceed()) {
                    LibToast.show(ChangePwdActivity.this, R.string.change_pwd_success);
                    CacheCenter.removeCurrentUser();
                    //关闭本页面，显示登录页面
                    ChangePwdActivity.this.finish();
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(ChangePwdActivity.this, result.getMessage());
                } else {
                    LibToast.show(ChangePwdActivity.this, R.string.change_pwd_failure);
                }
            } else {
                LibToast.show(ChangePwdActivity.this, R.string.member_register_network);
            }
        }
    }
}
