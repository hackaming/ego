package com.ego.jedis.service.impl;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ego.jedis.service.JedisDao;

import redis.clients.jedis.JedisPool;

@Repository
public class JedisDaoImpl implements JedisDao{

	@Resource
	private JedisPool jedisClient;
	
	public boolean exist(String key) {
		// TODO Auto-generated method stub
		
		return jedisClient.getResource().exists(key);
	}
	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisClient.getResource().set(key, value);
	}
	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return jedisClient.getResource().get(key);
	}
	public long delete(String key){
		return jedisClient.getResource().del(key);
	}
	public static void main(String[] str){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/*.xml");
		JedisPool client = (JedisPool) ac.getBean("redisClient");
		client.getResource().set("key", "value");
	}
}
