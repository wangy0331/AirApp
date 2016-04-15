package com.dz.airapp.service;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Aaron on 2016/4/3.
 */
public class HttpService {
    /**
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String post(String url, JSONObject obj) {
        String result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            //指定访问的服务器地址
            HttpPost httpPost = new HttpPost(url);
//            HttpGet httpGet = new HttpGet(url);

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //请求响应了成功了
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            Log.e("catch", e.toString());
        }
        return result;
    }

    public static String get(String url1) {
        String result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            //指定访问的服务器地址
            HttpGet httpGet = new HttpGet(url1);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //请求响应了成功了
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity, "GBK");
            }
        } catch (Exception e) {
            Log.e("catch", e.toString());
        }
        return result;
    }

    /**
     * 获取验证码
     * @param url
     * @param obj
     * @return
     */
    public static String postCode(String url, JSONObject obj) {
        return null;
    }

//    public static String sendMsg(String phone) {
//
//    }
}
