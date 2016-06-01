package com.sohu110.airapp.bean;

import java.io.Serializable;

/**
 * 预警详情
 * Created by Aaron on 2016/5/29.
 */
public class DeviceYujing implements Serializable {
    //日期
    private String data;
    //排气压力
    private String mPress;
    //机头温度
    private String mTemp;
    //主机风流
    private String mDianliu;
    //工作电压
    private String mDianya;
    //风机1电流
    private String fengji1;
    //风机2电流
    private String fengji2;
    //客户名称
    private String name;
    //客户地址
    private String add;
    //空压机品牌
    private String brand;
    //空压机编号
    private String sn;
    //紧急电话
    private String emsPhone;
    //维护电话
    private String whPhone;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getmPress() {
        return mPress;
    }

    public void setmPress(String mPress) {
        this.mPress = mPress;
    }

    public String getmTemp() {
        return mTemp;
    }

    public void setmTemp(String mTemp) {
        this.mTemp = mTemp;
    }

    public String getmDianliu() {
        return mDianliu;
    }

    public void setmDianliu(String mDianliu) {
        this.mDianliu = mDianliu;
    }

    public String getmDianya() {
        return mDianya;
    }

    public void setmDianya(String mDianya) {
        this.mDianya = mDianya;
    }

    public String getFengji1() {
        return fengji1;
    }

    public void setFengji1(String fengji1) {
        this.fengji1 = fengji1;
    }

    public String getFengji2() {
        return fengji2;
    }

    public void setFengji2(String fengji2) {
        this.fengji2 = fengji2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmsPhone() {
        return emsPhone;
    }

    public void setEmsPhone(String emsPhone) {
        this.emsPhone = emsPhone;
    }

    public String getWhPhone() {
        return whPhone;
    }

    public void setWhPhone(String whPhone) {
        this.whPhone = whPhone;
    }
}
