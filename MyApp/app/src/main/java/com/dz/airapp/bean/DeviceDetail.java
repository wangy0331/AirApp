package com.dz.airapp.bean;

import com.dz.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备详情
 * Created by Aaron on 2016/4/20.
 */
public class DeviceDetail implements Serializable {
    //空气压力
    private String airPress;
    //排气温度
    private String airTemp;
    //电机温度
    private String dianjiTemp;
    //模块温度
    private String modleTemp;
    //环境温度
    private String enviTemp;
    //延时
    private String delayDisplay;
    //设备电压
    private String currentDy;
    //主机电流A   电流*10
    private String zhujiDl1;
    //主机电流B
    private String zhujiDl2;
    //主机电流C
    private String zhujiDl3;
    //风机电流A
    private String fengjiDl1;
    //风机电流B
    private String fengjiDl2;
    //风机电流C
    private String fengjiDl3;
    //能耗高位
    private String nenghaoH;
    //能耗低位
    private String nenghaoL;
    //实际能耗高位
    private String nenghaoAH;
    //节能高位
    private String saveH;
    //节能低位
    private String saveL;
    //分离器水位
    private String fengliqi;
    //外部水箱水位
    private String shuixiang;
    //进水阀
    private String jinshui;
    //出水阀
    private String chushui;
    //风机平均电流A
    private String fengjiAvgDl;
    //风机平均电流B
    private String fengjiAvgDl1;
    //出厂日期
    private String jiqiPdate;
    //质保状态
    private String jiqiQa;
    //电机状态
    private String dianjiSta;
    //空压机状态
    private String airSta;
    //风机状态A
    private String fengjiSta1;
    //风机状态B
    private String fengjiSta2;
    //预警状态
    private String alarmSta;
    //机器时钟采集实时时间
    private String dataTime;
    //机器实时经度
    private Double jqJd;
    //机器实时纬度
    private Double jqWd;

    public Double getJqJd() {
        return jqJd;
    }

    public void setJqJd(Double jqJd) {
        this.jqJd = jqJd;
    }

    public Double getJqWd() {
        return jqWd;
    }

    public void setJqWd(Double jqWd) {
        this.jqWd = jqWd;
    }

    public String getAirPress() {
        return airPress;
    }

    public void setAirPress(String airPress) {
        this.airPress = airPress;
    }

    public String getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(String airTemp) {
        this.airTemp = airTemp;
    }

    public String getDianjiTemp() {
        return dianjiTemp;
    }

    public void setDianjiTemp(String dianjiTemp) {
        this.dianjiTemp = dianjiTemp;
    }

    public String getModleTemp() {
        return modleTemp;
    }

    public void setModleTemp(String modleTemp) {
        this.modleTemp = modleTemp;
    }

    public String getEnviTemp() {
        return enviTemp;
    }

    public void setEnviTemp(String enviTemp) {
        this.enviTemp = enviTemp;
    }

    public String getDelayDisplay() {
        return delayDisplay;
    }

    public void setDelayDisplay(String delayDisplay) {
        this.delayDisplay = delayDisplay;
    }

    public String getCurrentDy() {
        return currentDy;
    }

    public void setCurrentDy(String currentDy) {
        this.currentDy = currentDy;
    }

    public String getZhujiDl1() {
        return zhujiDl1;
    }

    public void setZhujiDl1(String zhujiDl1) {
        this.zhujiDl1 = zhujiDl1;
    }

    public String getZhujiDl2() {
        return zhujiDl2;
    }

    public void setZhujiDl2(String zhujiDl2) {
        this.zhujiDl2 = zhujiDl2;
    }

    public String getZhujiDl3() {
        return zhujiDl3;
    }

    public void setZhujiDl3(String zhujiDl3) {
        this.zhujiDl3 = zhujiDl3;
    }

    public String getFengjiDl1() {
        return fengjiDl1;
    }

    public void setFengjiDl1(String fengjiDl1) {
        this.fengjiDl1 = fengjiDl1;
    }

    public String getFengjiDl2() {
        return fengjiDl2;
    }

    public void setFengjiDl2(String fengjiDl2) {
        this.fengjiDl2 = fengjiDl2;
    }

    public String getFengjiDl3() {
        return fengjiDl3;
    }

    public void setFengjiDl3(String fengjiDl3) {
        this.fengjiDl3 = fengjiDl3;
    }

    public String getNenghaoH() {
        return nenghaoH;
    }

    public void setNenghaoH(String nenghaoH) {
        this.nenghaoH = nenghaoH;
    }

    public String getNenghaoL() {
        return nenghaoL;
    }

    public void setNenghaoL(String nenghaoL) {
        this.nenghaoL = nenghaoL;
    }

    public String getNenghaoAH() {
        return nenghaoAH;
    }

    public void setNenghaoAH(String nenghaoAH) {
        this.nenghaoAH = nenghaoAH;
    }

    public String getSaveH() {
        return saveH;
    }

    public void setSaveH(String saveH) {
        this.saveH = saveH;
    }

    public String getSaveL() {
        return saveL;
    }

    public void setSaveL(String saveL) {
        this.saveL = saveL;
    }

    public String getFengliqi() {
        return fengliqi;
    }

    public void setFengliqi(String fengliqi) {
        this.fengliqi = fengliqi;
    }

    public String getShuixiang() {
        return shuixiang;
    }

    public void setShuixiang(String shuixiang) {
        this.shuixiang = shuixiang;
    }

    public String getJinshui() {
        return jinshui;
    }

    public void setJinshui(String jinshui) {
        this.jinshui = jinshui;
    }

    public String getChushui() {
        return chushui;
    }

    public void setChushui(String chushui) {
        this.chushui = chushui;
    }

    public String getFengjiAvgDl() {
        return fengjiAvgDl;
    }

    public void setFengjiAvgDl(String fengjiAvgDl) {
        this.fengjiAvgDl = fengjiAvgDl;
    }

    public String getFengjiAvgDl1() {
        return fengjiAvgDl1;
    }

    public void setFengjiAvgDl1(String fengjiAvgDl1) {
        this.fengjiAvgDl1 = fengjiAvgDl1;
    }

    public String getJiqiPdate() {
        return jiqiPdate;
    }

    public void setJiqiPdate(String jiqiPdate) {
        this.jiqiPdate = jiqiPdate;
    }

    public String getJiqiQa() {
        return jiqiQa;
    }

    public void setJiqiQa(String jiqiQa) {
        this.jiqiQa = jiqiQa;
    }

    public String getDianjiSta() {
        return dianjiSta;
    }

    public void setDianjiSta(String dianjiSta) {
        this.dianjiSta = dianjiSta;
    }

    public String getAirSta() {
        return airSta;
    }

    public void setAirSta(String airSta) {
        this.airSta = airSta;
    }

    public String getFengjiSta1() {
        return fengjiSta1;
    }

    public void setFengjiSta1(String fengjiSta1) {
        this.fengjiSta1 = fengjiSta1;
    }

    public String getFengjiSta2() {
        return fengjiSta2;
    }

    public void setFengjiSta2(String fengjiSta2) {
        this.fengjiSta2 = fengjiSta2;
    }

    public String getAlarmSta() {
        return alarmSta;
    }

    public void setAlarmSta(String alarmSta) {
        this.alarmSta = alarmSta;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }


    /**
     * 解析json
     * @param response
     * @return
     */
    public static Result<DeviceDetail> parse(String response) {


        List<DeviceDetail> list = null;

        Result<DeviceDetail> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceDetail>();
            result.setCode(1);
//            JSONObject objInfo = obj.getJSONObject("Setdata_detail");


            JSONArray array = obj.optJSONArray("Setdata_detail");
            if (array != null) {
                list = new ArrayList<DeviceDetail>();
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

    private static DeviceDetail fromJson(JSONObject obj) {
        DeviceDetail item = null;
        try {
            item = new DeviceDetail();
            item.setAirPress(obj.getString("Air_press"));
            item.setAirTemp(obj.getString("air_temp1"));
            item.setDianjiTemp(obj.getString("dianji_temp"));
            item.setModleTemp(obj.getString("Modle_temp"));
            item.setEnviTemp(obj.getString("Envi_temp"));
            item.setDelayDisplay(obj.getString("Delay_display"));
            item.setCurrentDy(obj.getString("Current_dy"));
            item.setZhujiDl1(obj.getString("Zhuji_dl1"));
            item.setZhujiDl2(obj.getString("Zhuji_dl2"));
            item.setZhujiDl3(obj.getString("Zhuji_dl3"));
            item.setFengjiDl1(obj.getString("Fengji_dl1"));
            item.setFengjiDl2(obj.getString("Fengji_dl2"));
            item.setFengjiDl3(obj.getString("Fengji_dl3"));
            item.setNenghaoH(obj.getString("Nenghao_h"));
            item.setNenghaoL(obj.getString("Nenghao_l"));
            item.setNenghaoAH(obj.getString("Nenghao_ah"));
            item.setSaveH(obj.getString("Save_h"));
            item.setSaveL(obj.getString("Save_L"));
            item.setFengliqi(obj.getString("Fengliqi"));
            item.setShuixiang(obj.getString("Shuixiang"));
            item.setJinshui(obj.getString("Jinshui"));
            item.setChushui(obj.getString("Chushui"));
            item.setFengjiAvgDl(obj.getString("Fengjiavgdl"));
            item.setFengjiAvgDl1(obj.getString("Fengjiavgdl1"));
            item.setJiqiPdate(obj.getString("JiQi_Pdate"));
            item.setJiqiQa(obj.getString("JiQi_qa"));
            item.setDianjiSta(obj.getString("Dianji_sta"));
            item.setAirSta(obj.getString("Air_sta"));
            item.setFengjiSta1(obj.getString("Fengji_sta1"));
            item.setFengjiSta2(obj.getString("Fengji_sta2"));
            item.setAlarmSta(obj.getString("Alarm_sta"));
            item.setDataTime(obj.getString("Data_time"));
            item.setJqJd(obj.getDouble("JQ_JD"));
            item.setJqWd(obj.getDouble("JQ_WD"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
