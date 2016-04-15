package com.dz.airapp.cache;

import com.dz.airapp.LibApplication;
import com.dz.airapp.bean.User;
import com.dz.airapp.kit.FileKit;
import com.dz.airapp.utils.Const;

/**
 * 缓存信息
 * Created by Aaron on 2016/3/29.
 */
public class CacheCenter {

    /**
     * 获取当前登录的用户
     * @return
     */
    public static User getCurrentUser() {
        Object obj = FileKit.getObject(LibApplication.getInstance(), Const.CACHE_OBJ_USER);
        if(obj != null) {
            return (User)obj;
        }
        return null;
    }

    /**
     * 缓存用户数据
     * @param user
     * @return
     */
    public static boolean cacheCurrentUser(User user) {
        return FileKit.save(LibApplication.getInstance(), user, Const.CACHE_OBJ_USER);
    }

    /**
     * 清除用户数据
     * @return
     */
    public static boolean removeCurrentUser() {
        return FileKit.remove(LibApplication.getInstance(), Const.CACHE_OBJ_USER);
    }

}
