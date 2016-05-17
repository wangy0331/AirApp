package com.sohu110.airapp.ui.jieneng;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Energy;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.utils.SortComparator;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 节能之星
 * Created by Aaron on 2016/4/26.
 */
public class EnergyActivity extends BaseActivity {

    private LinearLayout mLinearLayout;

    private LineChart mLineChart;
    //昨日节能
    private TextView mZrjn;
    //总实际耗能
    private TextView mMaxHN;
    //累计节能
    private TextView mLeiji;
    //节能率
    private TextView mJnl;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    LineData mLineData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        setTitle(R.string.jieneng_zhixing);

        mLinearLayout = (LinearLayout) findViewById(R.id.jnzx_bg);

        mLineChart = (LineChart) findViewById(R.id.jn_chart);

        mZrjn = (TextView) findViewById(R.id.zrjn_text);

        mMaxHN = (TextView) findViewById(R.id.maxJn);

        mLeiji = (TextView) findViewById(R.id.leiji_jn);

        mJnl = (TextView) findViewById(R.id.jnl);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.energy_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (LibApplication.getInstance().isNetworkConnected()) {
                    //昨日
                    new EnergyYesterTask("yester").execute();
                    //今日
                    new EnergyTodayTask("today").execute();
                    //七日
                    new EnergySdayTask("sday").execute();
                } else {
                    LibToast.show(EnergyActivity.this, R.string.not_network);
                }
            }
        });

        GradientDrawable grad = new GradientDrawable(//渐变色
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{getResources().getColor(R.color.bg_jieneng1), getResources().getColor(R.color.bg_jieneng2)}
        );
        mLinearLayout.setBackgroundDrawable(grad);//设置渐变颜色

        if (LibApplication.getInstance().isNetworkConnected()) {
            //昨日
            new EnergyYesterTask("yester").execute();
            //今日
            new EnergyTodayTask("today").execute();
            //七日
            new EnergySdayTask("sday").execute();
        } else {
            LibToast.show(EnergyActivity.this, R.string.not_network);
        }


    }


    class EnergyYesterTask extends AsyncTask<Void, Void, Result<Energy>> {

        String mCondition;
        LoadProcessDialog mLoadDialog;

        public EnergyYesterTask(String condition) {
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(EnergyActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<Energy> doInBackground(Void... params) {
            try {
                return ServiceCenter.getEnergyYes(mCondition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<Energy> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {

                    if (result != null) {
                        if (result.getData() != null) {
                            mZrjn.setText(String.valueOf(result.getData().getJyhn()));
                        } else {
                            mZrjn.setText("0.0");
                        }
                    } else {
                        mZrjn.setText("0.0");
                    }

                } else {
                    Toast.makeText(EnergyActivity.this, R.string.energy_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(EnergyActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }


    class EnergyTodayTask extends AsyncTask<Void, Void, Result<Energy>> {

        String mCondition;
        LoadProcessDialog mLoadDialog;

        public EnergyTodayTask(String condition) {
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(EnergyActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<Energy> doInBackground(Void... params) {
            try {
                return ServiceCenter.getEnergyToday(mCondition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<Energy> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {

                    if (result != null) {
                        if (result.getData() != null) {
                            mMaxHN.setText(String.valueOf(result.getData().getSjhn()));
                            mLeiji.setText(String.valueOf(result.getData().getJyhn()));

                            int a = result.getData().getJyhn();
                            int b = result.getData().getSjhn();

                            Log.e("a", String.valueOf(a));
                            Log.e("b", String.valueOf(b));

                            double db = (double)a/b;


                            String sal = new DecimalFormat("#.00").format(db*100);

                            String lv = String .format("%.2f", db*100);

                            Log.e("a/b", lv);

                            mJnl.setText(lv);
                        }

                    } else {
                        mMaxHN.setText("0.0");
                    }

                } else {
                    Toast.makeText(EnergyActivity.this, R.string.energy_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(EnergyActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }


    class EnergySdayTask extends AsyncTask<Void, Void, Result<List<Energy>>> {

        String mCondition;
        LoadProcessDialog mLoadDialog;

        public EnergySdayTask(String condition) {
            mCondition = condition;
            mLoadDialog = new LoadProcessDialog(EnergyActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Energy>> doInBackground(Void... params) {
            try {
                return ServiceCenter.getEnergy(mCondition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<Energy>> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                if (result.isSuceed()) {

                    if (result.getData() != null) {

                        mLineData = getLineData(result.getData());

                        showChart(mLineChart, mLineData, Color.rgb(255, 255, 255));

                    }


                } else {
                    Toast.makeText(EnergyActivity.this, R.string.device_failure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(EnergyActivity.this, R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
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

        lineChart.getXAxis().setTextSize(12f);
        lineChart.getAxisLeft().setTextSize(12f);

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
        mLegend.setForm(Legend.LegendForm.SQUARE);
//		mLegend.set
        mLegend.setFormSize(15f);// 字体
        mLegend.setTextColor(Color.rgb(0, 0, 255));// 颜色
        mLegend.setTextSize(15f);
//      mLegend.setTypeface(mTf);// 字体


        lineChart.animateX(2500); // 立即执行的动画,x轴
    }

    /**
     * 生成一个数据
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(List<Energy> result) {

        SimpleDateFormat sf = new SimpleDateFormat ("MM-dd");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String str = formatter.format(curDate);


        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
//        ca.setTime(new Date()); //设置时间为当前时间
//        ca.add(Calendar.DATE, -1); //年份减1
//        Date lastMonth = ca.getTime(); //结果



        Comparator comp = new SortComparator();
        Collections.sort(result, comp);

        for (int a = 0; a < result.size(); a++) {
            Log.e("day", String.valueOf(result.get(a).getsDay()));
        }

        ArrayList<String> xValues = new ArrayList<String>();
//            for (int i = 0; i <result.size(); i++) {
//                ca.setTime(new Date()); //设置时间为当前时间
//                ca.add(Calendar.DATE, -i); //减1
//                Date lastDay = ca.getTime(); //结果
//
//                // x轴显示的数据，这里默认使用数字下标显示
//                xValues.add("" + sf.format(lastDay).toString());
//                Log.e("日期", sf.format(lastDay).toString());
//            }

            for (int i = result.size()-1; i >= 0; i--) {

                ca.setTime(new Date()); //设置时间为当前时间
                ca.add(Calendar.DATE, -i); //减1
                Date lastDay = ca.getTime(); //结果

                // x轴显示的数据，这里默认使用数字下标显示
                xValues.add("" + sf.format(lastDay).toString());
                Log.e("日期", sf.format(lastDay).toString());
            }

            xValues.add("");

            // y轴的数据
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < result.size(); i++) {
                int value = result.get(i).getJyhn();
                yValues.add(new Entry(value, i));
            }

            // create a dataset and give it a type
            // y轴的数据集合
            LineDataSet lineDataSet = new LineDataSet(yValues, "" /*显示在比例图上*/);
            // mLineDataSet.setFillAlpha(110);
            // mLineDataSet.setFillColor(Color.RED);

        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(3f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.rgb(255, 51, 0));// 显示颜色
        lineDataSet.setCircleColor(Color.rgb(255, 51, 0));// 圆形的颜色
        lineDataSet.setHighLightColor(Color.rgb(255, 51, 0)); // 高亮的线的颜色
        lineDataSet.setDrawValues(false);


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            lineDataSets.add(lineDataSet); // add the datasets

            // create a data object with the datasets
            LineData lineData = new LineData(xValues, lineDataSets);

            return lineData;
        }

}
