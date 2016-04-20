package com.dz.airapp.ui.device;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dz.airapp.R;

public class Fragment2 extends Fragment{

	private static String GUID = "guid";

	private String guid;

	public static Fragment2 newInstance(String guid) {
		Fragment2 fragment = new Fragment2();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_device_image,null );
	}

}
