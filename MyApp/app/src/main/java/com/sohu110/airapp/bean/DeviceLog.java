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
    //流水号
    private String lsh;
    //事件记录
    private String sjjl;
    //事件时间
    private String sjsj;

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

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getSjjl() {
        return sjjl;
    }

    public void setSjjl(String sjjl) {
        this.sjjl = sjjl;
    }

    public String getSjsj() {
        return sjsj;
    }

    public void setSjsj(String sjsj) {
        this.sjsj = sjsj;
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
     * 解析JSON数据---维保
     * @param json
     * @return
     */
    public static Result<List<DeviceLog>> parseWB(String json) {
        Result<List<DeviceLog>> deviceList = null;
        List<DeviceLog> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<DeviceLog>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("保养记录");
            if (array != null) {
                list = new ArrayList<DeviceLog>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonWB(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析JSON数据---设备日志
     * @param json
     * @return
     */
    public static Result<List<DeviceLog>> parseRZ(String json) {
        Result<List<DeviceLog>> deviceList = null;
        List<DeviceLog> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<DeviceLog>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("设备历史日志");
            if (array != null) {
                list = new ArrayList<DeviceLog>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonRZ(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析item---设备日志
     * @return
     */
    public static DeviceLog fromJsonRZ(JSONObject obj) {
        DeviceLog item = null;
        try {
            item = new DeviceLog();
            item.sjsj = obj.optString("事件时间");
            item.sjjl = obj.optString("事件记录");
            item.xlh = obj.optString("设备序列号");
            item.lsh = (obj.optString("事件流水号"));

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item---报警
     * @return
     */
    public static DeviceLog fromJsonWB(JSONObject obj) {
        DeviceLog item = null;
        try {
            item = new DeviceLog();
            item.sj = obj.optString("保养日期");
            item.zt = obj.optString("保养项目");
            item.xlh = obj.optString("机器序列号");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item---维保
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
