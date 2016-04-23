package com.sohu110.airapp.cache;

import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.bean.DayCount;
import com.sohu110.airapp.bean.User;
import com.sohu110.airapp.kit.FileKit;
import com.sohu110.airapp.utils.Const;

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

    /**
     * 缓存count
     */
    public static boolean cacheCurrentCount(DayCount count) {
        return FileKit.save(LibApplication.getInstance(), count, Const.EXTRA_COUNT);
    }

    /**
     * 获取当前count
     * @return
     */
    public static DayCount getCurrentCount() {
        Object obj = FileKit.getObject(LibApplication.getInstance(), Const.EXTRA_COUNT);
        if(obj != null) {
            return (DayCount)obj;
        }
        return null;
    }

    /**
     * 清除count
     * @return
     */
    public static boolean removeCurrentCount() {
        return FileKit.remove(LibApplication.getInstance(), Const.EXTRA_COUNT);
    }
}
