package com.investdata.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.investdata.utils.PropertiesUtils;

public class RedisCache {
	private static  Logger _log = Logger.getLogger(RedisCache.class);
	
	private static JedisPool jedisPool;
	
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
		Jedis jedis = null;
		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		jedis.auth(redisAuth);
		return jedis;
	}
	
	public static void destory() {
		if (jedisPool != null) {
			jedisPool.destroy();
			_log.info("销毁redis资源池");
		}
	}
	
	public static void main(String[] args) {
		Jedis jedis = getJedis();
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("userName", "zhangsan");
		map.put("password", "123");
		
		jedis.hmset("key", map);
		
		List<String> list = jedis.hmget("key", "userName");
		
		
		/*User user = new User();
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
        }  */
	}
}
