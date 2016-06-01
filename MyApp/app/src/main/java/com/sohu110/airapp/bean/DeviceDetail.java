package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

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
    //机器序列号
    private String jqQiSn;
    //排气压力
    private String airPress;
    //机头温度
    private String airTemp;
    //电机温度
    private String dianjiTemp;
    //电气温度
    private String modleTemp;
    //环境温度
    private String enviTemp;
    //电机状态
    private String dianjiSta;
    //空压机状态
    private String airSta;
    //风机1状态
    private String fengjiSta1;
    //风机2状态
    private String fengjiSta2;
    //节能率
    private String jienengLv;
    //节能电能
    private String jienengDianneng;
    //实际耗能
    private String sjHaoneng;
    //设备运行
    private String deviceSta;
    //通讯状况
    private String tongxunSta;
    //主机电流A   电流*10
    private String zhujiDl1;
    //主机电流B
    private String zhujiDl2;
    //主机电流C
    private String zhujiDl3;
    //风机1电流A
    private String fengji1Dl1;
    //风机1电流B
    private String fengji1Dl2;
    //风机1电流C
    private String fengji1Dl3;
    //风机2电流A
    private String fengji2Dl1;
    //风机2电流B
    private String fengji2Dl2;
    //风机2电流C
    private String fengji2Dl3;
    //设备电压
    private String currentDy;
    //加载次数
    private String jiazaiCs;
    //累积运行时间
    private String leijiYx;
    //累积加载时间
    private String leijiJz;
    //本次运行时间
    private String benciYx;
    //本次加载时间
    private String benciJz;
    //电源频率
    private String dianyuanPl;
    //设备功率
    private String deviceGl;
    //每天平均运行时间
    private String dayPjSj;
    //客户名称
    private String kehuMc;
    //空压机编号
    private String kyjBianhao;
    //空压机品牌
    private String kyjPp;
    //主机软件版本
    private String rjbb;
    //主机硬件版本
    private String yjbb;
    //空压机序列号
    private String kyjSn;
    //机器实时经度
    private Double jqJd;
    //机器实时纬度
    private Double jqWd;
    //采集时间
    private String nowTime;
    //状态
    private String status;

    public String getJqQiSn() {
        return jqQiSn;
    }

    public void setJqQiSn(String jqQiSn) {
        this.jqQiSn = jqQiSn;
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

    public String getJienengLv() {
        return jienengLv;
    }

    public void setJienengLv(String jienengLv) {
        this.jienengLv = jienengLv;
    }

    public String getJienengDianneng() {
        return jienengDianneng;
    }

    public void setJienengDianneng(String jienengDianneng) {
        this.jienengDianneng = jienengDianneng;
    }

    public String getSjHaoneng() {
        return sjHaoneng;
    }

    public void setSjHaoneng(String sjHaoneng) {
        this.sjHaoneng = sjHaoneng;
    }

    public String getDeviceSta() {
        return deviceSta;
    }

    public void setDeviceSta(String deviceSta) {
        this.deviceSta = deviceSta;
    }

    public String getTongxunSta() {
        return tongxunSta;
    }

    public void setTongxunSta(String tongxunSta) {
        this.tongxunSta = tongxunSta;
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

    public String getFengji1Dl1() {
        return fengji1Dl1;
    }

    public void setFengji1Dl1(String fengji1Dl1) {
        this.fengji1Dl1 = fengji1Dl1;
    }

    public String getFengji1Dl2() {
        return fengji1Dl2;
    }

    public void setFengji1Dl2(String fengji1Dl2) {
        this.fengji1Dl2 = fengji1Dl2;
    }

    public String getFengji1Dl3() {
        return fengji1Dl3;
    }

    public void setFengji1Dl3(String fengji1Dl3) {
        this.fengji1Dl3 = fengji1Dl3;
    }

    public String getFengji2Dl1() {
        return fengji2Dl1;
    }

    public void setFengji2Dl1(String fengji2Dl1) {
        this.fengji2Dl1 = fengji2Dl1;
    }

    public String getFengji2Dl2() {
        return fengji2Dl2;
    }

    public void setFengji2Dl2(String fengji2Dl2) {
        this.fengji2Dl2 = fengji2Dl2;
    }

    public String getFengji2Dl3() {
        return fengji2Dl3;
    }

    public void setFengji2Dl3(String fengji2Dl3) {
        this.fengji2Dl3 = fengji2Dl3;
    }

    public String getCurrentDy() {
        return currentDy;
    }

    public void setCurrentDy(String currentDy) {
        this.currentDy = currentDy;
    }

    public String getJiazaiCs() {
        return jiazaiCs;
    }

    public void setJiazaiCs(String jiazaiCs) {
        this.jiazaiCs = jiazaiCs;
    }

    public String getLeijiYx() {
        return leijiYx;
    }

    public void setLeijiYx(String leijiYx) {
        this.leijiYx = leijiYx;
    }

    public String getLeijiJz() {
        return leijiJz;
    }

    public void setLeijiJz(String leijiJz) {
        this.leijiJz = leijiJz;
    }

    public String getBenciYx() {
        return benciYx;
    }

    public void setBenciYx(String benciYx) {
        this.benciYx = benciYx;
    }

    public String getBenciJz() {
        return benciJz;
    }

    public void setBenciJz(String benciJz) {
        this.benciJz = benciJz;
    }

    public String getDianyuanPl() {
        return dianyuanPl;
    }

    public void setDianyuanPl(String dianyuanPl) {
        this.dianyuanPl = dianyuanPl;
    }

    public String getDeviceGl() {
        return deviceGl;
    }

    public void setDeviceGl(String deviceGl) {
        this.deviceGl = deviceGl;
    }

    public String getDayPjSj() {
        return dayPjSj;
    }

    public void setDayPjSj(String dayPjSj) {
        this.dayPjSj = dayPjSj;
    }

    public String getKehuMc() {
        return kehuMc;
    }

    public void setKehuMc(String kehuMc) {
        this.kehuMc = kehuMc;
    }

    public String getKyjBianhao() {
        return kyjBianhao;
    }

    public void setKyjBianhao(String kyjBianhao) {
        this.kyjBianhao = kyjBianhao;
    }

    public String getKyjPp() {
        return kyjPp;
    }

    public void setKyjPp(String kyjPp) {
        this.kyjPp = kyjPp;
    }

    public String getRjbb() {
        return rjbb;
    }

    public void setRjbb(String rjbb) {
        this.rjbb = rjbb;
    }

    public String getYjbb() {
        return yjbb;
    }

    public void setYjbb(String yjbb) {
        this.yjbb = yjbb;
    }

    public String getKyjSn() {
        return kyjSn;
    }

    public void setKyjSn(String kyjSn) {
        this.kyjSn = kyjSn;
    }

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

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            item.setJqQiSn(obj.getString("机器序列号"));
            item.setAirPress(obj.getString("排气压力"));
            item.setAirTemp(obj.getString("机头温度"));
            item.setDianjiTemp(obj.getString("电机温度"));
            item.setModleTemp(obj.getString("电气温度"));
            item.setEnviTemp(obj.getString("环境温度"));
            item.setDianjiSta(obj.getString("电机状态"));
            item.setAirSta(obj.getString("空压机状态"));
            item.setFengjiSta1(obj.getString("风机1状态"));
            item.setFengjiSta2(obj.getString("风机2状态"));
            item.setJienengLv(obj.getString("节能率"));
            item.setJienengDianneng(obj.getString("节能电能"));
            item.setSjHaoneng(obj.getString("实际耗能"));
            item.setDeviceSta(obj.getString("设备运行"));
            item.setTongxunSta(obj.getString("通讯状况"));
            item.setZhujiDl1(obj.getString("主机电流A"));
            item.setZhujiDl2(obj.getString("主机电流B"));
            item.setZhujiDl3(obj.getString("主机电流C"));
            item.setFengji1Dl1(obj.getString("风机1#电流A"));
            item.setFengji1Dl2(obj.getString("风机1#电流B"));
            item.setFengji1Dl3(obj.getString("风机1#电流C"));
            item.setFengji2Dl1(obj.getString("风机2#电流A"));
            item.setFengji2Dl2(obj.getString("风机2#电流B"));
            item.setFengji2Dl3(obj.getString("风机2#电流C"));
            item.setCurrentDy(obj.getString("设备电压"));
            item.setJiazaiCs(obj.getString("加载次数"));
            item.setLeijiYx(obj.getString("累积运行时间"));
            item.setLeijiJz(obj.getString("累积加载时间"));
            item.setBenciYx(obj.getString("本次运行时间"));
            item.setBenciJz(obj.getString("本次加载时间"));
            item.setDianyuanPl(obj.getString("电源频率"));
            item.setDeviceGl(obj.getString("设备功率"));
            item.setDayPjSj(obj.getString("每天平均运行时间"));
            item.setKehuMc(obj.getString("客户名称"));
            item.setKyjBianhao(obj.getString("空压机编号"));
            item.setKyjPp(obj.getString("空压机品牌"));
            item.setRjbb(obj.getString("主机软件版本"));
            item.setYjbb(obj.getString("主机硬件版本"));
            item.setKyjSn(obj.getString("空压机序列号"));
            item.setJqJd(obj.getDouble("经度"));
            item.setJqWd(obj.getDouble("纬度"));
            item.setNowTime(obj.getString("采集日期"));
            item.setStatus(obj.getString("预/报警状态"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
