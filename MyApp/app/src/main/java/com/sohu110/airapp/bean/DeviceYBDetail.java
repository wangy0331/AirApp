package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备预报详情
 * Created by Aaron on 2016/5/31.
 */
public class DeviceYBDetail implements Serializable{
    //错误返回
    private String code;
    //采集日期
    private String cjrq;
    //预警状态
    private String yjzt;
    //机器序列号
    private String jqxlh;
    //排气压力
    private String pqyl;
    //主机电流
    private String zjdl;
    //机头温度
    private String jtwd;
    //风机电流1
    private String fjdl1;
    //风机电流2
    private String fjdl2;
    //工作电压
    private String gzdy;
    //客户名称
    private String khmc;
    //客户地址
    private String khmcdz;
    //空压机品牌
    private String kyjpp;
    //空压机编号
    private String kyjbh;
    //紧急电话
    private String jjdh;
    //维护电话
    private String whdh;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCjrq() {
        return cjrq;
    }

    public void setCjrq(String cjrq) {
        this.cjrq = cjrq;
    }

    public String getYjzt() {
        return yjzt;
    }

    public void setYjzt(String yjzt) {
        this.yjzt = yjzt;
    }

    public String getJqxlh() {
        return jqxlh;
    }

    public void setJjxlh(String jqxlh) {
        this.jqxlh = jqxlh;
    }

    public String getPqyl() {
        return pqyl;
    }

    public void setPqyl(String pqyl) {
        this.pqyl = pqyl;
    }

    public String getZjdl() {
        return zjdl;
    }

    public void setZjdl(String zjdl) {
        this.zjdl = zjdl;
    }

    public String getJtwd() {
        return jtwd;
    }

    public void setJtwd(String jtwd) {
        this.jtwd = jtwd;
    }

    public String getFjdl1() {
        return fjdl1;
    }

    public void setFjdl1(String fjdl1) {
        this.fjdl1 = fjdl1;
    }

    public String getFjdl2() {
        return fjdl2;
    }

    public void setFjdl2(String fjdl2) {
        this.fjdl2 = fjdl2;
    }

    public String getGzdy() {
        return gzdy;
    }

    public void setGzdy(String gzdy) {
        this.gzdy = gzdy;
    }

    public String getKhmc() {
        return khmc;
    }

    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }

    public String getKhmcdz() {
        return khmcdz;
    }

    public void setKhmcdz(String khmcdz) {
        this.khmcdz = khmcdz;
    }

    public String getKyjpp() {
        return kyjpp;
    }

    public void setKyjpp(String kyjpp) {
        this.kyjpp = kyjpp;
    }

    public String getKyjbh() {
        return kyjbh;
    }

    public void setKyjbh(String kyjbh) {
        this.kyjbh = kyjbh;
    }

    public String getJjdh() {
        return jjdh;
    }

    public void setJjdh(String jjdh) {
        this.jjdh = jjdh;
    }

    public String getWhdh() {
        return whdh;
    }

    public void setWhdh(String whdh) {
        this.whdh = whdh;
    }

    /**
     * 解析json---预警
     * @param response
     * @return
     */
    public static Result<DeviceYBDetail> parseYJ(String response) {


        List<DeviceYBDetail> list = null;

        Result<DeviceYBDetail> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceYBDetail>();
            result.setCode(1);

            JSONArray array = obj.optJSONArray("预警详情数据");
            if (array != null) {
                list = new ArrayList<DeviceYBDetail>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    result.setData(fromJsonYJ(arrItem));
                }
            }

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return result;
    }



    /**
     * 解析json---报警
     * @param response
     * @return
     */
    public static Result<DeviceYBDetail> parseBJ(String response) {


        List<DeviceYBDetail> list = null;

        Result<DeviceYBDetail> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceYBDetail>();
            result.setCode(1);

            JSONArray array = obj.optJSONArray("报警详情数据");
            if (array != null) {
                list = new ArrayList<DeviceYBDetail>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    result.setData(fromJsonBJ(arrItem));
                }
            }

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return result;
    }

    private static DeviceYBDetail fromJsonYJ(JSONObject obj) {
        DeviceYBDetail item = null;
        try {
            item = new DeviceYBDetail();
            item.setCode(obj.getString("错误返回"));
            item.setCjrq(obj.getString("采集日期"));
            item.setYjzt(obj.getString("预警状态"));
            item.setJjxlh(obj.getString("机器序列号"));
            item.setPqyl(obj.getString("排气压力"));
            item.setZjdl(obj.getString("主机电流"));
            item.setJtwd(obj.getString("机头温度"));
            item.setFjdl1(obj.getString("风机电流1"));
            item.setFjdl2(obj.getString("风机电流2"));
            item.setGzdy(obj.getString("工作电压"));
            item.setKhmc(obj.getString("客户名称"));
            item.setKhmcdz(obj.getString("客户名称地址"));
            item.setKyjpp(obj.getString("空压机品牌"));
            item.setKyjbh(obj.getString("空压机编号"));
            item.setJjdh(obj.getString("紧急电话"));
            item.setWhdh(obj.getString("维护电话"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    private static DeviceYBDetail fromJsonBJ(JSONObject obj) {
        DeviceYBDetail item = null;
        try {
            item = new DeviceYBDetail();
            item.setCode(obj.getString("错误返回"));
            item.setCjrq(obj.getString("采集日期"));
            item.setYjzt(obj.getString("报警状态"));
            item.setJjxlh(obj.getString("机器序列号"));
            item.setPqyl(obj.getString("排气压力"));
            item.setZjdl(obj.getString("主机电流"));
            item.setJtwd(obj.getString("机头温度"));
            item.setFjdl1(obj.getString("风机电流1"));
            item.setFjdl2(obj.getString("风机电流2"));
            item.setGzdy(obj.getString("工作电压"));
            item.setKhmc(obj.getString("客户名称"));
            item.setKhmcdz(obj.getString("客户名称地址"));
            item.setKyjpp(obj.getString("空压机品牌"));
            item.setKyjbh(obj.getString("空压机编号"));
            item.setJjdh(obj.getString("紧急电话"));
            item.setWhdh(obj.getString("维护电话"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
