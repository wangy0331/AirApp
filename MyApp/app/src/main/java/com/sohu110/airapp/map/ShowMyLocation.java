package com.sohu110.airapp.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sohu110.airapp.R;

public class ShowMyLocation extends Activity implements
		OnGetGeoCoderResultListener {
	GeoCoder mSearch = null; //
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_my_location);
		mMapView = (MapView) findViewById(R.id.show_my_location_bmapView);
		mBaiduMap = mMapView.getMap();
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		Intent intent = getIntent();
		if (intent.hasExtra("x") && intent.hasExtra("y")) {
			Bundle b = intent.getExtras();
			LatLng point = new LatLng(b.getDouble("x"), b.getDouble("y"));
			float f = mBaiduMap.getMaxZoomLevel();// 19.0
			MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(f - 2);
			mBaiduMap.animateMapStatus(u);
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
		} else {
			Toast.makeText(this, "", Toast.LENGTH_LONG).show();
			this.finish();
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(ShowMyLocation.this, "", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(ShowMyLocation.this, strInfo, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// ����γ�Ⱥ;��Ƚ�������
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(ShowMyLocation.this, "", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		if (!result.getAddress().equals(""))
			Toast.makeText(ShowMyLocation.this, result.getAddress(),
					Toast.LENGTH_LONG).show();
		else {
			Toast toast = Toast.makeText(ShowMyLocation.this, "",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			float f = mBaiduMap.getMinZoomLevel();// 19.0
			MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(f);
			mBaiduMap.animateMapStatus(u);

		}
	}
}
