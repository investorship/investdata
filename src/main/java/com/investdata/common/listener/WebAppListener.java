package com.investdata.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
		JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxIdle(3000);  
        JedisPool jedisPool = new JedisPool(config,"10.10.77.83",6379);
		
		
		
		Jedis jedis = new Jedis("10.10.77.83",6379);
		jedis.auth("admin123"); 
		
//		je
		
		
		jedis.set("name", "zhangsan");
		jedis.set("name", "zhangs11an");
		
		System.err.println(jedis.get("name"));
	}
	
}
