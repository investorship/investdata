package com.investdata.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static Logger _log = Logger.getLogger(PropertiesUtils.class);
	static Properties pps = new Properties();
	static  {
		InputStream in = null;
		try {
			in = new BufferedInputStream (PropertiesUtils.class.getResourceAsStream("/config.properties"));
			BufferedReader bf = new BufferedReader(new InputStreamReader(in,"UTF-8")); //防止中文乱码，使用字符流,并指定编码 JDK1.6才支持 pps.load(Reader)
			pps.load(bf); 
		} catch (Exception e) {
			e.printStackTrace();
			_log.error("读取config.properties文件错误！", e);
		}  
	}
	
	/**
	 * 根据key获取配置文件信息
	 * @param key
	 * @param dft 默认值
	 * @return
	 */
	public static String getPropsValue(String key, String dft) {
		String retVal = pps.getProperty(key);
		if (StringUtils.isEmpty(retVal)) {
			return dft;
		}else {
			return retVal;
		}
	}
}
