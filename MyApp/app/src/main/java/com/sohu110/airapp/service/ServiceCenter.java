package com.sohu110.airapp.service;

import android.text.TextUtils;
import android.util.Log;

import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.DeviceChart;
import com.sohu110.airapp.bean.DeviceDetail;
import com.sohu110.airapp.bean.Energy;
import com.sohu110.airapp.bean.MemberDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.bean.User;
import com.sohu110.airapp.cache.CacheCenter;
import com.sohu110.airapp.net.URLCenter;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static Result deviceRegister(String scan) throws Exception{
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

        Result<User> result = null;
        if (!TextUtils.isEmpty(response)) {
            result = Result.fromJson(response);
        }
        return result;
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
        String url = URLCenter.getApi("querysets.asq");
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

//        obj.put("databaseid", "AirApp");
//        obj.put("mobile", "13771768710");
//        obj.put("loginsta", "1");
//        obj.put("condition", "area");
//
//        String t = "苏州";
//        String utf8 = new String(t.getBytes( "UTF-8"));
//        String unicode = new String(utf8.getBytes(),"UTF-8");
//        String gbk = new String(unicode.getBytes("GB2312"));
//
//
//        obj.put("content", "SZ");






        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parse(response);
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
        String url = URLCenter.getApi("qsetsdata.asq");


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
        String url = URLCenter.getApi("qsetschart.asq");
        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", CacheCenter.getCurrentUser().getUserid());
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        obj.put("hours", "1");
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
            return Energy.parse(response);
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
            return Energy.parse1(response);
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
            return Energy.parse2(response);
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
     * @param s
     * @return
     */
    public static Result<List<Device>> guanzhuList() throws Exception{

        //接口路径
        String url = URLCenter.getApi("qsetsfollow.asq");
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
            return Device.parse(response);
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
