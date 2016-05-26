package com.investdata.utils;


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
	
	public static void main(String[] args) {
		String str = "HLMD";
		System.err.println("|" + fillRSpace(str, 3) + "|");
	}
}
