package com.sohu110.airapp.utils;

/**
 * 电流范围
 * Created by Aaron on 2016/6/29.
 */
public class AutoCurrent {

    public static int getCurrent(int kw) {
        int current = 0;

        if (kw < 30) {
            current = 200;
        } else if (kw > 37 && kw < 55) {
            current = 400;
        } else if (kw > 75 && kw < 110) {
            current = 600;
        } else if (kw > 132 && kw < 220) {
            current = 1000;
        } else if (kw > 250 && kw < 315) {
            current = 1500;
        }
        return current;
    }
}
