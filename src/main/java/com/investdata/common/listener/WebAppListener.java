package com.investdata.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import redis.clients.jedis.Jedis;

/**
 * 监听器，完成所有财务数据与股票数据的初始化加载
 * @author Administrator
 *
 */
public class WebAppListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext app = event.getServletContext(); //application 对象
		Jedis jie = new Jedis("");
		
	}
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("10.10.77.83",6379);
		jedis.auth("admin123");
		jedis.set("name", "zhangsan");
		
		System.err.println(jedis.get("name"));
	}
	
}
