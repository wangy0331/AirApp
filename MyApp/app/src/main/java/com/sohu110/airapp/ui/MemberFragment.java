package com.sohu110.airapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.cache.CacheCenter;

/**
 * 个人
 * Created by Aaron on 2016/3/28.
 */
public class MemberFragment extends Fragment {
    // view
    private View view;

    private TextView mTextView;

    private Button mOutBtn;

    private Button phoneBtn;

    private Button mAboutUs;

    private Button mChangePwd;

    private Button mMemberDetail;

    private Button mErweima;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);
//        mTextView = (TextView) view.findViewById(R.id.phone_text_haoma);
//
//        if (CacheCenter.getCurrentUser() != null) {
//            mTextView.setText(CacheCenter.getCurrentUser().getUserid());
//        }

        mOutBtn = (Button) view.findViewById(R.id.settings_sign_out);

        phoneBtn = (Button) view.findViewById(R.id.btn_service_phone);

        mAboutUs = (Button) view.findViewById(R.id.about_us);

        mChangePwd = (Button) view.findViewById(R.id.changePwd);

        mMemberDetail = (Button) view.findViewById(R.id.my_user);

        mErweima = (Button) view.findViewById(R.id.my_erweima);

        mErweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ErweimaActivity.class));
            }
        });

        mMemberDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CacheCenter.getCurrentUser() != null) {
                    startActivity(new Intent(getActivity(), MemberDetailActivity.class));
                } else {
                    LoginActivity.open(getActivity());
                }
            }
        });

        mChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CacheCenter.getCurrentUser() != null) {
                    startActivity(new Intent(getActivity(), ChangePwdActivity.class));
                } else {
                    LoginActivity.open(getActivity());
                }
            }
        });

        mOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        // 设置对话框标题
                        new android.app.AlertDialog.Builder(getActivity()).setTitle(R.string.tishi)

                                // 设置显示的内容
                                .setMessage(R.string.sign_out)

                                        // 添加确定按钮
                                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {

                                    // 确定按钮的响应事件
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CacheCenter.removeCurrentUser();
                                        mOutBtn.setVisibility(View.GONE);
                                    }
                                    // 添加返回按钮
                                }).setNegativeButton(R.string.btnReturn, null).show();// 在按键响应事件中显示此对话框
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        if (CacheCenter.getCurrentUser() != null) {
            mOutBtn.setVisibility(View.VISIBLE);
        } else {
            mOutBtn.setVisibility(View.GONE);
        }
    }


    private void showDialog() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.callPhone)
                .setMessage(R.string.service_phone).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intentPhone = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" + getString(R.string.service_phone)));
                        startActivity(intentPhone);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }
}
