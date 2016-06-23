package com.sohu110.airapp.ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.device.DeviceDetailActivity;
import com.sohu110.airapp.utils.Const;
import com.sohu110.airapp.widget.LibToast;

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

    private ImageButton mBtnRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());

        if (LibApplication.getInstance().isNetworkConnected()) {
            new DeviceListTask().execute();
        } else {
            LibToast.show(getActivity(), R.string.not_network);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) view.findViewById(R.id.view_maps);
//        mMapGb = (TextView) view.findViewById(R.id.map_bg);
        mBtnRight = (ImageButton) view.findViewById(R.id.shuaxin_btn);
        mBtnRight.setImageResource(R.drawable.shuaxin);

        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LibApplication.getInstance().isNetworkConnected()) {
                    new DeviceListTask().execute();
                } else {
                    LibToast.show(getActivity(), R.string.not_network);
                }
            }
        });

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("map_click", marker.getTitle());
                Device device = new Device();
                device.setJiqiSn(marker.getTitle());
                Intent intent = new Intent(getActivity(),
                        DeviceDetailActivity.class);
                intent.putExtra(Const.EXTRA_DEVICE, device);
                startActivity(intent);
                return false;
            }
        });

        return view;
    }



    class DeviceListTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        String mCondition,mContent;
//        LoadProcessDialog mLoadDialog;

//        public DeviceListTask() {
//            mLoadDialog = new LoadProcessDialog(getActivity());
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.deviceList("setno", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<Device>> result) {
            super.onPostExecute(result);
//            mLoadDialog.dismiss();[]
            if (result != null) {
                if (result.isSuceed()) {
                    if (result.getData() != null) {

                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.yuxing);


                        //定义Maker坐标点
                        List<LatLng> pts = new ArrayList<LatLng>();

                        List<OverlayOptions> list = new ArrayList<OverlayOptions>();

                        if (result.getData() != null) {


                            for (int i = 0; i < result.getData().size(); i++) {

//                                Log.e("纬度", result.getData().get(i).getJqWD().toString());
//                                Log.e("经度", result.getData().get(i).getJqJD().toString());


                                if (result.getData().get(i).getJqWD().isNaN() || result.getData().get(i).getJqJD().isNaN()) {

                                } else {
                                    pts.add(new LatLng(result.getData().get(i).getJqWD(),result.getData().get(i).getJqJD()));
                                }

//                                pts.add(new LatLng(result.getData().get(i).getJqWD(),result.getData().get(i).getJqJD()));
                                //构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions option = new MarkerOptions()
                                        .position(new LatLng(result.getData().get(i).getJqWD(),result.getData().get(i).getJqJD()))
                                        .icon(bitmap).title(result.getData().get(i).getJiqiSn());
                                list.add(option);
                            }
                        }

                        if (pts.size() > 0) {
                            //定义地图状态
                            MapStatus mMapStatus = new MapStatus.Builder()
                                    .target(pts.get(0))
                                    .zoom(18)
                                    .build();

                            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                            //改变地图状态
                            mBaiduMap.setMapStatus(mMapStatusUpdate);


                            //在地图上添加Marker，并显示
                            mBaiduMap.addOverlays(list);

                        } else {

                            //定义地图状态
                            MapStatus mMapStatus = new MapStatus.Builder()
                                    .zoom(18)
                                    .build();

                            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                            //改变地图状态
                            mBaiduMap.setMapStatus(mMapStatusUpdate);


                            //在地图上添加Marker，并显示
                            mBaiduMap.addOverlays(list);

                        }

                        //定义地图状态
//                        MapStatus mMapStatus = new MapStatus.Builder()
//                                .target(pts.get(1))
//                                .zoom(18)
//                                .build();
                        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化



//                        mMapView.setVisibility(View.VISIBLE);
//                        mMapGb.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.device_failure, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BusinessFragment.this, R.string.device_failure, Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(getActivity(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
//                Toast.makeText(BusinessFragment.this, R.string.device_failure, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

}
