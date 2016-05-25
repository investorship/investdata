package com.investdata.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static Logger _log = Logger.getLogger(PropertiesUtils.class);
	static Properties pps = new Properties();
	static  {
		InputStream in = null;
		try {
			in = new BufferedInputStream (new FileInputStream("config.properties"));
			pps.load(in);
		} catch (Exception e) {
			_log.error("读取config.properties文件错误！", e);
		}  
	}
	
	/**
	 * 根据key获取配置文件信息
	 * @param key
	 * @return
	 */
	public static String getPropsValue(String key) {
		return pps.getProperty(key);
	}
		 
}
