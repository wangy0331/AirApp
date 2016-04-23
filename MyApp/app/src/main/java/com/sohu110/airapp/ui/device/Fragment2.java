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

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceChart;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment{

	private XAxis xAxis;         //X坐标轴
	private YAxis yAxis;

	private LineChart chart;

	private static String GUID = "guid";

	private String guid;

	LineData mLineData;

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


//		new DeviceDetailTask(guid).execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_device_image, null);

		chart = (LineChart) view.findViewById(R.id.chart);

		return view;
	}


	@Override
	public void onResume() {
		super.onResume();
		new DeviceDetailTask(guid).execute();
	}


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


		lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
		lineChart.getAxisRight().setEnabled(false);

		lineChart.getXAxis().setGridColor(
				getResources().getColor(R.color.lineChart_transparent));



		//隐藏左边坐标轴横网格线
		lineChart.getAxisLeft().setDrawGridLines(false);
		//隐藏右边坐标轴横网格线
		lineChart.getAxisRight().setDrawGridLines(false);
		//隐藏X轴竖网格线
		lineChart.getXAxis().setDrawGridLines(false);

		lineChart.getLegend().setEnabled(false);


		// enable touch gestures
		lineChart.setTouchEnabled(false); // 设置是否可以触摸

		// enable scaling and dragging
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(true);// 是否可以缩放

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//

		lineChart.setBackgroundColor(color);// 设置背景

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
		mLegend.setForm(Legend.LegendForm.CIRCLE);
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.rgb(0,178,238));// 颜色
//      mLegend.setTypeface(mTf);// 字体

		lineChart.animateX(2500); // 立即执行的动画,x轴
	}

	/**
	 * 生成一个数据
	 * @param count 表示图表中有多少个坐标点
	 * @param range 用来生成range以内的随机数
	 * @return
	 */
	private LineData getLineData(List<DeviceChart> result) {


		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < result.size(); i++) {
			// x轴显示的数据，这里默认使用数字下标显示
			xValues.add("" + i);
		}





		// y轴的数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getAirTemp();
			yValues.add(new Entry(value, i));
		}

		// y轴的数据
		ArrayList<Entry> yValues1 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getAirTemp1();
			yValues1.add(new Entry(value, i));
		}

		// y轴的数据
		ArrayList<Entry> yValues2 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getDianjiTmep();
			yValues2.add(new Entry(value, i));
		}

		// y轴的数据
		ArrayList<Entry> yValues3 = new ArrayList<Entry>();
		for (int i = 0; i < result.size(); i++) {
			int value = result.get(i).getEnvireTemp();
			yValues3.add(new Entry(value, i));
		}

		// create a dataset and give it a type
		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);


		//用y轴的集合来设置参数
		lineDataSet.setLineWidth(1.0f); // 线宽
		lineDataSet.setCircleSize(3f);// 显示的圆形大小
		lineDataSet.setColor(Color.rgb(0, 178, 238));// 显示颜色
		lineDataSet.setCircleColor(Color.rgb(0, 178, 238));// 圆形的颜色
		lineDataSet.setHighLightColor(Color.rgb(0, 178, 238)); // 高亮的线的颜色
		lineDataSet.setDrawValues(false);

		// y轴的数据集合
		LineDataSet lineDataSet1 = new LineDataSet(yValues1, "");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet1.setLineWidth(1.0f); // 线宽
		lineDataSet1.setCircleSize(3f);// 显示的圆形大小
		lineDataSet1.setColor(Color.rgb(255, 255, 0));// 显示颜色
		lineDataSet1.setCircleColor(Color.rgb(255, 255, 0));// 圆形的颜色
		lineDataSet1.setHighLightColor(Color.rgb(255, 255, 0)); // 高亮的线的颜色
		lineDataSet1.setDrawValues(false);

		// y轴的数据集合
		LineDataSet lineDataSet2 = new LineDataSet(yValues2, "");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet2.setLineWidth(1.0f); // 线宽
		lineDataSet2.setCircleSize(3f);// 显示的圆形大小
		lineDataSet2.setColor(Color.rgb(255, 0, 0));// 显示颜色
		lineDataSet2.setCircleColor(Color.rgb(255, 0, 0));// 圆形的颜色
		lineDataSet2.setHighLightColor(Color.rgb(255, 0, 0)); // 高亮的线的颜色
		lineDataSet2.setDrawValues(false);

		// y轴的数据集合
		LineDataSet lineDataSet3 = new LineDataSet(yValues3, "");
//		lineDataSet.setFillAlpha(200);
		// mLineDataSet.setFillColor(Color.RED);

		//用y轴的集合来设置参数
		lineDataSet3.setLineWidth(1.0f); // 线宽
		lineDataSet3.setCircleSize(3f);// 显示的圆形大小
		lineDataSet3.setColor(Color.rgb(0, 205, 102));// 显示颜色
		lineDataSet3.setCircleColor(Color.rgb(0, 205, 102));// 圆形的颜色
		lineDataSet3.setHighLightColor(Color.rgb(0, 205, 102)); // 高亮的线的颜色
		lineDataSet3.setDrawValues(false);




		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();


		lineDataSets.add(lineDataSet);
		lineDataSets.add(lineDataSet1); // add the datasets
		lineDataSets.add(lineDataSet2);
		lineDataSets.add(lineDataSet3);


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
			try{
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


						mLineData = getLineData(result.getData());

						showChart(chart, mLineData, Color.rgb(255, 255, 255));

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
