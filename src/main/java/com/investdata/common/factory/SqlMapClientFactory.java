package com.investdata.common.factory;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


public class SqlMapClientFactory {
	private static SqlMapClient sqlMapClient;
	private static final String ibatisFile = "SqlMapConfig.xml";
	private static final String log4jFile = "log4j.properties";
	private static Logger log = Logger.getLogger(SqlMapClientFactory.class);
	static {
		URL  url = ClassLoader.getSystemResource(log4jFile);
		String path = url.getPath();
		PropertyConfigurator.configure(path);
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(ibatisFile);
		} catch (IOException e) {
			log.error("读取ibatis配置文件出现异常", e);
		}
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		log.info("sqlMapClient 创建成功!");
	}
	
	public static SqlMapClient getInstance() {
		return sqlMapClient;
	}
}
