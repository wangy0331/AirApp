package com.sohu110.airapp.ui.jieneng;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.sohu110.airapp.bean.Energy;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.cn.sharesdk.onekeyshare.OnekeyShare;
import com.sohu110.airapp.cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.BaseActivity;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * 节能砖家
 * Created by Aaron on 2016/4/26.
 */
public class EnergyActivity extends BaseActivity {

    private LinearLayout mLinearLayout;

    private LineChart mLineChart;
    //昨日节能
    private TextView mZrjn;
    //昨日节能
    private TextView mJrjn;
    //总实际耗能
    private TextView mMaxHN;
    //累计节能
    private TextView mLeiji;
    //节能率
    private TextView mJnl;

    private TextView mJieneng;

    private TextView mShouyi;

    private TextView mDanwei;

    private TextView mJiage;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private double jrjn;
    private double zrjn;
    private double sjjn;
    private double ljjn;

    //年、半年、月、周
    private RadioGroup mRadioGroup;
    private RadioButton sday;
    private RadioButton moon;
    private RadioButton halfyear;
    private RadioButton year;

    //默认周
    private String condition = "sday";

    private TextView zsjText;
    private TextView lText;
    private TextView ljText;

    //节能和收益
    private String status = "jieneng";

    private double jiage = 0.8;

    LineData mLineData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        setTitle(R.string.jieneng_zhuanjia);

        mLinearLayout = (LinearLayout) findViewById(R.id.jnzx_bg);

        mLineChart = (LineChart) findViewById(R.id.jn_chart);

        mZrjn = (TextView) findViewById(R.id.zrjn);

        mJrjn = (TextView) findViewById(R.id.jrjn);

        mMaxHN = (TextView) findViewById(R.id.maxJn);

        mLeiji = (TextView) findViewById(R.id.leiji_jn);

        mJnl = (TextView) findViewById(R.id.jnl);

        mJieneng = (TextView) findViewById(R.id.jieneng);

        mShouyi = (TextView) findViewById(R.id.shouyi);

        mDanwei = (TextView) findViewById(R.id.danwei);

        zsjText = (TextView) findViewById(R.id.zsj_text);

        ljText = (TextView) findViewById(R.id.lj_text);

        lText = (TextView) findViewById(R.id.l_text);

        mJiage = (TextView) findViewById(R.id.jiage);

        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_chart);

        sday =  (RadioButton) findViewById(R.id.zhou_chart);
        moon =  (RadioButton) findViewById(R.id.yue_chart);
        halfyear =  (RadioButton) findViewById(R.id.bannian_chart);
        year =  (RadioButton) findViewById(R.id.nian_chart);

        sday.setTextColor(getResources().getColor(R.color.blue));
        moon.setTextColor(getResources().getColor(R.color.grey));
        halfyear.setTextColor(getResources().getColor(R.color.grey));
        year.setTextColor(getResources().getColor(R.color.grey));

        mJiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EnergyActivity.this);
                final EditText inputServer = new EditText(EnergyActivity.this);
                builder.setTitle("请输入每度电的价格(元/度)").setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        inputServer.getText().toString();
                        mJiage.setText(inputServer.getText().toString() + "元/度");
                        jiage = Double.valueOf(inputServer.getText().toString());

                        if ("shouyi".equals(status)) {
                            mJieneng.setTextColor(getResources().getColor(R.color.notCheck));
                            mShouyi.setTextColor(getResources().getColor(R.color.check));
                            mZrjn.setText(String.valueOf(zrjn));
                            mZrjn.setText("昨日收益 " + String.format("%.2f", zrjn * jiage) + "(元)");
                            mDanwei.setText(getResources().getString(R.string.yuan));
                            zsjText.setText(getResources().getString(R.string.zsjyd));
                            ljText.setText(getResources().getString(R.string.ljsy));
                            lText.setText(getResources().getString(R.string.syl));
                            mJrjn.setText(String.format("%.2f", jrjn * jiage));
                            mMaxHN.setText(String.format("%.2f", sjjn * jiage));
                            mLeiji.setText(String.format("%.2f", ljjn * jiage));
                            double a = ljjn;
                            double b = sjjn;
                            double db = a / (a+b);
                            String lv = "0.0";
                            if (!Double.valueOf(db).isNaN()) {
                                lv = String.format("%.2f", db * 100);
                            }
                            mJnl.setText(lv);
                        }

                    }
                });
                builder.show();
            }
        });

        mJieneng.setTextColor(getResources().getColor(R.color.check));

        mShouyi.setTextColor(getResources().getColor(R.color.notCheck));

        mDanwei.setText(getResources().getString(R.string.kwh));
        zsjText.setText(getResources().getString(R.string.jnzx_zjn));
        ljText.setText(getResources().getString(R.string.ljjn));
        lText.setText(getResources().getString(R.string.jnl));

        mZrjn.setText("昨日节能 0.0(kWh)");
        mJrjn.setText("0.0");
        mMaxHN.setText("0.0");
        mLeiji.setText("0.0");
        mJnl.setText("0.0");


        //节能
        mJieneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status = "jieneng";

                mJieneng.setTextColor(getResources().getColor(R.color.check));
                mShouyi.setTextColor(getResources().getColor(R.color.notCheck));

                mZrjn.setText(String.valueOf(zrjn));

                mZrjn.setText("昨日节能 " + String.valueOf(zrjn) + "(kWh)");

                mDanwei.setText(getResources().getString(R.string.kwh));
                zsjText.setText(getResources().getString(R.string.jnzx_zjn));
                ljText.setText(getResources().getString(R.string.ljjn));
                lText.setText(getResources().getString(R.string.jnl));

                mJrjn.setText(String.valueOf(jrjn));
                mMaxHN.setText(String.valueOf(sjjn));
                mLeiji.setText(String.valueOf(ljjn));

                double a = ljjn;
                double b = sjjn;
                double db = a / (a + b);


                String lv = "0.0";

                if (!Double.valueOf(db).isNaN()) {
                    lv = String.format("%.2f", db * 100);
                }

                mJnl.setText(lv);
            }
        });
        //收益
        mShouyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status = "shouyi";

                mJieneng.setTextColor(getResources().getColor(R.color.notCheck));
                mShouyi.setTextColor(getResources().getColor(R.color.check));

                mZrjn.setText(String.valueOf(zrjn));

                mZrjn.setText("昨日收益 " + String.format("%.2f", zrjn * jiage) + "(元)");

                mDanwei.setText(getResources().getString(R.string.yuan));
                zsjText.setText(getResources().getString(R.string.zsjyd));
                ljText.setText(getResources().getString(R.string.ljsy));
                lText.setText(getResources().getString(R.string.syl));

                mJrjn.setText(String.format("%.2f", jrjn * jiage));
                mMaxHN.setText(String.format("%.2f", sjjn * jiage));
                mLeiji.setText(String.format("%.2f", ljjn * jiage));

                double a = ljjn;
                double b = sjjn;

                double db = a / (a + b);

//                String lv = String.format("%.2f", db * 100);

                String lv = "0.0";

                if (!Double.valueOf(db).isNaN()) {
                    lv = String.format("%.2f", db * 100);
                }


                mJnl.setText(lv);
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.energy_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (LibApplication.getInstance().isNetworkConnected()) {
                    //今日
                    new EnergyTodayTask("day").execute();
                    //七日
                    new EnergySdayTask("sday").execute();
                } else {
                    LibToast.show(EnergyActivity.this, R.string.not_network);
                }
            }
        });


        if (LibApplication.getInstance().isNetworkConnected()) {
            //今日
            new EnergyTodayTask("day").execute();
            //七日
            new EnergySdayTask("sday").execute();
        } else {
            LibToast.show(EnergyActivity.this, R.string.not_network);
        }


        getBtnRight().setImageDrawable(getResources().getDrawable(R.drawable.fenxiang));


        getBtnRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showShare();

            }
        });






        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == sday.getId()) {//客户
                    condition = "sday";
                    sday.setTextColor(getResources().getColor(R.color.blue));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == moon.getId()) {//设备号
                    condition = "moon";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.blue));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == halfyear.getId()) {//地区
                    condition = "halfyear";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.blue));
                    year.setTextColor(getResources().getColor(R.color.grey));
                } else if (checkedId == year.getId()) {//地区
                    condition = "year";
                    sday.setTextColor(getResources().getColor(R.color.grey));
                    moon.setTextColor(getResources().getColor(R.color.grey));
                    halfyear.setTextColor(getResources().getColor(R.color.grey));
                    year.setTextColor(getResources().getColor(R.color.blue));
                }
                //
                new EnergySdayTask(condition).execute();
            }
        });

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
            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null) {
                if (result.isSuceed()) {

                    if (result != null) {
                        if (result.getData() != null) {


                            zrjn = result.getData().getZrjn();
                            jrjn = result.getData().getJrjn();
                            sjjn = result.getData().getSjhn();
                            ljjn = result.getData().getJyhn();

                            mJieneng.setTextColor(getResources().getColor(R.color.check));
                            mShouyi.setTextColor(getResources().getColor(R.color.notCheck));

                            mZrjn.setText("昨日节能 " + String.valueOf(result.getData().getZrjn()) + "(kWh)");

                            mDanwei.setText(getResources().getString(R.string.kwh));
                            zsjText.setText(getResources().getString(R.string.jnzx_zjn));
                            ljText.setText(getResources().getString(R.string.ljjn));
                            lText.setText(getResources().getString(R.string.jnl));

                            mJrjn.setText(String.valueOf(result.getData().getJrjn()));
                            mMaxHN.setText(String.valueOf(result.getData().getSjhn()));
                            mLeiji.setText(String.valueOf(result.getData().getJyhn()));

                            double a = result.getData().getJyhn();
                            double b = result.getData().getSjhn();

                            double db = a/(a+b);

                            String lv = "0.0";

                            if (!Double.valueOf(db).isNaN()) {
                                lv = String .format("%.2f", db*100);
                            }

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

        lineChart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        lineChart.getAxisLeft().setStartAtZero(false);

        //隐藏左边坐标轴横网格线
        lineChart.getAxisLeft().setDrawGridLines(true);
        //隐藏右边坐标轴横网格线
        lineChart.getAxisRight().setDrawGridLines(false);
        //隐藏X轴竖网格线
        lineChart.getXAxis().setDrawGridLines(false);

        lineChart.getXAxis().resetLabelsToSkip();


        lineChart.getLegend().setEnabled(false);

        lineChart.getXAxis().setTextSize(12f);
        lineChart.getAxisLeft().setTextSize(12f);

        lineChart.getAxisLeft().setStartAtZero(true);    //设置Y轴坐标是否从0开始
        lineChart.getAxisLeft().setAxisMaxValue(100);    //设置Y轴坐标最大为多少


        // enable touch gestures
        lineChart.setTouchEnabled(false); // 设置是否可以触摸


//        lineChart.getXAxis().resetLabelsToSkip();
//        lineChart.getXAxis().setSpaceBetweenLabels(3);
//        lineChart.getXAxis().setLabelsToSkip(1);
//        xAxis.resetLabelsToSkip(); setSpaceBetweenLabels(int characters)

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

//        SimpleDateFormat sf = new SimpleDateFormat ("MM-dd");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String str = formatter.format(curDate);


//        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
//        ca.setTime(new Date()); //设置时间为当前时间
//        ca.add(Calendar.DATE, -1); //年份减1
//        Date lastMonth = ca.getTime(); //结果



//        Comparator comp = new SortComparator();
//        Collections.sort(result, comp);

//        for (int a = 0; a < result.size(); a++) {
//            Log.e("day", String.valueOf(result.get(a).getsDay()));
//        }

        ArrayList<String> xValues = new ArrayList<String>();
            for (int i = 0; i <result.size(); i++) {
                // x轴显示的数据，这里默认使用数字下标显示
                xValues.add("" +  String.valueOf(result.get(i).getsDay()).substring(4,8));
                Log.e("日期", String.valueOf(result.get(i).getsDay()));
            }

//            for (int i = result.size()+1; i >= 0; i--) {
//
//                ca.setTime(new Date()); //设置时间为当前时间
//                ca.add(Calendar.DATE, -i); //减1
//                Date lastDay = ca.getTime(); //结果
//
//                // x轴显示的数据，这里默认使用数字下标显示
//                xValues.add("" + sf.format(lastDay).toString());
//                Log.e("日期", sf.format(lastDay).toString());
//            }

//            xValues.add("");

            // y轴的数据
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < result.size(); i++) {
//                double lv = result.get(i).getJyhn();

                double a = result.get(i).getJyhn();
                double b = result.get(i).getSjhn();
                double db = a/(a+b);
                String lv = "0.0";

                if (lv != null) {
                    lv = String .format("%.2f", db*100);
                }

                Log.e("chart", String.valueOf(lv));

                yValues.add(new Entry(Float.valueOf(lv), i));
            }

            // create a dataset and give it a type
            // y轴的数据集合
            LineDataSet lineDataSet = new LineDataSet(yValues, "" /*显示在比例图上*/);
            // mLineDataSet.setFillAlpha(110);
            // mLineDataSet.setFillColor(Color.RED);

        //用y轴的集合来设置参数
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(2f); // 线宽
//        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.rgb(0, 174, 239));// 显示颜色
//        lineDataSet.setCircleColor(Color.rgb(255, 51, 0));// 圆形的颜色
//        lineDataSet.setHighLightColor(Color.rgb(255, 51, 0)); // 高亮的线的颜色
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.rgb(164, 194, 244));
        lineDataSet.setDrawValues(false);


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            lineDataSets.add(lineDataSet); // add the datasets

            // create a data object with the datasets
            LineData lineData = new LineData(xValues, lineDataSets);

            return lineData;
        }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("开创空压机第3种节能方式");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.dzjn88.com/kyjezs.html");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("空压机e助手打造全新的工作方式，为您带来便捷、轻松的工作体验。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("file:///android_asset/share_icon.png");//确保SDcard下面存在此张图片
        oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz/iaHaBOvKY12lqeS03Dl35vq9qia6K8HDxwdXRjD8h5IrY6uicR0MVBS2pZW5oW7onZr0fnkr0Ko5SBWfHhxltqHIQ/0?wx_fmt=png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.dzjn88.com/kyjezs.html");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.dzjn88.com/kyjezs.html");
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                Log.d("Aaron", "platForm:" + platform.getName());

                // 重写新浪微博的 title
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText("空压机e助手打造全新的工作方式，为您带来便捷、轻松的工作体验。 http://www.dzjn88.com/kyjezs.html");
                    paramsToShare.setUrl(null);
                }



                // 重写朋友圈的 title
                if ("WechatMoments".equals(platform.getName())) {


                    paramsToShare.setTitle("开创空压机第3种节能方式");
                    paramsToShare.setText("空压机e助手打造全新的工作方式，为您带来便捷、轻松的工作体验。");
                }
            }
        });

        // 启动分享GUI
        oks.show(EnergyActivity.this);

//// 启动分享GUI
//        oks.show(this);
    }



    /**
     * 从Assets中读取图片
     */
    private Bitmap getImageFromAssetsFile(String fileName)
    {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }

}
