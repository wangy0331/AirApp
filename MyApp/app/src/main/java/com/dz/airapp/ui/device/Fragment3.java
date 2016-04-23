package com.dz.airapp.ui.device;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.dz.airapp.R;
import com.dz.airapp.bean.DeviceDetail;
import com.dz.airapp.bean.Result;
import com.dz.airapp.log.Logger;
import com.dz.airapp.service.ServiceCenter;

public class Fragment3 extends Fragment {

	private static String GUID = "guid";

	private String guid;

	private BaiduMap mBaiduMap;

	private MapView mMapView;


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
		Log.e("fragment", guid);
		SDKInitializer.initialize(getActivity().getApplicationContext());


		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_baidu_map,null );
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		new DeviceDetailTask(guid).execute();
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
						Log.e("纬度", item.getJqWd().toString());
						Log.e("经度", item.getJqJd().toString());
						//定义Maker坐标点
						LatLng point = new LatLng(item.getJqWd(), item.getJqJd());
						//构建Marker图标
						BitmapDescriptor bitmap = BitmapDescriptorFactory
								.fromResource(R.drawable.icon_gcoding);
						//构建MarkerOption，用于在地图上添加Marker
						OverlayOptions option = new MarkerOptions()
								.position(point)
								.icon(bitmap);

						//定义地图状态
						MapStatus mMapStatus = new MapStatus.Builder()
								.target(point)
								.zoom(18)
								.build();
						//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


						MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
						//改变地图状态
						mBaiduMap.setMapStatus(mMapStatusUpdate);


						//在地图上添加Marker，并显示
						mBaiduMap.addOverlay(option);




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
