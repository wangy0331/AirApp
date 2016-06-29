package com.sohu110.airapp.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sohu110.airapp.R;

public class MyLocation extends Activity {
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	private TextView tv;
	private MapView mapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_location);
		tv = (TextView) findViewById(R.id.tv);

		mCurrentMode = LocationMode.NORMAL;//
		mCurrentMarker = BitmapDescriptorFactory//
				.fromResource(R.drawable.icon_marka);
		// ��ͼ��ʼ��
		mapView = (MapView) findViewById(R.id.my_location_bmapView);

		mBaiduMap = mapView.getMap();

		// mBaiduMap.setMyLocationEnabled(true);
		// mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
		// mCurrentMode, true, null));
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);//

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	double latitude;
	double longitude;

	public void send(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putDouble("x", latitude);
		bundle.putDouble("y", longitude);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 
	 * @author LIUBO
	 * @date 2015-3-20����5:55:19
	 * @TODO
	 * @param longitude
	 * @param latitude
	 * @param bitmap
	 * @param baiduMap
	 */
	private void overlay(LatLng point, BitmapDescriptor bitmap,
			BaiduMap baiduMap) {
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		baiduMap.addOverlay(option);
	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mapView == null)
				return;

			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				LatLng ll = new LatLng(latitude, longitude);
				float f = mBaiduMap.getMaxZoomLevel();// 19.0
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll,
						f - 2);
				mBaiduMap.animateMapStatus(u);
				tv.setText(location.getAddrStr());
				overlay(ll, mCurrentMarker, mBaiduMap);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mLocClient.stop();
		mBaiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		mapView = null;
		super.onDestroy();
	}
}
