package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 维保信息
 * Created by Aaron on 2016/6/1.
 */
public class DeviceWBDetail implements Serializable {
    //天数
    private String ts;
    //下次维保
    private String xcwb;
    //油滤时间
    private String ylsj;
    //油分时间
    private String yfsj;
    //空滤时间
    private String klsj;
    //润油时间
    private String rysj;
    //油脂时间
    private String yzsj;
    //皮带时间
    private String pdsj;
    //油滤次数
    private String ylcs;
    //油分次数
    private String yfcs;
    //空滤次数
    private String klcs;
    //润油次数
    private String rycs;
    //油脂次数
    private String yzcs;
    //皮带次数
    private String pdcs;
    //累计运行时间
    private String ljyxsj;
    //累计加载时间
    private String ljjzsj;
    //本次运行时间
    private String bcyxsj;
    //本次加载时间
    private String bcjzsj;
    //采集时间
    private String cjsj;
    //维保状态
    private String wbzt;
    //机器序列号
    private String jQsn;

    public String getjQsn() {
        return jQsn;
    }

    public void setjQsn(String jQsn) {
        this.jQsn = jQsn;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getXcwb() {
        return xcwb;
    }

    public void setXcwb(String xcwb) {
        this.xcwb = xcwb;
    }

    public String getYlsj() {
        return ylsj;
    }

    public void setYlsj(String ylsj) {
        this.ylsj = ylsj;
    }

    public String getYfsj() {
        return yfsj;
    }

    public void setYfsj(String yfsj) {
        this.yfsj = yfsj;
    }

    public String getKlsj() {
        return klsj;
    }

    public void setKlsj(String klsj) {
        this.klsj = klsj;
    }

    public String getRysj() {
        return rysj;
    }

    public void setRysj(String rysj) {
        this.rysj = rysj;
    }

    public String getYzsj() {
        return yzsj;
    }

    public void setYzsj(String yzsj) {
        this.yzsj = yzsj;
    }

    public String getPdsj() {
        return pdsj;
    }

    public void setPdsj(String pdsj) {
        this.pdsj = pdsj;
    }

    public String getYlcs() {
        return ylcs;
    }

    public void setYlcs(String ylcs) {
        this.ylcs = ylcs;
    }

    public String getYfcs() {
        return yfcs;
    }

    public void setYfcs(String yfcs) {
        this.yfcs = yfcs;
    }

    public String getKlcs() {
        return klcs;
    }

    public void setKlcs(String klcs) {
        this.klcs = klcs;
    }

    public String getRycs() {
        return rycs;
    }

    public void setRycs(String rycs) {
        this.rycs = rycs;
    }

    public String getYzcs() {
        return yzcs;
    }

    public void setYzcs(String yzcs) {
        this.yzcs = yzcs;
    }

    public String getPdcs() {
        return pdcs;
    }

    public void setPdcs(String pdcs) {
        this.pdcs = pdcs;
    }

    public String getLjyxsj() {
        return ljyxsj;
    }

    public void setLjyxsj(String ljyxsj) {
        this.ljyxsj = ljyxsj;
    }

    public String getLjjzsj() {
        return ljjzsj;
    }

    public void setLjjzsj(String ljjzsj) {
        this.ljjzsj = ljjzsj;
    }

    public String getBcyxsj() {
        return bcyxsj;
    }

    public void setBcyxsj(String bcyxsj) {
        this.bcyxsj = bcyxsj;
    }

    public String getBcjzsj() {
        return bcjzsj;
    }

    public void setBcjzsj(String bcjzsj) {
        this.bcjzsj = bcjzsj;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getWbzt() {
        return wbzt;
    }

    public void setWbzt(String wbzt) {
        this.wbzt = wbzt;
    }

    /**
     * 解析json---维保
     * @param response
     * @return
     */
    public static Result<DeviceWBDetail> parseWB(String response) {


        List<DeviceWBDetail> list = null;

        Result<DeviceWBDetail> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<DeviceWBDetail>();
            result.setCode(1);

            JSONArray array = obj.optJSONArray("维保详情");
            if (array != null) {
                list = new ArrayList<DeviceWBDetail>();
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

    private static DeviceWBDetail fromJson(JSONObject obj) {
        DeviceWBDetail item = null;
        try {
            item = new DeviceWBDetail();
            item.setTs(obj.getString("预计下次保养天数"));
            item.setXcwb(obj.getString("预计下次保养日期"));
            item.setCjsj(obj.getString("采集日期"));
            item.setWbzt(obj.getString("维保状态"));
            item.setjQsn(obj.getString("机器序列号"));
            item.setYlsj(obj.getString("油滤时间"));
            item.setYfsj(obj.getString("油分时间"));
            item.setKlsj(obj.getString("空滤时间"));
            item.setRysj(obj.getString("润油时间"));
            item.setYzsj(obj.getString("润脂时间"));
            item.setPdsj(obj.getString("皮带时间"));
            item.setYlcs(obj.getString("油滤维保次数"));
            item.setYfcs(obj.getString("油分维保次数"));
            item.setYzcs(obj.getString("油脂维保次数"));
            item.setKlcs(obj.getString("空滤维保次数"));
            item.setPdcs(obj.getString("皮带维护次数"));
            item.setRycs(obj.getString("润油维保次数"));
            item.setLjyxsj(obj.getString("累积运行时间"));
            item.setLjjzsj(obj.getString("累积加载时间"));
            item.setBcyxsj(obj.getString("本次运行时间"));
            item.setBcjzsj(obj.getString("本次加载时间"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }

}
