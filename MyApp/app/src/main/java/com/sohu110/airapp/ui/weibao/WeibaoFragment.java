package com.sohu110.airapp.ui.weibao;

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
public class WeibaoFragment extends Fragment {

    private static String GUID = "guid";

    private String guid;

    //采集日期
    private TextView mTime;
    //距离天数
    private TextView wbts;
    //下次维保
    private TextView xcwb;
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

    //单位
    private String h = "h";
    private String ci = "次";

    public static WeibaoFragment newInstance(String guid) {
        WeibaoFragment fragment = new WeibaoFragment();
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

        View view = inflater.inflate(R.layout.fragment_weibao,null );

        wbts = (TextView) view.findViewById(R.id.wb_ts);
        ylsj = (TextView) view.findViewById(R.id.wb_ylsj);
        xcwb = (TextView) view.findViewById(R.id.wb_xcsj);
        yfsj = (TextView) view.findViewById(R.id.wb_yfsj);
        klsj = (TextView) view.findViewById(R.id.wb_klsj);
        rysj = (TextView) view.findViewById(R.id.wb_rysj);
        yzsj = (TextView) view.findViewById(R.id.wb_yzsj);
        pdsj = (TextView) view.findViewById(R.id.wb_pdsj);
        ylcs = (TextView) view.findViewById(R.id.wb_ylcs);
        yfcs = (TextView) view.findViewById(R.id.wb_yfcs);
        klcs = (TextView) view.findViewById(R.id.wb_klcs);
        rycs = (TextView) view.findViewById(R.id.wb_rycs);
        yzcs = (TextView) view.findViewById(R.id.wb_yzcs);
        pdcs = (TextView) view.findViewById(R.id.wb_pdcs);
        ljyxsj = (TextView) view.findViewById(R.id.wb_ljyxsj);
        ljjzsj = (TextView) view.findViewById(R.id.wb_ljjzsj);
        bcyxsj = (TextView) view.findViewById(R.id.wb_bcyxsj);
        bcjzsj = (TextView) view.findViewById(R.id.wb_bcjzsj);
        mTime = (TextView) view.findViewById(R.id.wb_cjsj);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wb_list_refresh);

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
                return ServiceCenter.getDetailWb(jiqiSn);
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

                        wbts.setText(item.getTs());
                        xcwb.setText("预计下次保养日期：" + item.getXcwb());
                        ylsj.setText(item.getYlsj().trim() + h);
                        yfsj.setText(item.getYfsj().trim() + h);
                        klsj.setText(item.getKlsj().trim() + h);
                        rysj.setText(item.getRysj().trim() + h);
                        yzsj.setText(item.getYzsj().trim() + h);
                        pdsj.setText(item.getPdsj().trim() + h);
                        ylcs.setText(item.getYlcs().trim() + ci);
                        yfcs.setText(item.getYfcs().trim() + ci);
                        klcs.setText(item.getKlcs().trim() + ci);
                        rycs.setText(item.getRycs().trim() + ci);
                        yzcs.setText(item.getYzcs().trim() + ci);
                        pdcs.setText(item.getPdcs().trim() + ci);
                        ljyxsj.setText(item.getLjyxsj().trim() + h);
                        ljjzsj.setText(item.getLjjzsj().trim() + h);
                        bcyxsj.setText(item.getBcyxsj().trim() + h);
                        bcjzsj.setText(item.getBcjzsj().trim() + h);
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
