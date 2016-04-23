package com.sohu110.airapp.bean;

import java.io.Serializable;

/**
 * 用户信息
 * Created by Aaron on 2016/4/6.
 */
public class User implements Serializable {
    private String databaseid;
    //用户id
    private String userid;
    //密码
    private String userpwd;
    //是否记住登录
    private String type;

    public String getDatabaseid() {
        return databaseid;
    }

    public void setDatabaseid(String databaseid) {
        this.databaseid = databaseid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
