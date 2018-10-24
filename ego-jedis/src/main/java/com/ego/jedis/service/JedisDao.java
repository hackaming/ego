package com.ego.jedis.service;

public interface JedisDao {
	boolean exist(String key);
	String set(String key,String value);
	Object get(String key);
}
