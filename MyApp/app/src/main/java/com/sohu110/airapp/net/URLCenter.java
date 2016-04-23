package com.sohu110.airapp.net;

import android.text.TextUtils;

public class URLCenter {

	public static final String SERVER = "http://222.92.237.43:1880";


	/**
	 * 获取完整图片路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getImageAddr(String uri) {
		if (TextUtils.isEmpty(uri) || "null".equalsIgnoreCase(uri)) {
			return "";
		}
		return String.format("%s%s", SERVER, uri);
	}

	/**
	 * 获取完整HTML5网页路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getWebAddr(String uri) {
		if (TextUtils.isEmpty(uri)) {
			return "";
		}
		return String.format("%s/app%s", SERVER, uri);
	}

	/**
	 * 获取接口完整路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getApi(String uri) {
		if (TextUtils.isEmpty(uri)) {
			return "";
		}
		return String.format("%s/air/%s", SERVER, uri);
	}

	/**
	 * WebView默认404页面
	 * 
	 * @return
	 */
	public static String getError() {
		return "file:///android_asset/404.html";
	}

}
