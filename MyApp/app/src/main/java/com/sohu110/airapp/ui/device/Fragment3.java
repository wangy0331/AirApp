package com.sohu110.airapp.ui.device;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;

public class Fragment3 extends Fragment implements LocationSource,
		AMapLocationListener {

	private static String GUID = "guid";

	private String guid;

	private boolean isCancel = false;

	private View view;
	private AMap aMap;
	private com.amap.api.maps2d.MapView mapView;
	private LocationSource.OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;


	public static Fragment3 newInstance(String guid) {
		Fragment3 fragment = new Fragment3();
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_baidu_map, container, false);
		mapView = (com.amap.api.maps2d.MapView) view.findViewById(R.id.bmapView);
		mapView.onCreate(savedInstanceState);
		init();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
		new DeviceDetailTask(guid).execute();
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
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
	}

	class DeviceDetailTask extends AsyncTask<Void, Void, Result<DeviceDetail>> {

		private String jiqiSn;

		public DeviceDetailTask(String guid) {
			jiqiSn = guid;
		}

		@Override
		protected Result<DeviceDetail> doInBackground(Void... params) {
			try{
				return ServiceCenter.getDetail(jiqiSn);
			} catch (Exception e) {
				Logger.e("", "", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result<DeviceDetail> result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isSuceed()) {

					DeviceDetail item = result.getData();

					if (item != null) {

						if (item.getJqWd() != null || item.getJqJd() != null) {
							Double jd = 0.0;
							Double wd = 0.0;
							if (item.getJqWd() != null) {
								jd = item.getJqJd();
							}
							if (item.getJqJd() != null) {
								wd = item.getJqWd();
							}
							drawMarkers(new LatLng(wd,jd),item.getAirSta());
						}
					}

				} else {
					if (!isCancel) {
						Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				if (!isCancel) {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}

	@Override
	public void onStop() {
		super.onStop();
		isCancel = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mapView.onDestroy();
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
	public void drawMarkers(LatLng latlng, String jqStatus) {

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
				.draggable(true));

	}
}
