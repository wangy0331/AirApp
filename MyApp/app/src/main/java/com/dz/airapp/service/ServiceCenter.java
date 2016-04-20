package com.dz.airapp.service;

import android.text.TextUtils;
import android.util.Log;

import com.dz.airapp.bean.Device;
import com.dz.airapp.bean.DeviceDetail;
import com.dz.airapp.bean.Result;
import com.dz.airapp.bean.User;
import com.dz.airapp.net.URLCenter;

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
        obj.put("mobile", "18662181836");
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
        obj.put("mobile", "13771768710");
        obj.put("loginsta", "1");
        obj.put("condition", mCondition);
        obj.put("content", content);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return Device.parse(response);
        }
        return null;
    }


    public static Result<DeviceDetail> getDetail(String jiqiSn) throws Exception{
        //接口路径
        String url = URLCenter.getApi("qsetsdata.asq");

//        CacheCenter.getCurrentUser().getUserid()

        //封装json
        JSONObject obj = new JSONObject();
        obj.put("databaseid", "AirApp");
        obj.put("mobile", "13771768710");
        obj.put("loginsta", "1");
        obj.put("setno", jiqiSn);
        //发送请求
        String response = HttpService.post(url, obj);

        if (!TextUtils.isEmpty(response)) {
            return DeviceDetail.parse(response);
        }
        return null;
    }
}
