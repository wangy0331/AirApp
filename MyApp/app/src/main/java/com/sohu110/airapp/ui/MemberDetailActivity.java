package com.sohu110.airapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.MemberDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.cache.CacheCenter;
import com.sohu110.airapp.kit.StringKit;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

/**
 * 个人信息
 * Created by Aaron on 2016/5/5.
 */
public class MemberDetailActivity extends BaseActivity {


    private EditText memberName;
    private EditText memberCname;
    private EditText memberMail;
    private EditText memberAdd;
    private EditText memberScore;
    private EditText memberPhone;
    private TextView beizhu;
    private Button submitEdit;

    private String phone;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        setTitle(R.string.member_detail);
        initView();
        initData();
    }

    private void initView() {
        memberName = (EditText) findViewById(R.id.member_name);
        memberCname = (EditText) findViewById(R.id.member_cname);
        memberMail = (EditText) findViewById(R.id.member_mail);
        memberAdd = (EditText) findViewById(R.id.member_add);
        memberScore = (EditText) findViewById(R.id.member_score);
        memberPhone = (EditText) findViewById(R.id.member_phone);
        beizhu = (TextView) findViewById(R.id.beizhu);
        submitEdit = (Button) findViewById(R.id.edit_member_submit);

        memberName.setEnabled(false);
        memberCname.setEnabled(false);
        memberMail.setEnabled(false);
        memberAdd.setEnabled(false);
        memberPhone.setEnabled(false);
        memberScore.setEnabled(false);
        beizhu.setVisibility(View.GONE);
        submitEdit.setVisibility(View.GONE);

        submitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = memberName.getText().toString();
                String cname = memberCname.getText().toString();
                String add = memberAdd.getText().toString();
                String mail = memberMail.getText().toString();

                new memberSubmitTask(name,cname,add,mail).execute();
            }
        });

        getBtnRight().setImageResource(R.drawable.btn_edit);
        getBtnRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //设置传递方向
//                Intent intent = new Intent(new Intent(MemberDetailActivity.this, MemberEditActivity.class));
//                //绑定数据
//                intent.putExtra(Const.EXTRA_PHONE, phone);
//                intent.putExtra(Const.EXTRA_SCORE, score);
//                startActivity(intent);

                memberName.setEnabled(true);
                memberCname.setEnabled(true);
                memberMail.setEnabled(true);
                memberAdd.setEnabled(true);
                beizhu.setVisibility(View.VISIBLE);
                submitEdit.setVisibility(View.VISIBLE);
            }
        });
    }


    private void initData() {
        new memberDetailTask().execute();
    }

    class memberDetailTask extends AsyncTask<Void, Void, Result<MemberDetail>> {

        LoadProcessDialog mLoadDialog;

        public memberDetailTask() {
            mLoadDialog = new LoadProcessDialog(MemberDetailActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<MemberDetail> doInBackground(Void... params) {
            try {
                return ServiceCenter.memberDetail();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<MemberDetail> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if(result != null) {
                if(result.isSuceed()) {

                    if (result != null) {
                        memberName.setText(result.getData().getUserName());
                        memberCname.setText(result.getData().getUserCname());
                        memberMail.setText(result.getData().getUserMail());
                        memberAdd.setText(result.getData().getUserAdd());
                        memberScore.setText(result.getData().getScore());
                        memberPhone.setText(CacheCenter.getCurrentUser().getUserid());

                        phone = CacheCenter.getCurrentUser().getUserid();
                        score = result.getData().getScore();
                    }

                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(MemberDetailActivity.this, result.getMessage());
                } else {
                    LibToast.show(MemberDetailActivity.this, R.string.member_detail_failure);
                }
            } else {
                LibToast.show(MemberDetailActivity.this, R.string.member_register_network);
            }
        }
    }

    class memberSubmitTask extends AsyncTask<Void, Void, Result<MemberDetail>> {

        String mName;
        String mCname;
        String mAdd;
        String mMail;
        LoadProcessDialog mLoadDialog;

        public memberSubmitTask(String name, String cname, String add, String mail) {
            mName = name;
            mCname = cname;
            mAdd = add;
            mMail = mail;
            mLoadDialog = new LoadProcessDialog(MemberDetailActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<MemberDetail> doInBackground(Void... params) {
            try {
                return ServiceCenter.submitMember(mName, mCname, mAdd, mMail);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<MemberDetail> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if(result != null) {
                if(result.isSuceed()) {
                    LibToast.show(MemberDetailActivity.this, R.string.member_edit_success);
                    beizhu.setVisibility(View.GONE);
                    submitEdit.setVisibility(View.GONE);
                } else if(StringKit.isNotEmpty(result.getMessage())) {
                    LibToast.show(MemberDetailActivity.this, result.getMessage());
                } else {
                    LibToast.show(MemberDetailActivity.this, R.string.member_detail_failure);
                }
            } else {
                LibToast.show(MemberDetailActivity.this, R.string.member_register_network);
            }
        }
    }
}
