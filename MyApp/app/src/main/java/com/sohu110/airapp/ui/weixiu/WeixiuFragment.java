package com.sohu110.airapp.ui.weixiu;

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
import com.sohu110.airapp.bean.DeviceWBDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;

/**
 * 维保详情
 * Created by Aaron on 2016/5/29.
 */
public class WeixiuFragment extends Fragment {

    private static String GUID = "guid";

    private String guid;

    //采集日期
    private TextView mTime;
    //下次维保
    private TextView xctc;
    //距离天数
    private TextView wbts;
    //油滤时间
    private TextView ylsj;
    //油分时间
    private TextView yfsj;
    //空滤时间
    private TextView klsj;
    //润油时间
    private TextView rysj;
    //油脂时间
    private TextView yzsj;
    //皮带时间
    private TextView pdsj;
    //油滤维保次数
    private TextView ylcs;
    //油分维护次数
    private TextView yfcs;
    //空滤维保次数
    private TextView klcs;
    //润油维保次数
    private TextView rycs;
    //油脂维保次数
    private TextView yzcs;
    //皮带维保次数
    private TextView pdcs;
    //累计运行时间
    private TextView ljyxsj;
    //累计加载时间
    private TextView ljjzsj;
    //本次运行时间
    private TextView bcyxsj;
    //本次加载时间
    private TextView bcjzsj;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static WeixiuFragment newInstance(String guid) {
        WeixiuFragment fragment = new WeixiuFragment();
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

        View view = inflater.inflate(R.layout.fragment_weixiu,null );

//        wbts = (TextView) view.findViewById(R.id.wx_t);
        ylsj = (TextView) view.findViewById(R.id.wx_ylsj);
        xctc = (TextView) view.findViewById(R.id.wx_xcsj);
        yfsj = (TextView) view.findViewById(R.id.wx_yfsj);
        klsj = (TextView) view.findViewById(R.id.wx_klsj);
        rysj = (TextView) view.findViewById(R.id.wx_rysj);
        yzsj = (TextView) view.findViewById(R.id.wx_yzsj);
        pdsj = (TextView) view.findViewById(R.id.wx_pdsj);
        ylcs = (TextView) view.findViewById(R.id.wx_ylcs);
        yfcs = (TextView) view.findViewById(R.id.wx_yfcs);
        klcs = (TextView) view.findViewById(R.id.wx_klcs);
        rycs = (TextView) view.findViewById(R.id.wx_rycs);
        yzcs = (TextView) view.findViewById(R.id.wx_yzcs);
        pdcs = (TextView) view.findViewById(R.id.wx_pdcs);
        ljyxsj = (TextView) view.findViewById(R.id.wx_ljyxsj);
        ljjzsj = (TextView) view.findViewById(R.id.wx_ljjzsj);
        bcyxsj = (TextView) view.findViewById(R.id.wx_bcyxsj);
        bcjzsj = (TextView) view.findViewById(R.id.wx_bcjzsj);
        mTime = (TextView) view.findViewById(R.id.wx_cjsj);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wx_list_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DeviceDetailTask(guid).execute();
            }
        });

        return view;
    }

    class DeviceDetailTask extends AsyncTask<Void, Void, Result<DeviceWBDetail>> {

        private String jiqiSn;

        public DeviceDetailTask(String guid) {
            jiqiSn = guid;
        }

        @Override
        protected Result<DeviceWBDetail> doInBackground(Void... params) {
            try{
                return ServiceCenter.getDetailWx(jiqiSn);
            } catch (Exception e) {
                Logger.e("", "", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<DeviceWBDetail> result) {
            super.onPostExecute(result);
            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                if (result.isSuceed()) {

                    DeviceWBDetail item = result.getData();

                    if (item != null) {

//                        wbts.setText("");
                        wbts.setText(item.getTs());
                        ylsj.setText(item.getYlsj());
                        xctc.setText(item.getXcwb());
                        yfsj.setText(item.getYfsj());
                        klsj.setText(item.getKlsj());
                        rysj.setText(item.getRysj());
                        yzsj.setText(item.getYzsj());
                        pdsj.setText(item.getPdsj());
                        ylcs.setText(item.getYlcs());
                        yfcs.setText(item.getYfcs());
                        klcs.setText(item.getKlcs());
                        rycs.setText(item.getRycs());
                        yzcs.setText(item.getYzcs());
                        pdcs.setText(item.getPdcs());
                        ljyxsj.setText(item.getLjyxsj());
                        ljjzsj.setText(item.getLjjzsj());
                        bcyxsj.setText(item.getBcyxsj());
                        bcjzsj.setText(item.getBcjzsj());
                        mTime.setText(item.getCjsj());

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
