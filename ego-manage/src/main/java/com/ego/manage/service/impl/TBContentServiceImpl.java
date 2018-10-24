package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TBContentDubboService;
import com.ego.manage.service.TBContentService;
import com.ego.pojo.TbContent;
import com.ego.jedis.service.JedisDao;


@Service
public class TBContentServiceImpl implements TBContentService{
	
	@Reference
	private TBContentDubboService tBContentDubboServiceImpl;
	
	@Value("${key}")
	private String key;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Override
	public EasyUIDataGrid list(long categoryId, int page, int rows) {
		// TODO Auto-generated method stub
		return tBContentDubboServiceImpl.list(categoryId, page, rows);
	}

	@Override
	public EgoResult save(TbContent tbContent) throws Exception {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		EgoResult er = new EgoResult();
		int index = 0;
		
		index = tBContentDubboServiceImpl.insContent(tbContent);
		if (1 != index){
			throw new Exception("Can't insert into DB");
		} else {
			//add data into jedis, add key, configuration file,pom, and code.
			if (jedisDaoImpl.exist(key)){
				String str = (String) jedisDaoImpl.get(key);
				List<HashMap> list =  JsonUtils.jsonToList(str, HashMap.class);
				
				HashMap<String,Object> map = new HashMap<>();
				map.put("srcB", tbContent.getPic2());
				map.put("height", "240");
				map.put("alt", "");
				map.put("width", "670");
				map.put("src", tbContent.getPic());
				map.put("widthB", "550");
				map.put("href", tbContent.getUrl());
				map.put("heightB", "240");
				if (list.size() == 6){ //check if the size's full,only supports 6
					list.remove(5);
				}
				list.add(0, map);
				String str1 = JsonUtils.objectTOString(list);
				jedisDaoImpl.set(key, str1);
				
			}
		}
		er.setStatus(200);
		return er;
	}

	@Override
	public EgoResult delContent(String ids) throws Exception {
		int index = 0;
		index = tBContentDubboServiceImpl.delContent(ids);
		if (1 != index){
			throw new Exception("Can't delete item!");
		}
		EgoResult er= new EgoResult();
		er.setStatus(200);
		return er;
	}

	@Override
	public EgoResult updateContent(TbContent tBContent) throws Exception {
		int index = 0;
		index = tBContentDubboServiceImpl.updateContent(tBContent);
		if (1 != index){
			throw new Exception("update Failure!");
		}
		EgoResult er= new EgoResult();
		er.setStatus(200);
		return er;
	}

}
