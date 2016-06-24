package com.investdata.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.investdata.dao.po.User;

public class RedisCache {
	private static JedisPool jedisPool;
	private static Jedis jedis;
	static {
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMinIdle(50);  
        config.setMaxIdle(3000);   
        jedisPool = new JedisPool(config,"10.10.77.83",6379); 
    }  
	
	/**
	 * 返回一个jedis对象，采用单例模式
	 * @return
	 */
	public static Jedis getJedis() {
		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		jedis.auth("admin123");
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
