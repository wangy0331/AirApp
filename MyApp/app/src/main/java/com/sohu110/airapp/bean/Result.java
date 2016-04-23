package com.sohu110.airapp.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aaron on 2016/4/6.
 */
    public class Result<T> implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 5656654368754505584L;

        /**
         * 请求结果 1-成功；其他值均为失败；
         */
        private int code;

        /**
         * 请求结果描述
         */
        private String message;

        /**
         * 数据
         */
        private T data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        /**
         * 判断操作是否成功
         *
         * @return
         */
        public boolean isSuceed() {
            return code == 1;
        }

        /**
         * 判断登录信息是否过期
         * @return
         */
        public boolean isSessionTimeout() {
            return code == 3;
        }

        /**
         * 将JSON数据转为对象
         *
         * @param json
         * @return
         */
        public static <T> Result<T> fromJson(String json) {
            Result<T> result = null;
            try {
                JSONObject obj = new JSONObject(json);
                result = new Result<T>();
                result.code = obj.optInt("errcode");
                result.message = obj.optString("errtext");
            } catch (Exception e) {
            }
            return result;
        }

        public static <T> Result<T> fromText(String json) {
            Result<T> result = null;
            try {
                result = new Result<T>();
//                result.code =
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }


}
