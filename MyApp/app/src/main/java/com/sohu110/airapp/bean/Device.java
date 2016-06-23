package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备列表item
 * Created by Aaron on 2016/4/18.
 */
public class Device implements Serializable {
    //记录状态
    private String jlSta;
    //登录账号
    private String zhanghao;
    //机器序列号
    private String jiqiSn;
    //权限（C为控制，R为显示）
    private String auth;
    //机器类型
    private String jqName;
    //机器经度
    private Double jqWD;
    //机器纬度
    private Double jqJD;
    //设备状态
    private String jqStatus;
    //机器注册日期
    private String jiqiNewDate;
    //机器生产日期
    private String jiqiPdate;
    //城市
    private String region;
    //客户名称
    private String coName;
    //是否关注（Y关注，N没关注）
    private String fSta;
    //空压机编号
    private String airSn;
    //温度
    private String temp;
    //压力
    private String press;
    //时间
    private String time;
    //状态
    private String errorSta;
    //报警信息
    private String xinxi;
    //报警时间
    private String shijian;
    //预计下次保养时间
    private String xcbysq;
    //预计剩余保养天数
    private String sybyts;

    public Double getJqWD() {
        return jqWD;
    }

    public void setJqWD(Double jqWD) {
        this.jqWD = jqWD;
    }

    public Double getJqJD() {
        return jqJD;
    }

    public void setJqJD(Double jqJD) {
        this.jqJD = jqJD;
    }

    public String getJiqiSn() {
        return jiqiSn;
    }

    public void setJiqiSn(String jiqiSn) {
        this.jiqiSn = jiqiSn;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getJqName() {
        return jqName;
    }

    public void setJqName(String jqName) {
        this.jqName = jqName;
    }

    public String getJqStatus() {
        return jqStatus;
    }

    public void setJqStatus(String jqStatus) {
        this.jqStatus = jqStatus;
    }

    public String getJiqiNewDate() {
        return jiqiNewDate;
    }

    public void setJiqiNewDate(String jiqiNewDate) {
        this.jiqiNewDate = jiqiNewDate;
    }

    public String getJiqiPdate() {
        return jiqiPdate;
    }

    public void setJiqiPdate(String jiqiPdate) {
        this.jiqiPdate = jiqiPdate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getfSta() {
        return fSta;
    }

    public void setfSta(String fSta) {
        this.fSta = fSta;
    }

    public String getAirSn() {
        return airSn;
    }

    public void setAirSn(String airSn) {
        this.airSn = airSn;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getErrorSta() {
        return errorSta;
    }

    public void setErrorSta(String errorSta) {
        this.errorSta = errorSta;
    }

    public String getJlSta() {
        return jlSta;
    }

    public void setJlSta(String jlSta) {
        this.jlSta = jlSta;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getXinxi() {
        return xinxi;
    }

    public void setXinxi(String xinxi) {
        this.xinxi = xinxi;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getXcbysq() {
        return xcbysq;
    }

    public void setXcbysq(String xcbysq) {
        this.xcbysq = xcbysq;
    }

    public String getSybyts() {
        return sybyts;
    }

    public void setSybyts(String sybyts) {
        this.sybyts = sybyts;
    }

    /**
     * 解析JSON数据---收藏列表
     * @param json
     * @return
     */
    public static Result<List<Device>> parseSC(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);

            JSONArray array = obj.optJSONArray("SetFlowlist");
            if (array != null) {
                list = new ArrayList<Device>();
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

    /**
     * 解析JSON数据---设备列表
     * @param json
     * @return
     */
    public static Result<List<Device>> parseSB(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);


            JSONArray array = obj.optJSONArray("Productlist");
            if (array != null) {
                list = new ArrayList<Device>();
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

    /**
     * 解析JSON数据---维保列表
     * @param json
     * @return
     */
    public static Result<List<Device>> parseWB(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);


            JSONArray array = obj.optJSONArray("设备维保清单");
            if (array != null) {
                list = new ArrayList<Device>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonWB(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }


    /**
     * 解析JSON数据---预警列表
     * @param json
     * @return
     */
    public static Result<List<Device>> parseYJ(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);


            JSONArray array = obj.optJSONArray("预警设备清单");
            if (array != null) {
                list = new ArrayList<Device>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonYJ(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析JSON数据---报警列表
     * @param json
     * @return
     */
    public static Result<List<Device>> parseBJ(String json) {
        Result<List<Device>> deviceList = null;
        List<Device> list = null;

        try {

            JSONObject obj = new JSONObject(json);
            deviceList = new Result<List<Device>>();
            deviceList.setCode(1);


            JSONArray array = obj.optJSONArray("报警设备清单");
            if (array != null) {
                list = new ArrayList<Device>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrItem = array.getJSONObject(i);
                    list.add(fromJsonBJ(arrItem));
                }
                deviceList.setData(list);
            }
        } catch (Exception e) {
            Logger.e("", "", e);
        }

        return deviceList;
    }

    /**
     * 解析item
     * @return
     */
    public static Device fromJson(JSONObject obj) {
        Device item = null;
        try {
            item = new Device();
            item.jiqiSn = obj.optString("Jiqi_sn");
            item.auth = obj.optString("Auth");
            item.jqName = obj.optString("JQ_Name");
//            if (!"".equals(obj.optDouble("纬度"))) {
//                item.jqWD = obj.optDouble("纬度");
//            }
//            if (!"".equals(obj.optDouble("经度"))) {
//                item.jqJD = obj.optDouble("经度");
//            }
            item.jqWD = obj.optDouble("纬度");
            item.jqJD = obj.optDouble("经度");
            item.jqStatus = obj.optString("JQ_status");
            item.jiqiNewDate = obj.optString("JQ_newDate");
            item.jiqiPdate = obj.optString("JiQi_Pdate");
            item.region = obj.optString("Region");
            item.coName = obj.optString("CoName");
            item.fSta = obj.optString("F_sta");
            item.airSn = obj.optString("Air_sn");
            item.temp = obj.optString("temp");
            item.press = obj.optString("press");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item ---维保
     * @return
     */
    public static Device fromJsonWB(JSONObject obj) {
        Device item = null;
        try {
            item = new Device();
            item.zhanghao = obj.optString("登录帐户");
            item.jiqiSn = obj.optString("机器序列号");
            item.fSta = obj.optString("关注状态");
            item.time = obj.optString("采集时间");
            item.jqStatus = obj.optString("设备状态");
            item.region = obj.optString("城市");
            item.coName = obj.optString("客户名称");
            item.airSn = obj.optString("空压机编号");
            item.temp = obj.optString("温度");
            item.xcbysq = obj.optString("预计下次保养日期");
            item.sybyts = obj.optString("预计剩余保养天数");
            item.press = obj.optString("压力");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item-----中文预警
     * @return
     */
    public static Device fromJsonYJ(JSONObject obj) {
        Device item = null;
        try {
            item = new Device();
            item.jlSta = obj.optString("记录状态");
            item.zhanghao = obj.optString("登录帐户");
            item.jiqiSn = obj.optString("机器序列号");
            item.jqName = obj.optString("客户名称");
            item.jqWD = obj.optDouble("纬度");
            item.jqJD = obj.optDouble("经度");
            item.jqStatus = obj.optString("设备状态");
            item.region = obj.optString("城市");
            item.coName = obj.optString("客户名称");
            item.fSta = obj.optString("关注状态");
            item.airSn = obj.optString("空压机编号");
            item.temp = obj.optString("温度");
            item.press = obj.optString("压力");
            item.xinxi = obj.optString("预警信息");
            item.shijian = obj.optString("预警时间");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

    /**
     * 解析item-----中文预警
     * @return
     */
    public static Device fromJsonBJ(JSONObject obj) {
        Device item = null;
        try {
            item = new Device();
            item.jlSta = obj.optString("记录状态");
            item.zhanghao = obj.optString("登录帐户");
            item.jiqiSn = obj.optString("机器序列号");
            item.jqName = obj.optString("客户名称");
            item.jqWD = obj.optDouble("纬度");
            item.jqJD = obj.optDouble("经度");
            item.jqStatus = obj.optString("设备状态");
            item.region = obj.optString("城市");
            item.coName = obj.optString("客户名称");
            item.fSta = obj.optString("关注状态");
            item.airSn = obj.optString("空压机编号");
            item.temp = obj.optString("温度");
            item.press = obj.optString("压力");
            item.xinxi = obj.optString("报警信息");
            item.shijian = obj.optString("报警时间");

        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
