package com.sohu110.airapp.service;

import android.text.TextUtils;
import android.util.Log;

import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.DeviceChart;
import com.sohu110.airapp.bean.DeviceDetail;
import com.sohu110.airapp.bean.DeviceInfo;
import com.sohu110.airapp.bean.DeviceLog;
import com.sohu110.airapp.bean.DeviceReform;
import com.sohu110.airapp.bean.DeviceThree;
import com.sohu110.airapp.bean.DeviceWBDetail;
import com.sohu110.airapp.bean.DeviceYBDetail;
import com.sohu110.airapp.bean.Energy;
import com.sohu110.airapp.bean.MemberDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.bean.User;
import com.sohu110.airapp.cache.CacheCenter;
import com.sohu110.airapp.net.URLCenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Aaron on 2016/4/6.
 */
public class ServiceCenter {

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     * @throws IOException
     */
    public static Result<User> login(String phone, String password) throws IOException,JSONException {

        //接口路径
        String url = URLCenter.getApi("P_checkuser.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("userid", phone);
        obj.put("userpwd", password);

        //发送请求
        String response = HttpService.post(url, obj);

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }
        return result;
    }

    /**
     * 注册
     * @param mPhone
     * @param mPwd
     * @param mCode
     * @return
     */
    public static Result register(String mPhone, String mPwd, String mCode) throws Exception{
        //接口路径
        String url = URLCenter.getApi("registeruser.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", mPhone);
        obj.put("userpwd", mPwd);
        obj.put("mobilevote", mCode);
        //发送请求
        String response = HttpService.post(url, obj);

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }

        return result;
    }

    /**
     * 重置密码
     * @param mPhone
     * @param mPwd
     * @param mCode
     * @return
     */
    public static Result
    reset(String mPhone, String mPwd, String mCode) throws Exception{
        //接口路径
        String url = URLCenter.getApi("restpwd.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", mPhone);
        obj.put("newpwd", mPwd);
        obj.put("mobilevote", mCode);
        //发送请求
        String response = HttpService.post(url, obj);

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }

        return result;
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public static Result getCode(String phone, String func) throws Exception{
        //接口路径
        String url = URLCenter.getApi("getsms.asq");



        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid","AirApp");
        obj.put("func",func);
        obj.put("mobile", phone);


        //发送请求
        String response = HttpService.post(url, obj);

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }
        return result;
    }

    /**
     * 设备注册
     * @param scan
     * @return
     */
    public static Result<DeviceThree> deviceRegister(String scan) throws Exception{
        //接口路径
        String url = URLCenter.getApi("regset.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setnum", scan);

        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceThree.parse(response);
        }
        return null;
    }

    /**
     * 设备列表
     * @param mCondition
     * @return
     */
    public static Result<List<Device>> deviceList(String mCondition, String content) throws Exception{

        Log.e("mCondition", mCondition);
        Log.e("content", content);

        //接口路径
        String url = URLCenter.getApi("querysets1.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        obj.put("content", new String(content.getBytes("utf-8"), "ISO-8859-1"));
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parseSB(response);
        }
        return null;
    }


    /**
     * 获取详情
     * @param jiqiSn
     * @return
     * @throws Exception
     */
    public static Result<DeviceDetail> getDetail(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetsdata1.asq");


        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceDetail.parse(response);
        }
        return null;
    }

    /**
     * 获取曲线图
     *
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceChart>> getChart(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetschart1.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("condition", "now");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceChart.parse(response);
        }
        return null;
    }

    /**
     * 每日签到
     * @return
     */
    public static Result<List<Device>> daySign() throws Exception{
        //接口路径
        String url = URLCenter.getApi("Signday.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 关注
     * @param jqSN
     * @return
     */
    public static Result deviceSC(String jqSN) throws Exception{
        //接口路径
        String url = URLCenter.getApi("Follow.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("jiqi_sn", jqSN);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 取消关注
     * @param jqSN
     * @return
     */
    public static Result<List<Device>> deviceNSC(String jqSN) throws Exception{
        //接口路径
        String url = URLCenter.getApi("Canclefollow.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("jiqi_sn", jqSN);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }


    /**
     * 总能耗
     * @param mCondition
     * @return
     * @throws Exception
     */
    public static Result<List<Energy>> getEnergy(String mCondition) throws Exception{

        Log.e("mCondition", mCondition);

        //接口路径
        String url = URLCenter.getApi("querynh.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            if ("sday".equals(mCondition)) {
                return Energy.parseSday(response);
            } else if ("moon".equals(mCondition)) {
                return Energy.parseMoon(response);
            } else if ("halfyear".equals(mCondition)) {
                return Energy.parseHeafyear(response);
            } else if ("year".equals(mCondition)) {
                return Energy.parseYear(response);
            }
        }
        return null;
    }

    /**
     * 昨日节能
     * @param mCondition
     * @return
     */
    public static Result<Energy> getEnergyYes(String mCondition) throws Exception{
        //接口路径
        String url = URLCenter.getApi("querynh.asq");


        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Energy.parseDay(response);
        }
        return null;
    }

    /**
     * 今日节能
     * @param mCondition
     * @return
     */
    public static Result<Energy> getEnergyToday(String mCondition) throws Exception {
        //接口路径
        String url = URLCenter.getApi("querynh.asq");


        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Energy.parseDay(response);
        }
        return null;
    }

    /**
     * 修改密码
     * @param mPwd
     * @return
     */
    public static Result changePwd(String mPwd) throws Exception{
        //接口路径
        String url = URLCenter.getApi("editpwd.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("newpwd", mPwd);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 个人详情
     * @return
     */
    public static Result<MemberDetail> memberDetail() throws Exception{
        //接口路径
        String url = URLCenter.getApi("showuser.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return MemberDetail.parse(response);
        }
        return null;
    }

    /**
     * 提交用户详情
     * @param mName
     * @param mCname
     * @param mAdd
     * @param mMail
     * @return
     */
    public static Result<MemberDetail> submitMember(String mName, String mCname, String mAdd, String mMail) throws Exception{
        //接口路径
        String url = URLCenter.getApi("fulldata.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("user_name", new String(mName.getBytes("utf-8"), "ISO-8859-1"));
        obj.put("user_cname", new String(mCname.getBytes("utf-8"), "ISO-8859-1"));
        obj.put("user_mail", mMail);
        obj.put("user_add", new String(mAdd.getBytes("utf-8"), "ISO-8859-1"));


        Log.e("name", mName);
        Log.e("cname", mCname);
        Log.e("mail", mMail);
        Log.e("add", mAdd);

        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 关注列表
     * @param mCondition
     * @return
     */
    public static Result<List<Device>> guanzhuList() throws Exception{

        //接口路径
        String url = URLCenter.getApi("setflowlist.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");

        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parseSC(response);
        }
        return null;
    }

    /**
     * 设备信息
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceInfo> getDeviceInfo(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("showsetd.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);

        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
//            if (Result.fromJson(response).getCode() == 1) {
                return DeviceInfo.parse(response);
//            } else {
//                return null;
//            }
        }
        return null;
    }

    /**
     * 上传图片
     * @param mFile
     * @return
     */
    public static Result pushImage(File mFile) throws Exception{
        //接口路径
        String url = URLCenter.getApi("registeruser.asq");
        //封装json
        JSONObject obj = new JSONObject();
//        obj.put("databaseid", "AirApp");
//        obj.put("mobile", mPhone);
//        obj.put("userpwd", mPwd);
//        obj.put("mobilevote", mCode);
        obj.put("File", mFile);
        //发送请求
        String response = HttpService.post(url, obj);

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }

        return result;
    }

    /**
     * 提交设备信息
     * @param mInfo
     * @return
     */
    public static Result<DeviceInfo> saveDeviceInfo(DeviceInfo mInfo) throws Exception{
        //接口路径
        String url = URLCenter.getApi("Saveset.asq");
        //封装json
        JSONObject obj = new JSONObject();

        String time = mInfo.getAirOd().replace("-", "/");

        Log.e("name", mInfo.getCustName());
        Log.e("add", mInfo.getCustAdd());
        Log.e("brand", mInfo.getAirBrand());
        Log.e("sn", mInfo.getAirSn());
        Log.e("od", mInfo.getAirOd());
        Log.e("tel", mInfo.getEmeTel());
        Log.e("tel", mInfo.getWhTel());
        Log.e("setnum", mInfo.getJiqiSn());





        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("Cust_name", new String(mInfo.getCustName().getBytes("utf-8"), "ISO-8859-1"));
        obj.put("Cust_add", new String(mInfo.getCustAdd().getBytes("utf-8"), "ISO-8859-1"));
        obj.put("Air_brand", new String(mInfo.getAirBrand().getBytes("utf-8"), "ISO-8859-1"));
        obj.put("Air_sn", new String(mInfo.getAirSn().getBytes("utf-8"), "ISO-8859-1"));
        Log.e("shijian", mInfo.getAirOd());
        obj.put("Air_od", time);
        obj.put("Eme_tel", mInfo.getEmeTel());
        obj.put("Wh_tel", mInfo.getWhTel());
        obj.put("setnum", mInfo.getJiqiSn());


        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 设备改造信息
     * @param mInfo
     * @return
     */

    public static Result<DeviceReform> submitDevice(DeviceReform mInfo) throws Exception{

        //接口路径
        String url = URLCenter.getApi("setdesign.asq");
        //封装json

        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setnum", "");
        if (mInfo.getPaiqiTemp() != null) {
            obj.put("Paiqi_temp", new String(mInfo.getPaiqiTemp().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Paiqi_temp", "");
        }

        if (mInfo.getJiazaifa() != null) {
            obj.put("Jiazaifa", new String(mInfo.getJiazaifa().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Jiazaifa", "");
        }

        if (mInfo.getPressCgq() != null) {
            obj.put("Press_cgq", new String(mInfo.getPressCgq().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Press_cgq", "");
        }

        if (mInfo.getInstallChicun() != null) {
            obj.put("Install_chicun", new String(mInfo.getInstallChicun().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Install_chicun", "");
        }

        if (mInfo.getDisplayChicun() != null) {
            obj.put("Display_chicun", new String(mInfo.getDisplayChicun().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Display_chicun", "");
        }

        if (mInfo.getDianjiChicun() != null) {
            obj.put("Dianji_chicun", new String(mInfo.getDianjiChicun().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Dianji_chicun", "");
        }

        if (mInfo.getPowerChicun() != null) {
            obj.put("Power_chicun", new String(mInfo.getPowerChicun().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Power_chicun", "");
        }

        if (mInfo.getFanKw() != null) {
            obj.put("Fan_kw", new String(mInfo.getFanKw().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Fan_kw", "");
        }

        if (mInfo.getPicUrl() != null) {
            obj.put("Pic_url", new String(mInfo.getPicUrl().getBytes("utf-8"), "ISO-8859-1"));
        } else {
            obj.put("Pic_url", "");
        }


        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Result.fromJson(response);
        }
        return null;
    }

    /**
     * 历史数据 --- 预警
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceLog>> getDetailLishiYJ(String jiqiSn, String mCondition) throws Exception{

        //接口路径
        String url = URLCenter.getApi("qsetyblog.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "yj");
        obj.put("datekind",mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceLog.parseYJ(response);
        }
        return null;
    }

    /**
     * 历史数据 --- 报警
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceLog>> getDetailLishiBJ(String jiqiSn, String condition) throws Exception{

        //接口路径
        String url = URLCenter.getApi("qsetyblog.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "bj");
        obj.put("datekind",condition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceLog.parseBJ(response);
        }
        return null;
    }

    /**
     * 预警列表
     * @param mCondition
     * @param content
     * @return
     */
    public static Result<List<Device>> deviceYj(String mCondition, String content) throws Exception{
        Log.e("mCondition", mCondition);
        Log.e("content", content);

        //接口路径
        String url = URLCenter.getApi("qsetyjlist.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        obj.put("content", new String(content.getBytes("utf-8"), "ISO-8859-1"));
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parseYJ(response);
        }
        return null;
    }

    /**
     * 报警列表
     * @param mCondition
     * @param content
     * @return
     */
    public static Result<List<Device>> deviceBj(String mCondition, String content) throws Exception{
        Log.e("mCondition", mCondition);
        Log.e("content", content);

        //接口路径
        String url = URLCenter.getApi("qsetbjlist.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        obj.put("content", new String(content.getBytes("utf-8"), "ISO-8859-1"));
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parseBJ(response);
        }
        return null;
    }

    /**
     * 报警详情
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceYBDetail> getDetailBj(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetybxq.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "bj");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceYBDetail.parseBJ(response);
        }
        return null;
    }

    /**
     * 预警详情
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceYBDetail> getDetailYj(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetybxq.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "yj");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceYBDetail.parseYJ(response);
        }
        return null;
    }

    /**
     * 维保列表
     * @param mCondition
     * @param content
     * @return
     */
    public static Result<List<Device>> deviceWB(String mCondition, String content) throws Exception{
        Log.e("mCondition", mCondition);
        Log.e("content", content);

        //接口路径
        String url = URLCenter.getApi("qsetwblist.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        if (CacheCenter.getCurrentUser() != null) {
            obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        } else {
            obj.put("mobile", "");
        }
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        obj.put("content", new String(content.getBytes("utf-8"), "ISO-8859-1"));
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parseWB(response);
        }
        return null;
    }

    /**
     * 维保信息
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceWBDetail> getDetailWb(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetwbxq.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("wydata", "wb");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceWBDetail.parseWB(response);
        }
        return null;
    }

    /**
     * 维保预警信息
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceWBDetail> getDetailWx(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetybxq.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "yj");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceWBDetail.parseWB(response);
        }
        return null;
    }

    /**
     * 历史数据 --- 维保
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceLog>> getDetailLishiWB(String jiqiSn) throws Exception{

        //接口路径
        String url = URLCenter.getApi("qsetwblog.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
//        obj.put("datekind", mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceLog.parseWB(response);
        }
        return null;
    }

    /**
     * 历史数据 --- 维修
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceLog>> getDetailLishiWX(String jiqiSn) throws Exception{

        //接口路径
        String url = URLCenter.getApi("qsetyblog.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "bj");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceLog.parseBJ(response);
        }
        return null;
    }

    /**
     * 获取局部刷新标示
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceDetail> getDetailStatus(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("Csetdata.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        //发送请求
        String response = HttpService.post(url, obj);

        Result<DeviceDetail> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJsonJB(response);
        }

        return result;
    }

    /**
     * 设备详情局部刷新
     * @param jiqiSn
     * @return
     */
    public static Result<DeviceDetail> getDetailJubu(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetview.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("ybdata", "yj");
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceDetail.parseJB(response);
        }
        return null;
    }

    /**
     * 设备日志
     * @param jiqiSn
     * @return
     */
    public static Result<List<DeviceLog>> getDeviceLog(String jiqiSn, String mCondition) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetlogdata.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("datekind", mCondition);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceLog.parseRZ(response);
        }
        return null;
    }


//    /**
//     * 地图发现
//     * @return
//     */
//    public static Result<List<Device>> devcieMapList() throws Exception{
//        //接口路径
//        String url = URLCenter.getApi("querysets.asq");
//        //封装json
//        JSONObject obj = new JSONObject();
//        obj.put("databaseid", "AirApp");
//        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
//        obj.put("loginsta", "1");
//        obj.put("condition", mCondition);
//        obj.put("content", content);
//        //发送请求
//        String response = HttpService.post(url, obj);
//
//        if (!TextUtils.isEmpty(response)) {
//            return Device.parse(response);
//        }
//        return null;
//    }
}
