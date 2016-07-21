package com.sohu110.airapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.device.DeviceDetailActivity;
import com.sohu110.airapp.utils.Const;
import com.sohu110.airapp.widget.LibToast;

import java.util.List;

/**
 * Created by Aaron on 2016/7/8.
 */
public class FindMapFragment extends Fragment implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener, OnClickListener {

    private View view;
    private ImageButton mBtnRight;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (com.amap.api.maps2d.MapView) view.findViewById(R.id.view_maps);
        mapView.onCreate(savedInstanceState);
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

        if (LibApplication.getInstance().isNetworkConnected()) {
            new DeviceListTask().execute();
        } else {
            LibToast.show(getActivity(), R.string.not_network);
        }

        init();

        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            Log.e("init", "init");
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//        myLocationStyle.anchor(5,5);//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器


        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

        Log.e("setUpMap", "setUpMap");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 绘制系统默认的1种marker背景图片
     */
    public void drawMarkers(LatLng latlng, String sn, String name, String jqStatus) {

        //构建Marker图标
        BitmapDescriptor bitmap = null;

        if ("停止中".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_tzz);
        } else if ("节能停机".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_jntj);
        } else if ("卸载运行".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_xzyx);
        } else if ("加载运行".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_jzyx);
        } else if ("系统报警".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_xtbj);
        } else if ("系统测试".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_xtcs);
        } else if ("紧急停机".equals(jqStatus)) {
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_jjtj);
        }


        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .icon(bitmap)
                .title(sn)
                .draggable(true).snippet(name));

//        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.e("title", marker.getTitle());
        Device device = new Device();
        device.setJiqiSn(marker.getTitle());
        Intent intent = new Intent(getActivity(),
                DeviceDetailActivity.class);
        intent.putExtra(Const.EXTRA_DEVICE, device);
        startActivity(intent);
        return false;
    }

    @Override
    public void onClick(View v) {

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
//            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {
                    if (result.getData() != null) {

                        //构建Marker图标
//                        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                .fromResource(R.drawable.yuxing);

                        //定义Maker坐标点
                        if (result.getData() != null) {
                            for (int i = 0; i < result.getData().size(); i++) {

                                Log.e("纬度", result.getData().get(i).getJqWD().toString());
                                Log.e("经度", result.getData().get(i).getJqJD().toString());

                                if (!result.getData().get(i).getJqWD().isNaN() || !result.getData().get(i).getJqJD().isNaN()) {
                                    drawMarkers(new LatLng(result.getData().get(i).getJqWD(), result.getData().get(i).getJqJD()),
                                            result.getData().get(i).getJiqiSn(), result.getData().get(i).getCoName(),result.getData().get(i).getJqStatus());
                                }
                            }
                        }
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
