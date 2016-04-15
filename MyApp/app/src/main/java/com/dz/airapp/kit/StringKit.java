/*
 *	Copyright (c) 2012, Yulong Information Technologies
 *	All rights reserved.
 *  
 *  @Project: LibYulong
 *  @author: Robot
 *	@email: feng88724@126.com
 */
package com.dz.airapp.kit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 * @author Robot
 * @weibo http://weibo.com/feng88724
 * @date Dec 14, 2012	
 */
public class StringKit {
	
	/**
	 * 邮箱
	 */
	private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	/**
	 * 数字
	 */
	private static final Pattern numericPattern = Pattern.compile("^[0-9]+$");
	
	/**
	 * 匹配空格、换行、回车等
	 */
	private static final Pattern blankPattern = Pattern.compile("\\s*|\t|\r|\n");
	
	/**
	 * 匹配首尾空行、换行、回车等
	 */
	private static final Pattern blankPatternLR = Pattern.compile("^\\s*|\\s*$");
	
	/**
	 * 匹配所有标签（Html、Xml）(忽略大小写)
	 */
	private static final Pattern htmlPattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);

	/**
	 * 匹配摘要中的" 本报讯（记者 鲍旺 ）"
	 */
	private static Pattern abstractPattern = Pattern.compile("^.{1,10}[报网]讯\\s?([（(][^）)]*[）)])?");

	/**
	 * 判断是否为空字符串
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(String src) {
		return src == null || src.trim().length() == 0;
	}
	
	/**
	 * 判断是否是空字符串(包括null, 长度为0, 只包含空格)
	 * @param args
	 * @return
	 */
	public static boolean isNotEmpty(String src) {
		return !isEmpty(src);
	}
	
	/**
	 * 判断是否为邮箱
	 * @param src
	 * @return
	 */
	public static boolean isEmail(String src) {
		if(isEmpty(src))
			return false;
		return emailer.matcher(src).matches();
	}
	
	/**
	 * 判断是否只包含数字
	 * @param src
	 * @return
	 */
	public static boolean isNumeric(String src) {
		if(isEmpty(src))
			return false;
		return numericPattern.matcher(src).matches();
	}
	
    /**
     * 去除空格、回车、换行、Tab
     * @param str
     * @return
     */
    public static String trim(String str) {
    	if(isEmpty(str)) return str;
    	Matcher m = blankPattern.matcher(str);
    	String after = m.replaceAll("");
    	return after;
    }

    /**
     * 过滤Abstract
     * @param str
     * @return
     */
    public static String trimAbstract(String str) {
    	if(str == null) return str;
    	Matcher m = abstractPattern.matcher(str);
    	String after = m.replaceAll("");
    	return after;
    }

    /**
     * 去除字符串两端空格、回车、换行、Tab
     * @param str
     * @return
     */
    public static String trimLR(String str) {
    	if(isEmpty(str)) return str;
    	Matcher m = blankPatternLR.matcher(str);
    	String after = m.replaceAll("");
    	return after;
    }
    
    
    /***
	 * 去除所有标签(Html\Xml)
	 * @param str
	 * @return
	 */
	public static String trimHtml(String str){
		if(isEmpty(str)) return str;
    	Matcher m = htmlPattern.matcher(str);
    	String after = m.replaceAll("");
    	return after;
	}
	
	/***
	 * 替换所有HTML标签(Html\Xml)
	 * @param str
	 * @return
	 */
	public static String replaceHtml(String str, String replaceStr){
		if(isEmpty(str)) return str;
    	Matcher m = htmlPattern.matcher(str);
    	String after = m.replaceAll(replaceStr);
    	return after;
	}
	
    //**************************************************************
    //InputStream 与 String 互转
    //**************************************************************
    /**
     * 字符串转为输入流
     * @param str
     * @return
     */
    public static InputStream string2Stream(String str) {
    	if(isEmpty(str)) return null;
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }
    
    /**
     * 输入流转为字符串
     * @param inStream
     * @return
     * @throws IOException
     */
    public static String stream2String(InputStream inStream) throws IOException {
    	return new String(stream2Bytes(inStream));
    }
    
    /**
     * 输入流转为字符数组
     * @param inStream
     * @return
     * @throws IOException
     */
    public static byte[] stream2Bytes(InputStream inStream) throws IOException {
 		ByteArrayOutputStream outStream = null;
 		try {
 			outStream = new ByteArrayOutputStream();
 			byte[] buffer = new byte[1024];
 			int len = 0;
 			while ((len = inStream.read(buffer)) != -1) {
 				outStream.write(buffer, 0, len);
 			}
 			return outStream.toByteArray();
 		} finally {
 			try {
 				if(outStream != null) {
 					outStream.close();
 				}
 			}catch(Exception e) {
 			}
 		}
    }
}
