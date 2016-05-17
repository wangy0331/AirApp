package com.sohu110.airapp.bean;

import com.sohu110.airapp.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息
 * Created by Aaron on 2016/5/5.
 */
public class MemberDetail implements Serializable {
    //姓名
    private String userName;
    //昵称
    private String userCname;
    //邮箱
    private String userMail;
    //地址
    private String userAdd;
    //积分
    private String score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 解析json
     * @param response
     * @return
     */
    public static Result<MemberDetail> parse(String response) {


        List<MemberDetail> list = null;

        Result<MemberDetail> result = null;
        try {
            JSONObject obj = new JSONObject(response);
            result = new Result<MemberDetail>();
            result.setCode(1);
            JSONArray array = obj.optJSONArray("Showuserinfo");
            if (array != null) {
                list = new ArrayList<MemberDetail>();
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

    private static MemberDetail fromJson(JSONObject obj) {
        MemberDetail item = null;
        try {
            item = new MemberDetail();
            item.setUserName(obj.getString("User_name"));
            item.setUserCname(obj.getString("User_cname"));
            item.setUserMail(obj.getString("User_mail"));
            item.setUserAdd(obj.getString("User_add"));
            item.setScore(obj.getString("Score"));
        } catch (Exception e) {
            Logger.e("", "", e);
        }
        return item;
    }
}
