package com.sohu110.airapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import com.sohu110.airapp.R;

/**
 * 重置密码
 * Created by Aaron on 2016/4/1.
 */
public class ResetPwdActivity extends BaseActivity {

    private EditText mResetPhone;
    private EditText mResetPwd;
    private EditText mResetPwdAgain;
    private EditText mResetCode;
    private Button mResetSubmit;
    private TextView mCodeSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        setTitle(getResources().getString(R.string.reset_pwd));

        initView();
    }

    private void initView() {
        mResetPhone = (EditText) findViewById(R.id.reset_phone);
        mResetPwd = (EditText) findViewById(R.id.reset_new_pwd);
        mResetPwdAgain = (EditText) findViewById(R.id.reset_pwd_again);
        mResetCode = (EditText) findViewById(R.id.reset_code);
        mResetSubmit = (Button) findViewById(R.id.reset_submit);
        mCodeSend = (TextView) findViewById(R.id.reset_code_send);

        mCodeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerPhone = mResetPhone.getText().toString().trim();

                if (StringKit.isEmpty(registerPhone)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_phone_for_reset_password);
                    return;
                }
                new CodeTask(registerPhone).execute();
            }
        });

        enableRegisterButton(false);

        mResetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //手机
                String memberPhone = mResetPhone.getText().toString().trim();
                //密码
                String memberPassword = mResetPwd.getText().toString().trim();
                String memberPwdAgain = mResetPwdAgain.getText().toString().trim();
                //验证码
                String memberCode = mResetCode.getText().toString().trim();


                //手机
                if (StringKit.isEmpty(memberPhone)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_phone_for_reset_password);
                    return;
                }

                //密码
                if (StringKit.isEmpty(memberPassword)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_pwd_for_reset_password);
                    return;
                } else if (memberPassword.length() < 5) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_pwd_too_short);
                    return;
                }

                //重复密码
                if (StringKit.isEmpty(memberPwdAgain)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_new_pwd_for_reset_password);
                    return;
                } else if (!memberPwdAgain.equals(memberPassword)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_pwd_not_match);
                    return;
                }

                //验证码
                if (StringKit.isEmpty(memberCode)) {
                    LibToast.show(ResetPwdActivity.this, R.string.hint_code_for_reset_password);
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
            mLoadDialog = new LoadProcessDialog(ResetPwdActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result doInBackground(Void... params) {
            try {
                return ServiceCenter.reset(mPhone, mPwd, mCode);
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
                    LibToast.show(ResetPwdActivity.this, R.string.member_reset_success);
                    //关闭本页面，显示登录页面
                    ResetPwdActivity.this.finish();
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(ResetPwdActivity.this, result.getMessage());
                } else {
                    LibToast.show(ResetPwdActivity.this, R.string.member_reset_failure);
                }
            } else {
                LibToast.show(ResetPwdActivity.this, R.string.member_register_network);
            }
        }
    }

    class CodeTask extends AsyncTask<Void, Void, Result> {
        String phone;
        LoadProcessDialog mLoadDialog;
        String func = "reset";

        public CodeTask(String registerPhone) {
            phone = registerPhone;
            mLoadDialog = new LoadProcessDialog(ResetPwdActivity.this);
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
                    LibToast.show(ResetPwdActivity.this, R.string.sms_captcha_success);
                    //启动倒计时
                    startCountDownTimer();
                    enableRegisterButton(true);
                } else if(!TextUtils.isEmpty(result.getMessage())) {
                    enableCaptchaButton(true);
                    enableRegisterButton(false);
                    mCodeSend.setText(R.string.getCode);
                    LibToast.show(ResetPwdActivity.this, result.getMessage());
                }
            } else {
                enableCaptchaButton(true);
                enableRegisterButton(false);
                mCodeSend.setText(R.string.getCode);
                LibToast.show(ResetPwdActivity.this, R.string.sms_captcha_failure);
            }
        }
    }


    CountDownTimer mCountDownTimer = null;

    /**
     * 启动倒计时
     */
    private void startCountDownTimer() {
        if(mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(60000,1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    String leaveSecond = "" + Math.round(millisUntilFinished/1000f);
                    mCodeSend.setText(leaveSecond);
                }

                @Override
                public void onFinish() {
                    mCodeSend.setText(R.string.getCode);
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
            mCodeSend.setEnabled(true);
            mCodeSend.setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            mCodeSend.setEnabled(false);
            mCodeSend.setBackgroundColor(getResources().getColor(R.color.grey));
        }
    }


    /**
     * 启用、禁用注册按钮
     * @param enable
     */
    private void enableRegisterButton(boolean enable) {
        if(enable) {
            mResetSubmit.setEnabled(true);
        } else {
            mResetSubmit.setEnabled(false);
        }
    }
}
