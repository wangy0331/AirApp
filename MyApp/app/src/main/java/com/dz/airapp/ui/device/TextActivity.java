package com.dz.airapp.ui.device;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dz.airapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by Aaron on 2016/4/21.
 */
public class TextActivity extends Activity {

    // 高温线下标
    private final int HIGH = 0;

    // 低温线下标
    private final int LOW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        final LineChart mChart = (LineChart) findViewById(R.id.chart);
        initialChart(mChart);
        addLineDataSet(mChart);

        // 每点击一次按钮，增加一个点
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEntry(mChart);
            }
        });
    }

    // 初始化图表
    private void initialChart(LineChart mChart) {
        mChart.setDescription("Zhang Phil @ http://blog.csdn.net/zhangphil");
        mChart.setNoDataTextDescription("暂时尚无数据");

        mChart.setTouchEnabled(true);

        // 可拖曳
        mChart.setDragEnabled(true);

        // 可缩放
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        mChart.setPinchZoom(true);

        // 设置图表的背景颜色
        mChart.setBackgroundColor(0xfff5f5f5);

        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();

        // 可以修改图表注解部分的位置
        // l.setPosition(LegendPosition.LEFT_OF_CHART);

        // 线性，也可是圆
        l.setForm(Legend.LegendForm.LINE);

        // 颜色
        l.setTextColor(Color.CYAN);

        // x坐标轴
        XAxis xl = mChart.getXAxis();
        xl.setTextColor(0xff00897b);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        // 几个x坐标轴之间才绘制？
        xl.setSpaceBetweenLabels(5);

        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);

        // 将X坐标轴放置在底部，默认是在顶部。
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(0xff37474f);

        // 最大值
        leftAxis.setAxisMaxValue(50f);

        // 最小值
        leftAxis.setAxisMinValue(-10f);

        // 不一定要从0开始
        leftAxis.setStartAtZero(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);
    }

    // 为LineChart增加LineDataSet
    private void addLineDataSet(LineChart mChart) {
        LineData data = new LineData();

        data.addDataSet(createHighLineDataSet());
        data.addDataSet(createLowLineDataSet());

        // 数据显示的颜色
        // data.setValueTextColor(Color.WHITE);


        int count = 10;
        int range = 30;

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add("" + i);
        }

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + 3;
            yValues.add(new Entry(value, i));
        }


        // 先增加一个空的数据，随后往里面动态添加
        mChart.setData(data);
    }

    // 同时为高温线和低温线添加进去一个坐标点
    private void addEntry(LineChart mChart) {

        LineData data = mChart.getData();

        data.addXValue((data.getXValCount()) + "");

        // 增加高温
        LineDataSet highLineDataSet = data.getDataSetByIndex(HIGH);
        float fh = (float) ((Math.random()) * 10 + 30);
        Entry entryh = new Entry(fh, highLineDataSet.getEntryCount());
        data.addEntry(entryh, HIGH);

        // 增加低温
        LineDataSet lowLineDataSet = data.getDataSetByIndex(LOW);
        float fl = (float) ((Math.random()) * 10);
        Entry entryl = new Entry(fl, lowLineDataSet.getEntryCount());
        data.addEntry(entryl, LOW);

        mChart.notifyDataSetChanged();

        // 当前统计图表中最多在x轴坐标线上显示的总量
//        mChart.setVisibleXRangeMaximum(4);

        mChart.moveViewToX(data.getXValCount() - 4);
    }

    // 初始化数据集，添加一条高温统计折线
    private LineDataSet createHighLineDataSet() {

        LineDataSet set = new LineDataSet(null, "高温");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // 折线的颜色
        set.setColor(Color.RED);
        set.setCircleColor(Color.YELLOW);
        set.setLineWidth(5f);
        set.setCircleSize(10f);
        // set.setFillAlpha(128);
        set.setCircleColorHole(Color.BLUE);
        set.setHighLightColor(Color.GREEN);
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(10f);
        set.setDrawValues(true);


        return set;
    }

    // 初始化数据集，添加一条低温统计折线
    private LineDataSet createLowLineDataSet() {

        LineDataSet set = new LineDataSet(null, "低温");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // 折线的颜色
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.BLUE);
        set.setLineWidth(1f);
        set.setCircleSize(10f);
        // set.setFillAlpha(128);
        // set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.DKGRAY);
        set.setValueTextColor(Color.BLACK);
        set.setCircleColorHole(Color.RED);
        set.setValueTextSize(15f);
        set.setDrawValues(true);


        return set;
    }
}
