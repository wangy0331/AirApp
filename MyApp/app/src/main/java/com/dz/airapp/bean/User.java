package com.dz.airapp.bean;

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
}
