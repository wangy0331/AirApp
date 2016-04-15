package com.dz.airapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dz.airapp.bean.Result;
import com.dz.airapp.kit.StringKit;
import com.dz.airapp.service.ServiceCenter;
import com.dz.airapp.widget.LibToast;
import com.dz.airapp.widget.LoadProcessDialog;

import com.dz.airapp.R;

/**
 * 注册
 * Created by Aaron on 2016/4/1.
 */
public class RegisterActivity extends BaseActivity {

    private EditText codeView;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private TextView mCodeBtn;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_register);
        setTitle(getResources().getString(R.string.register));
        initView();
    }

    private void initView() {
        codeView = (EditText) findViewById(R.id.code_text);
        mPhone = (EditText) findViewById(R.id.phone_text);
        mPassword = (EditText) findViewById(R.id.password_text);
        mPasswordAgain = (EditText) findViewById(R.id.password_again);
        mCodeBtn = (TextView) findViewById(R.id.codeBtn);
        mRegister = (Button) findViewById(R.id.register);

        mCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startCountDownTimer();
//                enableCaptchaButton(false);
//
                String registerPhone = mPhone.getText().toString().trim();

                if (StringKit.isEmpty(registerPhone)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_phone_for_reset_password);
                    return;
                }
                new CodeTask(registerPhone).execute();
            }
        });

        enableRegisterButton(false);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //手机
                String memberPhone = mPhone.getText().toString().trim();
                //密码
                String memberPassword = mPassword.getText().toString().trim();
                String memberPwdAgain = mPasswordAgain.getText().toString().trim();
                //验证码
                String memberCode = codeView.getText().toString().trim();


                //手机
                if (StringKit.isEmpty(memberPhone)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_phone_for_reset_password);
                    return;
                }

                //密码
                if (StringKit.isEmpty(memberPassword)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_pwd_for_reset_password);
                    return;
                } else if (memberPassword.length() < 5) {
                    LibToast.show(RegisterActivity.this, R.string.hint_pwd_too_short);
                    return;
                }

                //重复密码
                if (StringKit.isEmpty(memberPwdAgain)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_new_pwd_for_reset_password);
                    return;
                } else if (!memberPwdAgain.equals(memberPassword)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_pwd_not_match);
                    return;
                }

                //验证码
                if (StringKit.isEmpty(memberCode)) {
                    LibToast.show(RegisterActivity.this, R.string.hint_code_for_reset_password);
                    return;
                }

                new RegisterTask(memberPhone, memberPassword, memberCode).execute();

            }
        });
    }


    class RegisterTask extends AsyncTask<Void, Void, Result> {

        String mPhone;
        String mPwd;
        String mCode;
        LoadProcessDialog mLoadDialog;

        public RegisterTask(String memberPhone, String memberPassword, String memberCode) {
            mPhone = memberPhone;
            mPwd = memberPassword;
            mCode = memberCode;
            mLoadDialog = new LoadProcessDialog(RegisterActivity.this);
        }

        @Override
           protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result doInBackground(Void... params) {
            try {
                return ServiceCenter.register(mPhone, mPwd, mCode);
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
                    LibToast.show(RegisterActivity.this, R.string.member_register_success);
                    //关闭本页面，显示登录页面
                    RegisterActivity.this.finish();
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(RegisterActivity.this, result.getMessage());
                } else {
                    LibToast.show(RegisterActivity.this, R.string.member_register_failure);
                }
            } else {
                LibToast.show(RegisterActivity.this, R.string.member_register_network);
            }
        }
    }

    class CodeTask extends AsyncTask<Void, Void, Result> {
        String phone;
        LoadProcessDialog mLoadDialog;
        String func = "reg";

        public CodeTask(String registerPhone) {
            phone = registerPhone;
            mLoadDialog = new LoadProcessDialog(RegisterActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
            enableCaptchaButton(false);
        }

        @Override
        protected Result doInBackground(Void... params) {
            try {
                return ServiceCenter.getCode(phone, func);
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
                    LibToast.show(RegisterActivity.this, R.string.sms_captcha_success);
                    //启动倒计时
                    startCountDownTimer();
                    enableRegisterButton(true);
                } else if(!TextUtils.isEmpty(result.getMessage())) {
                    enableCaptchaButton(true);
                    enableRegisterButton(false);
                    mCodeBtn.setText(R.string.getCode);
                    LibToast.show(RegisterActivity.this, result.getMessage());
                }
            } else {
                enableRegisterButton(false);
                enableCaptchaButton(true);
                mCodeBtn.setText(R.string.getCode);
                LibToast.show(RegisterActivity.this, R.string.sms_captcha_failure);
            }
        }
    }


    CountDownTimer mCountDownTimer = null;

    /**
     * 启动倒计时
     */
    private void startCountDownTimer() {
        if(mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(120000,1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    String leaveSecond = "" + Math.round(millisUntilFinished/1000f);
                    mCodeBtn.setText(leaveSecond);
                    mCodeBtn.setTextColor(getResources().getColor(R.color.black));
                }

                @Override
                public void onFinish() {
                    mCodeBtn.setText(R.string.getCode);
                    enableCaptchaButton(true);
                }
            };
        }
        mCountDownTimer.start();
    }

    /**
     * 启用、禁用验证码按钮
     * @param enable
     */
    private void enableCaptchaButton(boolean enable) {
        if(enable) {
            mCodeBtn.setEnabled(true);
            mCodeBtn.setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            mCodeBtn.setEnabled(false);
            mCodeBtn.setBackgroundColor(getResources().getColor(R.color.grey));
        }
    }

    /**
     * 启用、禁用注册按钮
     * @param enable
     */
    private void enableRegisterButton(boolean enable) {
        if(enable) {
            mRegister.setEnabled(true);
        } else {
            mRegister.setEnabled(false);
        }
    }
}
