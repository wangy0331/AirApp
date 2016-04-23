package com.sohu110.airapp.utils;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;

public class ResponseHelper {
	
	private static final String TAG = ResponseHelper.class.getSimpleName();
	
	/**
	 * 将请求结果转为String
	 * @param response
	 * @param charsetName
	 * @return
	 */
	public static String parseString(HttpResponse response, String charsetName) {
		charsetName = charsetName == null ? "utf8":charsetName;
		String result = null;
		try {
			byte[] bytes = parseBytes(response);
			if(bytes != null) {
				result = new String(bytes,charsetName);
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 将请求结果转为String
	 * @param response
	 * @return
	 */
	public static String parseString(HttpResponse response) {
		return parseString(response, null); 
	}
	
	/**
	 * 将请求结果转为byte[]
	 * @param response
	 * @return
	 */
	public static byte[] parseBytes(HttpResponse response) {
		try {
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toByteArray(response.getEntity());
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 将请求结果转为InputStream
	 * @param response
	 * @return
	 */
	public static InputStream parseInputStream(HttpResponse response) {
		try {
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntity().getContent();
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 将请求结果转为boolean
	 * @param response
	 * @return
	 */
	public static boolean parseBoolean(HttpResponse response) {
		boolean result = false;
		try {
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = true;
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
		return result;
	}
}
