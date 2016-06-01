package com.sohu110.airapp.ui;

import android.app.Activity;
import android.os.Bundle;

import com.sohu110.airapp.DashBoardPressView;
import com.sohu110.airapp.DashBoardTempView;
import com.sohu110.airapp.DashBoardView;
import com.sohu110.airapp.R;

public class ThermometerActivity extends Activity {

    DashBoardView dashBoardView;
    DashBoardTempView dashBoardTempView;
    DashBoardPressView dashBoardPressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        dashBoardView = (DashBoardView) findViewById(R.id.dashBoardView);
        dashBoardTempView = (DashBoardTempView) findViewById(R.id.dashBoardTempView);
        dashBoardPressView = (DashBoardPressView) findViewById(R.id.dashBoardPressView);
        dashBoardView.setSpeed(683);
        dashBoardTempView.setSpeed(100);
        dashBoardPressView.setSpeed(1.3f);
    }


}