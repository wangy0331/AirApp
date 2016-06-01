package com.sohu110.airapp.ui.device;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceChart;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {

	private XAxis xAxis;         //X坐标轴
	private YAxis yAxis;

	//排气温度
	private LineChart pqwdChart;
	//电机温度
	private LineChart djwdChart;
	//环境温度
	private LineChart hjwdChart;
	//空气温度
	private LineChart kqylChart;

	private static String GUID = "guid";

	private String guid;

	private ImageButton mImageButton;

	private RadioGroup mRadioGroup;

	private RadioButton mRadionBtn;

	private RadioButton mPress;



	LineData mLineDataPQ;
	LineData mLineDataDJ;
	LineData mLineDataHJ;
	LineData mLineDataKQ;


	//y轴
	private int num = 0;


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


		new DeviceDetailTask(guid).execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_device_image, null);

		mImageButton = (ImageButton) view.findViewById(R.id.chart_refresh);

//		mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_chart);
//
//		mTemp = (RadioButton) view.findViewById(R.id.temp);
//
//		mPress = (RadioButton) view.findViewById(R.id.press);

		mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_chart);

		mRadionBtn = (RadioButton) view.findViewById(mRadioGroup.getCheckedRadioButtonId());

		Log.e("chart_radion", mRadionBtn.getText().toString());

//		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				if (checkedId == mRadionBtn.getId()) {
//					Log.e("chart_radion", mRadionBtn.getText().toString());
//				}
//
//
////				if (LibApplication.getInstance().isNetworkConnected()) {
////					new DeviceListTask(mEditText.getText().toString(), condition).execute();
////				} else {
////					LibToast.show(BaojingListActivity.this, R.string.not_network);
////				}
//			}
//		});

		mImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.e("click", "fragment2");
				new DeviceShuaxinTask(guid).execute();
			}
		});

//		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//				if (checkedId == mTemp.getId()) {//温度
//					Log.e("temp", "temp");
//				} else if (checkedId == mPress.getId()) {//压力
//					Log.e("press", "press");
//				}
//
////				new DeviceListTask(mEditText.getText().toString(), condition).execute();
//			}
//		});


		pqwdChart = (LineChart) view.findViewById(R.id.chartPQWD);
		djwdChart = (LineChart) view.findViewById(R.id.chartDJWD);
		hjwdChart = (LineChart) view.findViewById(R.id.chartHJWD);
		kqylChart = (LineChart) view.findViewById(R.id.chartKQYL);

		return view;
	}


//	@Override
//	public void onResume() {
//		super.onResume();
//		new DeviceDetailTask(guid).execute();
//	}


	// 设置显示的样式
	private void showChart(LineChart lineChart, LineData lineData, int color) {
		lineChart.setDrawBorders(false);  //是否在折线图上添加边框

		// no description text
		lineChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		lineChart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid background
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度


		lineChart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//让Y轴的刻度在右边
		lineChart.getAxisLeft().setStartAtZero(false);//刻度不从0开始

		lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
		lineChart.getAxisRight().setEnabled(false);

		lineChart.getXAxis().setGridColor(
				getResources().getColor(R.color.lineChart_transparent));

		//刻度颜色
		lineChart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
		lineChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
		lineChart.getXAxis().setAxisLineColor(Color.rgb(255, 255, 255));
		lineChart.getAxisLeft().setAxisLineColor(Color.rgb(255, 255, 255));


		//隐藏左边坐标轴横网格线
		lineChart.getAxisLeft().setDrawGridLines(false);
		//隐藏右边坐标轴横网格线
		lineChart.getAxisRight().setDrawGridLines(false);
		//隐藏X轴竖网格线
		lineChart.getXAxis().setDrawGridLines(false);

		lineChart.getLegend().setEnabled(false);

		lineChart.getXAxis().setTextSize(10f);
		lineChart.getAxisLeft().setTextSize(10f);

		// enable touch gestures
		lineChart.setTouchEnabled(false); // 设置是否可以触摸

		// enable scaling and dragging
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(true);// 是否可以缩放

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//

//		lineChart.setBackgroundColor(color);// 设置背景

//		lineChart.getAxisLeft().setAxisMinValue(-30f);


		// add data
		lineChart.setData(lineData); // 设置数据

//		lineChart.getAxisLeft().setAxisMaxValue(200);
//		lineChart.getAxisLeft().setAxisMinValue(-10f);

//		lineChart.getAxisLeft().setSpaceTop(40);
//		lineChart.getAxisLeft().setSpaceBottom(-20f);

		// get the legend (only possible after setting data)
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
//		mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
		mLegend.setForm(Legend.LegendForm.SQUARE);
//		mLegend.set
		mLegend.setFormSize(15f);// 字体
		mLegend.setTextColor(Color.rgb(255, 255, 255));// 颜色
		mLegend.setTextSize(15f);
//      mLegend.setTypeface(mTf);// 字体


		lineChart.animateX(2500); // 立即执行的动画,x轴
	}

	/**
	 * 生成一个数据
	 *
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData1(List<DeviceChart> result) {


		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = result.size()-1; i >= 0; i--) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + result.get(i).getDatetime());
		}


		// y轴的数据  排气温度
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getGqYl();
			yValues.add(new Entry(value, i));
		}


		// create a dataset and give it a type
		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "供气压力折线图");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);


		//用y轴的集合来设置参数
		lineDataSet.setLineWidth(1.0f); // 线宽
		lineDataSet.setColor(Color.rgb(255, 255, 255));// 显示颜色
		lineDataSet.setCircleColor(Color.rgb(255, 255, 255));// 圆形的颜色
		lineDataSet.setHighLightColor(Color.rgb(255, 255, 255)); // 高亮的线的颜色
		lineDataSet.setDrawValues(false);

		lineDataSet.setDrawCircles(false);


		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

		lineDataSets.add(lineDataSet);

		// create a data object with the datasets

		LineData lineData = new LineData(xValues, lineDataSets);


		return lineData;
	}


	/**
	 * 生成一个数据
	 *
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData2(List<DeviceChart> result) {


		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = result.size()-1; i >= 0; i--) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + result.get(i).getDatetime());
		}



		// y轴的数据  电机温度
		ArrayList<Entry> yValues2 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getDjdl();
			yValues2.add(new Entry(value, i));
		}




		// y轴的数据集合
		LineDataSet lineDataSet2 = new LineDataSet(yValues2, "电机电流折线图");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet2.setLineWidth(1.0f); // 线宽
		lineDataSet2.setColor(Color.rgb(255, 255, 255));// 显示颜色
		lineDataSet2.setCircleColor(Color.rgb(255, 255, 255));// 圆形的颜色
		lineDataSet2.setHighLightColor(Color.rgb(255, 255, 255)); // 高亮的线的颜色
		lineDataSet2.setDrawValues(false);

		lineDataSet2.setDrawCircles(false);

		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

		lineDataSets.add(lineDataSet2);


		// create a data object with the datasets

		LineData lineData = new LineData(xValues, lineDataSets);


		return lineData;
	}

	/**
	 * 生成一个数据
	 *
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData3(List<DeviceChart> result) {


		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = result.size()-1; i >= 0; i--) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + result.get(i).getDatetime());
		}



		// y轴的数据  环境温度
		ArrayList<Entry> yValues3 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getDjwd();
			yValues3.add(new Entry(value, i));
		}

		// y轴的数据集合
		LineDataSet lineDataSet3 = new LineDataSet(yValues3, "电机温度折线图");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet3.setLineWidth(1.0f); // 线宽
		lineDataSet3.setColor(Color.rgb(255, 255, 255));// 显示颜色
		lineDataSet3.setCircleColor(Color.rgb(255, 255, 255));// 圆形的颜色
		lineDataSet3.setHighLightColor(Color.rgb(255, 255, 255)); // 高亮的线的颜色
		lineDataSet3.setDrawValues(false);

		lineDataSet3.setDrawCircles(false);

		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

		lineDataSets.add(lineDataSet3);

		// create a data object with the datasets

		LineData lineData = new LineData(xValues, lineDataSets);


		return lineData;
	}


	/**
	 * 生成一个数据
	 *
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData4(List<DeviceChart> result) {


		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = result.size()-1; i >= 0; i--) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + result.get(i).getDatetime());
		}




		// y轴的数据  气压
		ArrayList<Entry> yValues4 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getPqwd();
			yValues4.add(new Entry(value, i));
		}

		// y轴的数据集合
		LineDataSet lineDataSet4 = new LineDataSet(yValues4, "排气温度折线图");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet4.setLineWidth(1.0f); // 线宽
		lineDataSet4.setColor(Color.rgb(255, 255, 255));// 显示颜色
		lineDataSet4.setCircleColor(Color.rgb(255, 255, 255));// 圆形的颜色
		lineDataSet4.setHighLightColor(Color.rgb(255, 255, 255)); // 高亮的线的颜色
		lineDataSet4.setDrawValues(false);


		lineDataSet4.setDrawCircles(false);

		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

		lineDataSets.add(lineDataSet4);


		// create a data object with the datasets


		LineData lineData = new LineData(xValues, lineDataSets);


		return lineData;
	}


	class DeviceDetailTask extends AsyncTask<Void, Void, Result<List<DeviceChart>>> {

		private String jiqiSn;

		public DeviceDetailTask(String guid) {
			jiqiSn = guid;
		}


		@Override
		protected Result<List<DeviceChart>> doInBackground(Void... params) {
			try {
				return ServiceCenter.getChart(jiqiSn);
			} catch (Exception e) {
				Logger.e("", "", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result<List<DeviceChart>> result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.isSuceed()) {


					if (result.getData() != null) {

						result.getData().size();

						Log.e("个数", String.valueOf(result.getData().size()));


						mLineDataPQ = getLineData1(result.getData());
						mLineDataDJ = getLineData2(result.getData());
						mLineDataHJ = getLineData3(result.getData());
						mLineDataKQ = getLineData4(result.getData());

						showChart(pqwdChart, mLineDataPQ, Color.rgb(255, 255, 255));
						showChart(djwdChart, mLineDataDJ, Color.rgb(255, 255, 255));
						showChart(hjwdChart, mLineDataHJ, Color.rgb(255, 255, 255));
						showChart(kqylChart, mLineDataKQ, Color.rgb(255, 255, 255));

					}

				} else {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
			}
		}

	}


	class DeviceShuaxinTask extends AsyncTask<Void, Void, Result<List<DeviceChart>>> {

		private String jiqiSn;
		LoadProcessDialog mLoadDialog;

		public DeviceShuaxinTask(String guid) {
			jiqiSn = guid;
			mLoadDialog = new LoadProcessDialog(getContext());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadDialog.show();
		}


		@Override
		protected Result<List<DeviceChart>> doInBackground(Void... params) {
			try {
				return ServiceCenter.getChart(jiqiSn);
			} catch (Exception e) {
				Logger.e("", "", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result<List<DeviceChart>> result) {
			super.onPostExecute(result);
			mLoadDialog.dismiss();
			if (result != null) {
				if (result.isSuceed()) {


					if (result.getData() != null) {

						result.getData().size();

						Log.e("个数", String.valueOf(result.getData().size()));


//						mLineData = getLineData(result.getData());
//
//						showChart(chart, mLineData, Color.rgb(255, 255, 255));

					}

				} else {
//					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			} else {
//				Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
			}
		}


	}

}

