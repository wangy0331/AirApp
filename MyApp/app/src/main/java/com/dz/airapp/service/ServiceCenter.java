package com.dz.airapp.service;

import android.text.TextUtils;
import android.util.Log;

import com.dz.airapp.bean.Result;
import com.dz.airapp.bean.User;
import com.dz.airapp.net.URLCenter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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


    public static String code() throws Exception{
        String url2 = "http://sms.api.ums86.com:8893/sms/Api/Send.do?SpCode=230" +
                "&LoginName=admin&Password=admin&MessageContent=你有一项编号为123456789的事务需要处理。" +
                "&UserNumber=18662181836&SerialNumber=&ScheduleTime=&f=1 ";

        String result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            //指定访问的服务器地址
            HttpGet httpGet = new HttpGet(url2);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //请求响应了成功了
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            Log.e("catch", e.toString());
        }
        return null;
    }
}
