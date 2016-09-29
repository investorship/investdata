package com.investdata.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.investdata.redis.CacheManager;
import com.investdata.redis.RedisCache;

/**
 * 监听器，完成所有财务数据与股票数据的初始化加载
 * 
 * @author Administrator
 * 
 */
public class WebAppListener implements ServletContextListener {
	Logger _log = Logger.getLogger(WebAppListener.class);
	Jedis jedis = RedisCache.getJedis();

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}
	
	/**
	 * 将所有数据加载到缓存redis中
	 * 后续所有的计算直接从redis取值，增加展示速度。
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// ServletContext app = event.getServletContext(); //application 对象
		_log.info("监听器webApplistener 准备初始化数据到redis...");
		ServletContext application = event.getServletContext();
		
		try {
			CacheManager.initStockData(application);  // 初始化页面检索数据
			CacheManager.initFinanceIndexInfo(application); // 初始化财务指标信息
			CacheManager.initGendataSheet();  // 初始化综合数据表项数据.
			CacheManager.initIncstateSheet(); //初始化利润表数数据
			CacheManager.initCashFlowSheet(); //初始化现金流量表数据
			CacheManager.initBalanceSheet();  //初始化资产负债表数据
			CacheManager.initSearchIndex(); //初始化首页搜索指数
		} catch (Exception e) {
			_log.error("监听器初始化数据错误:" + e.getMessage(), e);
		}

	}
}
