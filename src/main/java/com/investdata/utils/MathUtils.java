package com.investdata.utils;

import java.text.DecimalFormat;


public class MathUtils {
	private static DecimalFormat mathDec  = new DecimalFormat("###.00");
	
	/**
	 * 保留2位小数点
	 * @param number
	 * @return
	 */
	public static String format2DecPoint(double number) {
		return mathDec.format(number);
	}
	
}
