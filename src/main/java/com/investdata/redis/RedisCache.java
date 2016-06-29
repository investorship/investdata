package com.investdata.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.investdata.dao.po.User;
import com.investdata.utils.PropertiesUtils;

public class RedisCache {
	private static  Logger _log = Logger.getLogger(RedisCache.class);
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	private static String redisIp = PropertiesUtils.getPropsValue("redis_ip", "");
	private static int redisPort = Integer.parseInt(PropertiesUtils.getPropsValue("redis_port", ""));
	private static String redisAuth = PropertiesUtils.getPropsValue("redis_auth", "");
	static {
        JedisPoolConfig config = new JedisPoolConfig();  
//        config.setMinIdle(50);  
//        config.setMaxIdle(3000); 
        
        _log.info(String.format("redis参数 redisIp=[%s],redisPort=[%s],redisAuth=[%s]",redisIp,redisPort,redisAuth));
        
        jedisPool = new JedisPool(config,redisIp,redisPort); 
    }  
	
	/**
	 * 返回一个jedis对象，采用单例模式
	 * redis服务器端已经开启验证，所以需要验证密码
	 * @return
	 */
	public static Jedis getJedis() {
		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		jedis.auth(redisAuth);
		return jedis;
	}
	
	public static void main(String[] args) {
		Jedis jedis = getJedis();
		User user = new User();
		user.setUserName("zhangsan");
		user.setPassword("nihao");
		
		User u2 = new User();
		u2.setUserName("lisi");
		u2.setPassword("wangwu");
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		userList.add(u2);
		
		String key = "keysssss"; 
		
		jedis.set(key.getBytes(), ObjectsTranscoder.serialize(userList));
		
		byte[] in = jedis.get(key.getBytes()); 
		List<User> list = ObjectsTranscoder.deserialize(in);    
        for(User u : list){  
            System.out.println("testSetElements user name is:" + u.getUserName() + "-----" + u.getPassword());  
        }  
	}
}
