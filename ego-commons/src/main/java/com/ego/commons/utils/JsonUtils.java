package com.ego.commons.utils;

import java.io.IOException;
import java.util.List;

import com.ego.commons.pojo.EgoResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();
	public static void main(String[] argv) throws IOException{
		
		EgoResult value = new EgoResult();
		value.setData("xxx");
		value.setMessage("dffdf");
		value.setStatus(200);
		String jsString = mapper.writeValueAsString(value);
		System.out.println(jsString);
		
		EgoResult newObj = mapper.readValue(jsString, EgoResult.class);
		System.out.println(newObj.getData());
		System.out.println(newObj.getMessage());
		System.out.println(newObj.getStatus());
	}
	public static String objectTOString(Object o){
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static <T> T jsonToPojo(String str, Class<T> beanType){
		T t = null;
		try {
			t = mapper.readValue(str, beanType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}
	public static <T> List<T> jsonToList(String str, Class<T> beanType){
		List<T> t = null;
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			t = mapper.readValue(str, javaType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
}
