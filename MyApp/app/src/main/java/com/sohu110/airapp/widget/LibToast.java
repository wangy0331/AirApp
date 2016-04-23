package com.sohu110.airapp.widget;

import android.content.Context;
import android.widget.Toast;

import com.sohu110.airapp.LibApplication;

/**
 * Created by Aaron on 2016/4/6.
 */
public class LibToast {

    private static Toast toast = null;

    @Deprecated
    public static void show(Context context, String message) {
        if(toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    @Deprecated
    public static void show(Context context, int stringId) {
        show(context, context.getString(stringId));
    }

    /**
     * 显示提示消息
     * @param message
     */
    public static void show(String message) {
        if(toast == null) {
            toast = Toast.makeText(LibApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        //默认
        toast.setText(message);
        toast.show();
    }

    /**
     * 显示提示消息
     * @param stringId
     */
    public static void show(int stringId) {
        Context context = LibApplication.getInstance();
        show(context.getString(stringId));
    }

}
