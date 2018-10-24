package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.service.TBItemCatService;

@Controller
public class ItemController {
	
	@Resource
	TBItemCatService TbItemCatServiceImpl;
	
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue all(String callBack){
		PortalMenu pm = TbItemCatServiceImpl.showMenu();
		for (Object o:pm.getData()){
			if (null == o){
				System.out.println("found an null!");
			}
		}
		MappingJacksonValue mjv = new MappingJacksonValue(pm);
		mjv.setJsonpFunction(callBack);
		return mjv;
	}
}
