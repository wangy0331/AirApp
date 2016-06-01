package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 设备注册回调
 * Created by Aaron on 2016/5/27.
 */
public class DeviceThree implements Serializable {
    //机器编码
    private String setNum;
    //品牌
    private String brand;
    //功率
    private String kw;
    //日期
    private String outDate;

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    /**
     * 解析json
     * @param response
     * @return
     */
    public static Result<DeviceThree> parse(String response) {
        List<DeviceThree> list = null;

        Result<DeviceThree> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceThree>();
            result.setCode(obj.optInt("errcode"));
            result.setMessage(obj.optString("errtext"));
//            Log.e("size", String.valueOf(obj.length()));
            result.setData(fromJson(obj));

//            JSONArray array = obj.optJSONArray("Showsetinfo");
//            if (array != null) {
//                list = new ArrayList<DeviceThree>();
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject arrItem = array.getJSONObject(i);
//                    result.setData(fromJson(arrItem));
//                }
//            }

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return result;
    }

    private static DeviceThree fromJson(JSONObject obj) {
        DeviceThree item = null;
        try {
            item = new DeviceThree();
            item.setSetNum(obj.getString("setnum"));
            item.setBrand(obj.getString("brand"));
            item.setKw(obj.getString("kw"));
            item.setOutDate(obj.getString("out_date"));

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
