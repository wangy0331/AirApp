package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息
 * Created by Aaron on 2016/5/22.
 */
public class DeviceInfo implements Serializable {
    //设备序列号
    private String jiqiSn;
    //机器品牌
    private String jiqiBrand;
    //机器功率
    private String jiqiKw;
    //客户名称
    private String CustName;
    //客户地址
    private String CustAdd;
    //空压机品牌
    private String airBrand;
    //空压机编号
    private String airSn;
    //空压机出厂日期
    private String airOd;
    //紧急电话
    private String emeTel;
    //维护电话
    private String whTel;

    public String getJiqiSn() {
        return jiqiSn;
    }

    public void setJiqiSn(String jiqiSn) {
        this.jiqiSn = jiqiSn;
    }

    public String getJiqiBrand() {
        return jiqiBrand;
    }

    public void setJiqiBrand(String jiqiBrand) {
        this.jiqiBrand = jiqiBrand;
    }

    public String getJiqiKw() {
        return jiqiKw;
    }

    public void setJiqiKw(String jiqiKw) {
        this.jiqiKw = jiqiKw;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustAdd() {
        return CustAdd;
    }

    public void setCustAdd(String custAdd) {
        CustAdd = custAdd;
    }

    public String getAirBrand() {
        return airBrand;
    }

    public void setAirBrand(String airBrand) {
        this.airBrand = airBrand;
    }

    public String getAirSn() {
        return airSn;
    }

    public void setAirSn(String airSn) {
        this.airSn = airSn;
    }

    public String getAirOd() {
        return airOd;
    }

    public void setAirOd(String airOd) {
        this.airOd = airOd;
    }

    public String getEmeTel() {
        return emeTel;
    }

    public void setEmeTel(String emeTel) {
        this.emeTel = emeTel;
    }

    public String getWhTel() {
        return whTel;
    }

    public void setWhTel(String whTel) {
        this.whTel = whTel;
    }


    /**
     * 解析json
     * @param response
     * @return
     */
    public static Result<DeviceInfo> parse(String response) {
        List<DeviceInfo> list = null;

        Result<DeviceInfo> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceInfo>();
            result.setCode(1);
//            result.setCode(obj.optInt("errcode"));
//            result.setMessage(obj.optString("errtext"));
//            Log.e("size", String.valueOf(obj.length()));
            result.setData(fromJson(obj));

            JSONArray array = obj.optJSONArray("Showsetinfo");
            if (array != null) {
                list = new ArrayList<DeviceInfo>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    result.setData(fromJson(arrItem));
                }
            }

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return result;
    }

    private static DeviceInfo fromJson(JSONObject obj) {
        DeviceInfo item = null;
        try {
            item = new DeviceInfo();
            item.setJiqiSn(obj.getString("Jiqi_sn"));
            item.setJiqiBrand(obj.getString("JiQi_brand"));
            item.setJiqiKw(obj.getString("jiQi_kw"));
            item.setCustName(obj.getString("Cust_name"));
            item.setCustAdd(obj.getString("Cust_add"));
            item.setAirBrand(obj.getString("Air_brand"));
            item.setAirSn(obj.getString("Air_sn"));
            item.setAirOd(obj.getString("Air_od"));
            item.setEmeTel(obj.getString("Eme_tel"));
            item.setWhTel(obj.getString("Wh_tel"));

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
