package com.sohu110.airapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aaron on 2016/3/28.
 */
public class BusinessFragment extends Fragment {
    // view
    private View view;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baidu_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new DeviceListTask().execute();
    }

    class DeviceListTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        String mCondition,mContent;
        LoadProcessDialog mLoadDialog;

        public DeviceListTask() {
            mLoadDialog = new LoadProcessDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.deviceList("cust", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<Device>> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {
                    if (result.getData() != null) {

                        //定义Maker坐标点
                        List<LatLng> pts = new ArrayList<LatLng>();
                        if (result.getData() != null) {
                            for (int i = 0; i < result.getData().size(); i++) {
                                pts.add(new LatLng(result.getData().get(i).getJqWD(),result.getData().get(i).getJqJD()));
                            }
                        }

                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_gcoding);

                        List<OverlayOptions> list = new ArrayList<OverlayOptions>();
                        for (int i = 0; i < pts.size(); i++) {
                            //构建MarkerOption，用于在地图上添加Marker
                            OverlayOptions option = new MarkerOptions()
                                    .position(pts.get(i))
                                    .icon(bitmap);

                            list.add(option);
                        }


                        //定义地图状态
                        MapStatus mMapStatus = new MapStatus.Builder()
                                .target(pts.get(1))
                                .zoom(18)
                                .build();
                        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


                        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                        //改变地图状态
                        mBaiduMap.setMapStatus(mMapStatusUpdate);


                        //在地图上添加Marker，并显示
                        mBaiduMap.addOverlays(list);
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.device_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
