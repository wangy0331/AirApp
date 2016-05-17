package com.sohu110.airapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.utils.Const;

/**
 * 编辑用户
 * Created by Aaron on 2016/5/5.
 */
public class MemberEditActivity extends BaseActivity {

    private TextView memberPhone;
    private TextView memberScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit);
        setTitle(R.string.member_edit);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        //获取数据
        String phone = intent.getStringExtra(Const.EXTRA_PHONE);
        String score = intent.getStringExtra(Const.EXTRA_SCORE);
        memberPhone.setText(phone);
        memberScore.setText(score);
    }

    private void initView() {
        memberPhone = (TextView) findViewById(R.id.member_phone_edit);
        memberScore = (TextView) findViewById(R.id.member_score_edit);
    }


}
