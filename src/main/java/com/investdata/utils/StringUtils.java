package com.investdata.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtils {
	
	/**
	 * 给定字符串右侧填充空格
	 * @param str 给定字符串
	 * @param length 空格位数
	 * @return 填充之后的字符串
	 */
	public static String fillRSpace(String str, int length) {
		String space = "";
		for (int i=0; i<length; i++) {
			space += " ";
		}
		str = str + space;
		return str;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if ("".equals(str) || str == null) {
			return true;
		}else {
			return false;
		}
		 
	}
	
	/**
	 * 去掉字符串尾部的空格.
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null) {
			return "";
		} else {
			return str.trim();
		}
	}
	
	/**
	 * 获取14位时间戳
	 * @return
	 */
	public static String get14Time() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String retVal = sdf.format(new Date(System.currentTimeMillis()));
		return retVal;
	}
	
	public static void main(String[] args) {
		System.err.println(get14Time());
//		String str = "HLMD";
//		System.err.println("|" + fillRSpace(str, 3) + "|");
	}
}
