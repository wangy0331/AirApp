package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备列表item
 * Created by Aaron on 2016/4/18.
 */
public class Device implements Serializable {
    //机器序列号
    private String jiqiSn;
    //权限（C为控制，R为显示）
    private String auth;
    //机器类型
    private String jqName;
    //机器经度
    private Double jqWD;
    //机器纬度
    private Double jqJD;
    //运行状态
    private String jqStatus;
    //机器注册日期
    private String jiqiNewDate;
    //机器生产日期
    private String jiqiPdate;
    //归属地区
    private String region;
    //客户名称
    private String coName;

    public Double getJqWD() {
        return jqWD;
    }

    public void setJqWD(Double jqWD) {
        this.jqWD = jqWD;
    }

    public Double getJqJD() {
        return jqJD;
    }

    public void setJqJD(Double jqJD) {
        this.jqJD = jqJD;
    }

    public String getJiqiSn() {
        return jiqiSn;
    }

    public void setJiqiSn(String jiqiSn) {
        this.jiqiSn = jiqiSn;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getJqName() {
        return jqName;
    }

    public void setJqName(String jqName) {
        this.jqName = jqName;
    }

    public String getJqStatus() {
        return jqStatus;
    }

    public void setJqStatus(String jqStatus) {
        this.jqStatus = jqStatus;
    }

    public String getJiqiNewDate() {
        return jiqiNewDate;
    }

    public void setJiqiNewDate(String jiqiNewDate) {
        this.jiqiNewDate = jiqiNewDate;
    }

    public String getJiqiPdate() {
        return jiqiPdate;
    }

    public void setJiqiPdate(String jiqiPdate) {
        this.jiqiPdate = jiqiPdate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    /**
     * 解析JSON数据
     * @param json
     * @return
     */
    public static Result<List<Device>> parse(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("Productlist");
            if (array != null) {
                list = new ArrayList<Device>();
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

    /**
     * 解析item
     * @return
     */
    public static Device fromJson(JSONObject obj) {
        Device item = null;
        try {
            item = new Device();
            item.jiqiSn = obj.optString("Jiqi_sn");
            item.auth = obj.optString("Auth");
            item.jqName = obj.optString("JQ_Name");
            item.jqWD = obj.optDouble("JQ_WD");
            item.jqJD = obj.optDouble("JQ_JD");
            item.jqStatus = obj.optString("JQ_status");
            item.jiqiNewDate = obj.optString("JQ_newDate");
            item.jiqiPdate = obj.optString("JiQi_Pdate");
            item.region = obj.optString("Region");
            item.coName = obj.optString("CoName");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
