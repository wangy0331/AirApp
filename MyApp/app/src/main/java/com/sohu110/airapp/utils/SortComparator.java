package com.sohu110.airapp.utils;

import com.sohu110.airapp.bean.DeviceLog;

import java.util.Comparator;

/**
 * 排序
 * Created by Aaron on 2016/5/3.
 */
public class SortComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        DeviceLog a = (DeviceLog) lhs;
        DeviceLog b = (DeviceLog) rhs;

        int flag = a.getSjsj().compareTo(b.getSjsj());
        return flag;
    }
}
