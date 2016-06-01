package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2016/4/21.
 */
public class DeviceChart implements Serializable {
    //机器序列号
    private String jiqiSn;
    //供气压力
    private int gqYl;
    //电机电流
    private int djdl;
    //电机温度
    private int djwd;
    //排气温度
    private int pqwd;
    //时间
    private String datetime;

    public String getJiqiSn() {
        return jiqiSn;
    }

    public void setJiqiSn(String jiqiSn) {
        this.jiqiSn = jiqiSn;
    }

    public int getGqYl() {
        return gqYl;
    }

    public void setGqYl(int gqYl) {
        this.gqYl = gqYl;
    }

    public int getDjdl() {
        return djdl;
    }

    public void setDjdl(int djdl) {
        this.djdl = djdl;
    }

    public int getDjwd() {
        return djwd;
    }

    public void setDjwd(int djwd) {
        this.djwd = djwd;
    }

    public int getPqwd() {
        return pqwd;
    }

    public void setPqwd(int pqwd) {
        this.pqwd = pqwd;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static Result<List<DeviceChart>> parse(String json) {
        Result<List<DeviceChart>> deviceList = null;
        List<DeviceChart> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<DeviceChart>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("ShowSet_Chart");
            if (array != null) {
                list = new ArrayList<DeviceChart>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJson(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    private static DeviceChart fromJson(JSONObject obj) {
        DeviceChart item = null;
        try {
            item = new DeviceChart();
            item.jiqiSn = obj.optString("机器序列号");
            item.gqYl = obj.optInt("供气压力");
            item.djdl = obj.optInt("电机电流");
            item.djwd = obj.optInt("电机温度");
            item.pqwd = obj.optInt("排气温度");
            String time = obj.optString("采集时间");
            item.datetime = time.substring(9, time.length()-3);

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
