package com.sohu110.airapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sohu110.airapp.R;


/**
 * Created by Aaron on 2016/3/28.
 */
public class BaseActivity extends FragmentActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ImageButton mBtnLeft;
    private ImageButton mBtnRight;
    private TextView mTvText;
    private Button moreButton;
    private int MAX_FETCH_NUMBER = 10;
    // 暂无内容
    private TextView mTvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        View layout = View.inflate(this, R.layout.activity_base, null);
        mBtnLeft = (ImageButton) layout.findViewById(R.id.header_btn_back);
        mBtnRight = (ImageButton) layout.findViewById(R.id.header_btn_settings);
        mTvText = (TextView) layout.findViewById(R.id.header_title);
//        mTvNoData = (TextView) layout.findViewById(R.id.base_nodata);

        FrameLayout container = (FrameLayout) layout.findViewById(R.id.base_container);
        container.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        mBtnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        super.setContentView(layout, params);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        StatService.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        StatService.onResume(this);
    }

    public void setTitle(int strId) {
        setTitle(getString(strId));
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        if(mTvText != null) {
            mTvText.setText(title);
        }
    }

    /**
     * 获取左侧按钮
     * @return
     */
    public ImageButton getBtnLeft() {
        return mBtnLeft;
    }

    /**
     * 获取右侧按钮
     * @return
     */
    public ImageButton getBtnRight() {
        return mBtnRight;
    }

    /**
     * 打开、关闭“暂无数据”
     * @param visiable
     */
//    public void showNoDataView(boolean visiable) {
//        if(visiable) {
//            mTvNoData.setVisibility(View.VISIBLE);
//        } else {
//            mTvNoData.setVisibility(View.GONE);
//        }
//    }

    /**
     * 简化查找View
     * 替换findViewById
     */
//    protected <T extends View> T findView(int id) {
//        return (T)findViewById(id);
//    }

    /**
     * 添加 List Footer
     * 不可重写
     */
//    protected View getFooterView() {
//        moreButton = new Button(this);
//        moreButton.setText("更多");
//        moreButton.setTextColor(Color.BLACK);
//        moreButton.setBackgroundResource(android.R.drawable.list_selector_background);
//        moreButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        moreButton.setPadding(10, 15, 10, 15);
//        moreButton.setVisibility(View.GONE);
//        return moreButton;
//    }

    /**
     * 重置更多按钮状态
     * @param size 本次请求返回的数据长度
     */
//    protected void resetMoreBtnStatus(List data) {
//        if(hasMoreButton()) {
//            if(data != null && data.size() >= getFetchNumber()) {
//                moreButton.setText("加载更多...");
//                moreButton.setClickable(true);
//                moreButton.setVisibility(View.VISIBLE);
////				mListView.setFooterDividersEnabled(true);
//            } else {
//                moreButton.setText("无更多内容");
//                moreButton.setClickable(false);
//                moreButton.setVisibility(View.GONE);
////				mListView.setFooterDividersEnabled(false);
//            }
//        }
//    }

    /**
     * 是否显示更多按钮
     * @return
     */
    public boolean hasMoreButton() {
        return true;
    }

    /**
     * 获取一次获取数据条数
     * 可以覆盖本方法设置新的数值
     * @return
     */
    public int getFetchNumber() {
        return MAX_FETCH_NUMBER;
    }
}
