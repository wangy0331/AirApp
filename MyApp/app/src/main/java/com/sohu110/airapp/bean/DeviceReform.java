package com.sohu110.airapp.bean;

import java.io.Serializable;

/**
 * 设备改造
 * Created by Aaron on 2016/5/24.
 */
public class DeviceReform implements Serializable {
    //设备改造序列号
    private String setNum;
    //排气温度传感器
    private String PaiqiTemp;
    //加载阀
    private String Jiazaifa;
    //压力传感器
    private String PressCgq;
    //安装尺寸
    private String InstallChicun;
    //显示器尺寸
    private String DisplayChicun;
    //电机尺寸
    private String DianjiChicun;
    //电源线尺寸
    private String PowerChicun;
    //风机数量
    private String FanKw;
    //图片地址
    private String picUrl;

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public String getPaiqiTemp() {
        return PaiqiTemp;
    }

    public void setPaiqiTemp(String paiqiTemp) {
        PaiqiTemp = paiqiTemp;
    }

    public String getJiazaifa() {
        return Jiazaifa;
    }

    public void setJiazaifa(String jiazaifa) {
        Jiazaifa = jiazaifa;
    }

    public String getPressCgq() {
        return PressCgq;
    }

    public void setPressCgq(String pressCgq) {
        PressCgq = pressCgq;
    }

    public String getInstallChicun() {
        return InstallChicun;
    }

    public void setInstallChicun(String installChicun) {
        InstallChicun = installChicun;
    }

    public String getDisplayChicun() {
        return DisplayChicun;
    }

    public void setDisplayChicun(String displayChicun) {
        DisplayChicun = displayChicun;
    }

    public String getDianjiChicun() {
        return DianjiChicun;
    }

    public void setDianjiChicun(String dianjiChicun) {
        DianjiChicun = dianjiChicun;
    }

    public String getPowerChicun() {
        return PowerChicun;
    }

    public void setPowerChicun(String powerChicun) {
        PowerChicun = powerChicun;
    }

    public String getFanKw() {
        return FanKw;
    }

    public void setFanKw(String fanKw) {
        FanKw = fanKw;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
