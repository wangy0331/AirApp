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
    //设备电流
    private int setDl;
    //设备电压
    private int setDy;
    //空气压力
    private int airPress;
    //排气温度
    private int airTemp;
    //排气温度1
    private int airTemp1;
    //环境温度
    private int envireTemp;
    //电机温度
    private int dianjiTmep;
    //主机电流A
    private int zhujiDla;
    //主机电流B
    private int zhujiDlb;
    //主机电流C
    private int zhujiDlc;
    //模块温度
    private int modleTemp;
    //时间
    private String datetime;


    public int getSetDl() {
        return setDl;
    }

    public void setSetDl(int setDl) {
        this.setDl = setDl;
    }

    public int getSetDy() {
        return setDy;
    }

    public void setSetDy(int setDy) {
        this.setDy = setDy;
    }

    public int getAirPress() {
        return airPress;
    }

    public void setAirPress(int airPress) {
        this.airPress = airPress;
    }

    public int getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(int airTemp) {
        this.airTemp = airTemp;
    }

    public int getAirTemp1() {
        return airTemp1;
    }

    public void setAirTemp1(int airTemp1) {
        this.airTemp1 = airTemp1;
    }

    public int getEnvireTemp() {
        return envireTemp;
    }

    public void setEnvireTemp(int envireTemp) {
        this.envireTemp = envireTemp;
    }

    public int getDianjiTmep() {
        return dianjiTmep;
    }

    public void setDianjiTmep(int dianjiTmep) {
        this.dianjiTmep = dianjiTmep;
    }

    public int getZhujiDla() {
        return zhujiDla;
    }

    public void setZhujiDla(int zhujiDla) {
        this.zhujiDla = zhujiDla;
    }

    public int getZhujiDlb() {
        return zhujiDlb;
    }

    public void setZhujiDlb(int zhujiDlb) {
        this.zhujiDlb = zhujiDlb;
    }

    public int getZhujiDlc() {
        return zhujiDlc;
    }

    public void setZhujiDlc(int zhujiDlc) {
        this.zhujiDlc = zhujiDlc;
    }

    public int getModleTemp() {
        return modleTemp;
    }

    public void setModleTemp(int modleTemp) {
        this.modleTemp = modleTemp;
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
            item.setDl = obj.optInt("Set_dl");
            item.setDy = obj.optInt("Set_dy");
            item.airPress = obj.optInt("Air_press");
            item.airTemp = obj.optInt("Air_temp");
            item.airTemp1 = obj.optInt("Air_temp1");
            item.envireTemp = obj.optInt("Envire_temp");
            item.dianjiTmep = obj.optInt("Dianji_temp");
            item.zhujiDla = obj.optInt("Zhuji_dla");
            item.zhujiDlb = obj.optInt("Zhuji_dlb");
            item.zhujiDlc = obj.optInt("Zhuji_dlc");
            item.modleTemp = obj.optInt("modle_temp");
            String time = obj.optString("Get_datetime");
            item.datetime = time.substring(9, time.length()-3);

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
