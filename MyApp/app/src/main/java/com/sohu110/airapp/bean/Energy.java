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
    //实际耗能
    private double sjhn;
    //节约能耗
    private double jyhn;
    //昨日节能
    private double zrjn;
    //今日节能
    private double jrjn;
    //用户
    private String userAccount;
    //Sday
    private int sDay;

    public double getSjhn() {
        return sjhn;
    }

    public void setSjhn(double sjhn) {
        this.sjhn = sjhn;
    }

    public double getJyhn() {
        return jyhn;
    }

    public void setJyhn(double jyhn) {
        this.jyhn = jyhn;
    }

    public double getZrjn() {
        return zrjn;
    }

    public void setZrjn(double zrjn) {
        this.zrjn = zrjn;
    }

    public double getJrjn() {
        return jrjn;
    }

    public void setJrjn(double jrjn) {
        this.jrjn = jrjn;
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

    /**
     * 七日
     * @param json
     * @return
     */
    public static Result<List<Energy>> parseSday(String json) {
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
                    list.add(fromJsonSday(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    private static Energy fromJsonSday(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.sjhn = obj.optDouble("sjhn");
            item.jyhn = obj.optDouble("jyhn");
            item.zrjn = obj.optDouble("zrjn");
            item.jrjn = obj.optDouble("jrjn");
            item.sDay = obj.optInt("Sday");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }


    /**
     * 月
     * @param json
     * @return
     */
    public static Result<List<Energy>> parseMoon(String json) {
        Result<List<Energy>> deviceList = null;
        List<Energy> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Energy>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("Moon data");
            if (array != null) {
                list = new ArrayList<Energy>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonMoon(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    private static Energy fromJsonMoon(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.sjhn = obj.optDouble("sjhn");
            item.jyhn = obj.optDouble("jyhn");
            item.zrjn = obj.optDouble("zrjn");
            item.jrjn = obj.optDouble("jrjn");
            item.sDay = obj.optInt("Moon");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 半年
     * @param json
     * @return
     */
    public static Result<List<Energy>> parseHeafyear(String json) {
        Result<List<Energy>> deviceList = null;
        List<Energy> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Energy>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("Halfyear data");
            if (array != null) {
                list = new ArrayList<Energy>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonHeafyear(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    private static Energy fromJsonHeafyear(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.sjhn = obj.optDouble("sjhn");
            item.jyhn = obj.optDouble("jyhn");
            item.zrjn = obj.optDouble("zrjn");
            item.jrjn = obj.optDouble("jrjn");
            item.sDay = obj.optInt("halfyear");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 年
     * @param json
     * @return
     */
    public static Result<List<Energy>> parseYear(String json) {
        Result<List<Energy>> deviceList = null;
        List<Energy> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Energy>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("Year data");
            if (array != null) {
                list = new ArrayList<Energy>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonYear(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    private static Energy fromJsonYear(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.sjhn = obj.optDouble("sjhn");
            item.jyhn = obj.optDouble("jyhn");
            item.zrjn = obj.optDouble("zrjn");
            item.jrjn = obj.optDouble("jrjn");
            item.sDay = obj.optInt("year");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }



    /**
     * 今日
     * @param response
     * @return
     */
    public static Result<Energy> parseDay(String response) {
        List<Energy> list = null;

        Result<Energy> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<Energy>();
            result.setCode(1);


            JSONArray array = obj.optJSONArray("day data");
            if (array != null) {
                list = new ArrayList<Energy>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    result.setData(fromJsonDay(arrItem));
                }
            }

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return result;
    }

    private static Energy fromJsonDay(JSONObject obj) {
        Energy item = null;
        try {
            item = new Energy();
            item.sjhn = obj.optDouble("sjhn");
            item.jyhn = obj.optDouble("jyhn");
            item.zrjn = obj.optDouble("zrjn");
            item.jrjn = obj.optDouble("jrjn");
//            item.sDay = obj.optInt("Sday");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
