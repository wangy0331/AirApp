package com.sohu110.airapp.widget;

import android.os.Environment;

import
        com.sohu110.airapp.kit.StringKit;

/**
 * 配置
 * Created by Aaron on 2016/5/16.
 */
public class LibConfig {

    /**
     * 本地缓存根目录<br />
     * 默认为外部存储器根目录
     *
     */
    private static String cacheRootFolder;

    /**
     * 获取本地图片缓存目录
     * @return the cacheImagePath
     */
    public static String getCacheImagePath() {
        String path = getCacheRootFolder();
        if(path != null) {
            if(!path.endsWith("/")) {
                path += "/";
            }
            path += "image/";
        }
        return path;
    }

    /**
     * 获取本地缓存根目录
     * @return the cacheRootPath
     */
    public static String getCacheRootFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        if(StringKit.isNotEmpty(cacheRootFolder)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + cacheRootFolder;
            if(!path.endsWith("/")) {
                path += "/";
            }
        }
        return path;
    }
}
