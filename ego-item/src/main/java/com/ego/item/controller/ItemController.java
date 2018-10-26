package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TBItemChild;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.service.TBItemCatService;
import com.ego.item.service.TBItemService;
import com.ego.pojo.TbItem;

@Controller
public class ItemController {
	
	@Resource
	private TBItemCatService TbItemCatServiceImpl;
	@Resource
	private TBItemService tbItemServiceImpl;
	
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
	@RequestMapping("item/{id}")
	public String getitem(@PathVariable String id,Model model){
		TbItem item = tbItemServiceImpl.selItemById(id);
		TBItemChild tbItemChild = new TBItemChild();
		tbItemChild.setId(item.getId());
		tbItemChild.setSellPoint(item.getSellPoint());
		tbItemChild.setPrice(item.getPrice());
		tbItemChild.setTitle(item.getTitle());
		tbItemChild.setImages(item.getImage()!=null&&!item.equals("")?item.getImage().split(","):new String[1]);
		model.addAttribute("item", tbItemChild);
		return "item";
	}
}
