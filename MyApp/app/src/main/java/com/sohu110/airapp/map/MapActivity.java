package com.sohu110.airapp.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sohu110.airapp.R;

public class MapActivity extends Activity {
	TextView tvLocationPoint;
	double latitude;// γ��
	double longitude;// ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		tvLocationPoint = (TextView) findViewById(R.id.my_location_point);

		Intent intent = getIntent();
		if (intent.hasExtra("x") && intent.hasExtra("y")) {
			Bundle bundle = intent.getExtras();
			latitude = bundle.getDouble("x");
			longitude = bundle.getDouble("y");
			tvLocationPoint.setText("y:" + latitude + "x:" + longitude);
		} else {
			tvLocationPoint.setText("y1111");
		}

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.location_btn:
			startActivity(new Intent(this, MyLocation.class));
			break;
		case R.id.show_my_location_btn:
			Intent intent = new Intent(this, ShowMyLocation.class);
			Bundle bundle = new Bundle();
			bundle.putDouble("x", latitude);
			bundle.putDouble("y", longitude);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}
}
