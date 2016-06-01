package com.sohu110.airapp.ui.baojing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.DeviceYBDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;

/**
 * 报警详情
 * Created by Aaron on 2016/5/29.
 */
public class BaojingFragment extends Fragment {

    private static String GUID = "guid";

    Device mDevice;

    private String guid;

    //采集日期
    private TextView mTime;
    //机器序列号
    private TextView jiqiSn;
    //排气压力
    private TextView pqPress;
    //主机电流
    private TextView zjdl;
    //机头温度
    private TextView jtTemp;
    //风机电流1
    private TextView fj1dl;
    //风机电流2
    private TextView fj2dl;
    //工作电压
    private TextView gzdy;
    //客户名称
    private TextView khmc;
    //客户名称地址
    private TextView khmcdz;
    //空压机品牌
    private TextView kyjpp;
    //空压机编号
    private TextView kyjbh;
    //紧急电话
    private TextView jjdh;
    //维护电话
    private TextView whdh;
    //原因
    private TextView mYy;
    //已停机
    private TextView ytj;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static BaojingFragment newInstance(String guid) {
        BaojingFragment fragment = new BaojingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GUID, guid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取预警类型编号
        guid = this.getArguments().getString(GUID);
        Log.e("fragment", guid);
    }

    @Override
    public void onResume() {
        super.onResume();
        new DeviceDetailTask(guid).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_baojing,null );

        ytj = (TextView) view.findViewById(R.id.tjsj);
        mYy = (TextView) view.findViewById(R.id.bjyy);
        mTime = (TextView) view.findViewById(R.id.bjcjsj);
        khmc = (TextView) view.findViewById(R.id.bj_khmc);
        khmcdz = (TextView) view.findViewById(R.id.bj_khdz);
        kyjpp = (TextView) view.findViewById(R.id.bj_kyjpp);
        kyjbh = (TextView) view.findViewById(R.id.bj_kyjbh);
        jjdh = (TextView) view.findViewById(R.id.bj_jjdh);
        whdh = (TextView) view.findViewById(R.id.bj_whdh);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.bj_list_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DeviceDetailTask(guid).execute();
            }
        });

        return view;
    }

    class DeviceDetailTask extends AsyncTask<Void, Void, Result<DeviceYBDetail>> {

        private String jiqiSn;

        public DeviceDetailTask(String guid) {
            jiqiSn = guid;
        }

        @Override
        protected Result<DeviceYBDetail> doInBackground(Void... params) {
            try{
                return ServiceCenter.getDetailBj(jiqiSn);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<DeviceYBDetail> result) {
            super.onPostExecute(result);
            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                if (result.isSuceed()) {

                    DeviceYBDetail item = result.getData();

                    if (item != null) {
                        ytj.setText("已停机12h");
                        if (!"".equals(item.getYjzt())) {
                            mYy.setText(item.getYjzt().substring(5, item.getYjzt().length()).trim());
                        }
                        mTime.setText(item.getCjrq().trim());
                        khmc.setText(item.getKhmc().trim());
                        khmcdz.setText(item.getKhmcdz().trim());
                        kyjpp.setText(item.getKyjpp().trim());
                        kyjbh.setText(item.getKyjbh().trim());
                        jjdh.setText(item.getJjdh().trim());
                        whdh.setText(item.getWhdh().trim());
                    }

                } else {
                    Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
