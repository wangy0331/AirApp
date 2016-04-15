package com.dz.airapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dz.airapp.ui.BusinessFragment;
import com.dz.airapp.ui.CustomerFragment;
import com.dz.airapp.ui.HomeFragment;
import com.dz.airapp.ui.MemberFragment;
import com.dz.airapp.utils.FragmentHelper;

public class MainActivity extends FragmentActivity {

    FragmentHelper mFragmentHelper;
    View currentButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("aaron", "MainActivity");
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        // 默认选中Tab1
        setDefaultTab();

    }

    private void initView() {
        mFragmentHelper = new FragmentHelper(this, getSupportFragmentManager(), R.id.main_container);
    }

    /**
     * 设置默认Tab
     */
    public void setDefaultTab() {
        tabClick(findViewById(R.id.main_tab_1));
    }


    public void tabClick(View tab) {
        Intent intent = null;
        String id = null;
        switch (tab.getId()) {
            case R.id.main_tab_1: // 首页
                Log.e("tab1", String.valueOf(tab.getId()));
                id = HomeFragment.class.getName();
                intent = new Intent(getApplicationContext(), HomeFragment.class);
                break;
            case R.id.main_tab_2: // 客户管理
                Log.e("tab2", String.valueOf(tab.getId()));
//                if (CacheCenter.getCurrentUser() != null) {
                    id = CustomerFragment.class.getName();
                    intent = new Intent(getApplicationContext(), CustomerFragment.class);
//                } else {
//                    LoginActivity.open(MainActivity.this);
//                }
                break;
            case R.id.main_tab_3: // 节能商圈
                Log.e("tab3", String.valueOf(tab.getId()));
                id = BusinessFragment.class.getName();
                intent = new Intent(getApplicationContext(), BusinessFragment.class);
                break;
            case R.id.main_tab_4: // 我
                Log.e("tab4", String.valueOf(tab.getId()));
                id = MemberFragment.class.getName();
                intent = new Intent(getApplicationContext(), MemberFragment.class);
//                if (CacheCenter.getCurrentUser() != null) {
////                    new CountTask().execute();
//                }
                break;
            default:
                break;
        }
        if (intent != null) {
//            Log.e("id", id.toString());
            mFragmentHelper.switchFragment(id, intent);
            setButton(tab);
        }
    }

    /**
     * 更新Tab按钮状态
     *
     * @param v
     */
    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton = v;
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            super.onBackPressed();
        }
    }

    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.activity_pop_window, null);
    }

}
