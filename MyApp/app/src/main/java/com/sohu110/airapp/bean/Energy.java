package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 能耗
 * Created by Aaron on 2016/5/3.
 */
public class Energy implements Serializable {
    //计算耗能
    private int jshn;
    //实际耗能
    private int sjhn;
    //节约能耗
    private int jyhn;
    //用户
    private String userAccount;
    //Sday
    private int sDay;


    public int getSjhn() {
        return sjhn;
    }

    public void setSjhn(int sjhn) {
        this.sjhn = sjhn;
    }

    public int getJshn() {
        return jshn;
    }

    public void setJshn(int jshn) {
        this.jshn = jshn;
    }

    public int getJyhn() {
        return jyhn;
    }

    public void setJyhn(int jyhn) {
        this.jyhn = jyhn;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getsDay() {
        return sDay;
    }

    public void setsDay(int sDay) {
        this.sDay = sDay;
    }

    public static Result<List<Energy>> parse(String json) {
        Result<List<Energy>> deviceList = null;
        List<Energy> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Energy>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("Week data");
            if (array != null) {
                list = new ArrayList<Energy>();
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

    private static Energy fromJson(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.jshn = obj.optInt("jshn");
            item.sjhn = obj.optInt("sjhn");
            item.jyhn = obj.optInt("jyhn");
            item.sDay = obj.optInt("Sday");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    public static Result<Energy> parse1(String response) {
        List<Energy> list = null;

        Result<Energy> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<Energy>();
            result.setCode(1);


            JSONArray array = obj.optJSONArray("Yesterday data");
            if (array != null) {
                list = new ArrayList<Energy>();
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

    public static Result<Energy> parse2(String response) {
        List<Energy> list = null;

        Result<Energy> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<Energy>();
            result.setCode(1);


            JSONArray array = obj.optJSONArray("Today data");
            if (array != null) {
                list = new ArrayList<Energy>();
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
}
