package com.sohu110.airapp.utils;

import com.sohu110.airapp.bean.Energy;

import java.util.Comparator;

/**
 * Created by Aaron on 2016/5/3.
 */
public class SortComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        Energy a = (Energy) lhs;
        Energy b = (Energy) rhs;

        return (a.getsDay() - b.getsDay());
    }
}
