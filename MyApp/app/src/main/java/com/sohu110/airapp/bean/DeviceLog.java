package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史记录
 * Created by Aaron on 2016/5/31.
 */
public class DeviceLog implements Serializable {
    //报警时间
    private String sj;
    //报警状态
    private String zt;
    //机器序列号
    private String xlh;

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh;
    }

    /**
     * 解析JSON数据---预警
     * @param json
     * @return
     */
    public static Result<List<DeviceLog>> parseYJ(String json) {
        Result<List<DeviceLog>> deviceList = null;
        List<DeviceLog> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<DeviceLog>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("预警记录");
            if (array != null) {
                list = new ArrayList<DeviceLog>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonYJ(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析JSON数据---报警
     * @param json
     * @return
     */
    public static Result<List<DeviceLog>> parseBJ(String json) {
        Result<List<DeviceLog>> deviceList = null;
        List<DeviceLog> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<DeviceLog>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("报警记录");
            if (array != null) {
                list = new ArrayList<DeviceLog>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonBJ(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析item---报警
     * @return
     */
    public static DeviceLog fromJsonBJ(JSONObject obj) {
        DeviceLog item = null;
        try {
            item = new DeviceLog();
            item.sj = obj.optString("报警时间");
            item.zt = obj.optString("报警状态");
            item.xlh = obj.optString("机器序列号");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item---预警
     * @return
     */
    public static DeviceLog fromJsonYJ(JSONObject obj) {
        DeviceLog item = null;
        try {
            item = new DeviceLog();
            item.sj = obj.optString("预警时间");
            item.zt = obj.optString("预警状态");
            item.xlh = obj.optString("机器序列号");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
