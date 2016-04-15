/*
 *	Copyright (c) 2012, Yulong Information Technologies
 *	All rights reserved.
 *  
 *  @Project: LibYulong
 *  @author: Robot
 *	@email: feng88724@126.com
 */
package com.dz.airapp.HttpKit;


import com.dz.airapp.utils.ResponseHelper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Http请求类
 * @see	http://loopj.com/android-async-http/
 * @author Robot
 * @weibo http://weibo.com/feng88724
 * @date Dec 14, 2012	
 */
public class HttpKit {
	private static final String TAG = HttpKit.class.getSimpleName();
	
	private static ThreadLocal<DefaultHttpClient> httpClient = new ThreadLocal<DefaultHttpClient>();
	
	static final int CONNECTION_TIMEOUT = 10000;
	static final int SO_TIMEOUT = 60000;
	
	/**
	 * Get
	 * @param url
	 * @return
	 */
	public static HttpResponse get(String url) {
		return get(url,null);
	}
	
	/**
	 * Get
	 * @param url
	 * @param headers
	 * @return
	 */
	public static HttpResponse get(String url, Header[] headers) {
		HttpGet get = new HttpGet(url);
		if(headers != null) {
			get.setHeaders(headers);
		}
		return request(get, ThreeTimehandler);
	}
	
	/**
	 * Post
	 * @param url
	 * @return
	 */
	public static HttpResponse post(String url) {
		return post(url, null, null);
	}
	
	/**
	 * Post
	 * @param url
	 * @param entity (StringEntity,ByteArrayEntity, etc)
	 * @return
	 */
	public static HttpResponse post(String url, HttpEntity entity) {
		return post(url, null, entity);
	}
	
	/**
	 * Post
	 * @param url
	 * @param headers
	 * @return
	 */
	public static HttpResponse post(String url, Header[] headers) {
		return post(url, headers, null);
	}
	
	/**
	 * Post
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static HttpResponse post(String url, Header[] headers, HttpEntity entity) {
		HttpPost post = new HttpPost(url);
		if(headers != null) {
			post.setHeaders(headers);
		}
		if(entity != null) {
			post.setEntity(entity);
		}
		return request(post);
	}
	
	/**
	 * 以Form形式提交表单<br>
	 * 适用于Java、PHP等写的表单请求
	 * @param url		地址
	 * @param params	参数
	 * @return
	 */
	public static HttpResponse postForm(String url, List<NameValuePair> params) {
		return postForm(url, null, params);
	}
	
	/**
	 * 以Form形式提交表单<br>
	 * 适用于Java、PHP等写的表单请求
	 * @param url		地址
	 * @param headers	参数
	 * @param params
	 * @return
	 */
	public static HttpResponse postForm(String url, Header[] headers, List<NameValuePair> params) {
		UrlEncodedFormEntity entity = null;
		try {
			if(params != null) {
				entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			}
		} catch (Exception e) {
//			Logger.e(TAG, "", e);
		}
		return post(url, headers, entity);
	}
	
	/**
	 * 以JSON形式提交数据<br>
	 * 适用于ASP.NET写的 Web Service
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static HttpResponse postJson(String url, Header[] headers, List<NameValuePair> params) {
		StringEntity entity = null;
		try {
			if(params != null) {
				entity = new StringEntity(parseEntityString(params), HTTP.UTF_8);
				entity.setContentType("application/json");
				entity.setContentEncoding("utf-8");
			}
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		}
		return post(url, headers, entity);
	}
	
    /**
     * 将List<NameValuePair>拼成json数据
     * @param nameValuePairs
     * @return
     */
	public static String parseEntityString(List<NameValuePair> nameValuePairs) {
    	if(nameValuePairs == null) {
    		return null;
    	}
    	JSONObject jsons = new JSONObject();
    	for (int i = 0; i < nameValuePairs.size(); i++) {
    		BasicNameValuePair pair = (BasicNameValuePair)nameValuePairs.get(i);
    		try {
				jsons.put(pair.getName(), pair.getValue());
			} catch (JSONException e) {
//				Logger.e(TAG, e.getMessage(), e);
			}
    	}
    	return jsons.toString();
    }
	
	/**
	 * Put
	 * @param url
	 * @return
	 */
	public static HttpResponse put(String url) {
		return put(url, null, null);
	}
	
	/**
	 * Put
	 * @param url
	 * @param headers
	 * @return
	 */
	public static HttpResponse put(String url, Header[] headers) {
		return put(url, headers, null);
	}
	
	/**
	 * Put
	 * @param url
	 * @param params
	 * @return
	 */
	public static HttpResponse put(String url, HttpEntity entity) {
		return put(url, null, entity);
	}
	
	/**
	 * Put
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static HttpResponse put(String url, Header[] headers, HttpEntity entity) {
		HttpPut put = new HttpPut(url);
		if(headers != null) {
			put.setHeaders(headers);
		}
		if(entity != null) {
			put.setEntity(entity);
		}
		return request(put);
	}
	
	/**
	 * 发送get请求至地址url,并返回String结果
	 * @param url	请求地址
	 * @return
	 */
	public static String getStringResponse(String url) {
		return ResponseHelper.parseString(get(url));
	}
	
	/**
	 * 发送get请求至地址url,并返回String结果
	 * @param url			请求地址
	 * @param charsetName	结果编码
	 * @return
	 */
	public static String getStringResponse(String url, String charsetName) {
		return ResponseHelper.parseString(get(url), charsetName);
	}
	
	/**
	 * 发送get请求至地址url,并返回boolean结果
	 * @param url			请求地址
	 * @return
	 */
	public static boolean getBooleanResponse(String url) {
		return ResponseHelper.parseBoolean(get(url));
	}
	
	/**
	 * 请求
	 * @param request
	 * @return
	 */
	protected static HttpResponse request(HttpUriRequest request) {
		return request(request, OneTimehandler);
	}
	
	/**
	 * 请求
	 * @param request
	 * @param retryHandler 重试
	 * @return
	 */
	protected static HttpResponse request(HttpUriRequest request, HttpRequestRetryHandler retryHandler) {
		HttpResponse response = null;
		try {
//			ThreadSafeClientConnManager
			DefaultHttpClient client = getHttpClient();
			if(retryHandler != null) {
				client.setHttpRequestRetryHandler(retryHandler);
			} else {
				client.setHttpRequestRetryHandler(OneTimehandler);
			}
			response = client.execute(request);
		} catch (Exception e) {
//			Logger.e(TAG, e.getMessage(), e);
		} finally {
			
		}
		return response;
	}
	
	protected static DefaultHttpClient getHttpClient() {
		/****** Method 1 ******/
		//已知问题(1)：请求前没有打开连接, org.apache.http.NoHttpResponseException: The target server failed to respond
		//已知问题(2)：请求前没有打开连接, Invalid use of SingleClientConnManager: connection still allocated.
//		DefaultHttpClient client = httpClient.get();
//		if(client == null) {
//			HttpParams httpParams = new BasicHttpParams();  
//			//读取超时
//			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
//			//请求超时
//	        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT); 
//	         
//			client = new DefaultHttpClient(httpParams);
//			
//			httpClient.set(client);
//		}
//		return client;
		
		/****** Method 2 ******/
//		DefaultHttpClient client = null;
//		if(client == null) {
//			HttpParams httpParams = new BasicHttpParams();  
//			//读取超时
//			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
//			//请求超时
//	        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT); 
//			
//			//设置访问协议
//			SchemeRegistry schReg = new SchemeRegistry();
//			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
//			schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
//			
//			ClientConnectionManager cm = new ThreadSafeClientConnManager(httpParams, schReg);
//			client = new DefaultHttpClient(cm, httpParams);
//		}
//		return client;
		
		/****** Method 3 ******/
		HttpParams httpParams = new BasicHttpParams();
		//读取超时
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		//请求超时
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
         
		return new DefaultHttpClient(httpParams);
	}
	
    /**
     * 重试三次
     */
    private static HttpRequestRetryHandler ThreeTimehandler = new HttpRequestRetryHandler() {
		
		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if(executionCount >= 3)
				return false;
			return true;
		}
	};
	
	/**
	 * 重试一次
	 */
    private static HttpRequestRetryHandler OneTimehandler = new HttpRequestRetryHandler() {
		
		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if(executionCount >= 1)
				return false;
			return true;
		}
	};
	
//	/**
//	 * get
//	 * @return
//	 */
//	private static String doGet(String url, Header[] headers) {
//		String res = null;
//		HttpURLConnection conn = null;
//		try {
//			URL _url_ = new URL(url);
//			URLConnection urlConn = _url_.openConnection();
//			urlConn.setConnectTimeout(10*1000);
//			urlConn.setReadTimeout(60*1000);
//			conn = (HttpURLConnection)urlConn;
//			if(headers != null) {
//				for (int i = 0; i < headers.length; i++) {
//					conn.setRequestProperty(headers[i].getName(), headers[i].getValue());
//				}
//			}
//			conn.setDoInput(true);
//			conn.setRequestMethod("GET");
//			InputStream ins = conn.getInputStream();
//			int code = conn.getResponseCode();
//			String message = conn.getResponseMessage();
//			Logger.d(TAG, "Response Code: " + code + " | Info: " + message);
//			if(200 == code) {
//				res = StringKit.stream2String(ins);
//			}
//		} catch (Exception e) {
//			Logger.e(TAG, "", e);
//		} finally {
//			if(conn != null) {
//				conn.disconnect();
//			}
//		}
//		return res;
//	}
}
