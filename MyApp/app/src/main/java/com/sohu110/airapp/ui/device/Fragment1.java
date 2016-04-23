package com.sohu110.airapp.ui.device;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;

public class Fragment1 extends Fragment{
	private TextView mAirPress;
	private TextView mAirTemp1;
	private TextView mDianjiTemp;
	private TextView mModleTemp;
	private TextView mEnviTemp;
	private TextView mCurrentDy;
	private TextView mZhujiDl1;
	private TextView mZhujiDl2;
	private TextView mZhujiDl3;
	private TextView mFengjiDl1;
	private TextView mFengjiDl2;
	private TextView mFengjiDl3;
	private TextView mFengjiAvgDl;
	private TextView mFengjiAvgDl1;
	private TextView mDataTime;

	private static String GUID = "guid";

	private String guid;

	public static Fragment1 newInstance(String guid) {
		Fragment1 fragment = new Fragment1();
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

		View view = inflater.inflate(R.layout.fragment_device_data,null );

		mAirPress = (TextView) view.findViewById(R.id.air_press);
		mAirTemp1 = (TextView) view.findViewById(R.id.air_temp1);
		mDianjiTemp = (TextView) view.findViewById(R.id.dianji_temp);
		mModleTemp = (TextView) view.findViewById(R.id.modle_temp);
		mEnviTemp = (TextView) view.findViewById(R.id.envi_temp);
		mCurrentDy = (TextView) view.findViewById(R.id.current_dy);
		mZhujiDl1 = (TextView) view.findViewById(R.id.zhuji_dl1);
		mZhujiDl2 = (TextView) view.findViewById(R.id.zhuji_dl2);
		mZhujiDl3 = (TextView) view.findViewById(R.id.zhuji_dl3);
		mFengjiDl1 = (TextView) view.findViewById(R.id.fengji_dl1);
		mFengjiDl2 = (TextView) view.findViewById(R.id.fengji_dl2);
		mFengjiDl3 = (TextView) view.findViewById(R.id.fengji_dl3);
		mFengjiAvgDl = (TextView) view.findViewById(R.id.fengjiavgdl);
		mFengjiAvgDl1 = (TextView) view.findViewById(R.id.fengjiavgdl1);
		mDataTime = (TextView) view.findViewById(R.id.data_time);

		return view;
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

						mAirPress.setText(item.getAirPress());
						mAirTemp1.setText(item.getAirTemp());
						mDianjiTemp.setText(item.getDianjiTemp());
						mModleTemp.setText(item.getModleTemp());
						mEnviTemp.setText(item.getEnviTemp());
						mCurrentDy.setText(item.getCurrentDy());
						mZhujiDl1.setText(item.getZhujiDl1());
						mZhujiDl2.setText(item.getZhujiDl2());
						mZhujiDl3.setText(item.getZhujiDl3());
						mFengjiDl1.setText(item.getFengjiDl1());
						mFengjiDl2.setText(item.getFengjiDl2());
						mFengjiDl3.setText(item.getFengjiDl3());
						mFengjiAvgDl.setText(item.getFengjiAvgDl());
						mFengjiAvgDl1.setText(item.getFengjiAvgDl1());
						mDataTime.setText(item.getDataTime());

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
