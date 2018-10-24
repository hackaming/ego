package com.ego.manage.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.TBItemDescService;
import com.ego.manage.service.TBItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

@Controller
public class TbItemController {
	@Resource
	private TBItemService tbitemmanageservice;
	@Resource
	private TBItemDescService tBItemDescServiceImpl;
	
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page,int rows){
		return tbitemmanageservice.show(page, rows);
	}
	@RequestMapping("rest/page/item-edit")
	public String showItemEdit(){
		return "item-edit";
	}
	@RequestMapping("rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids){ //ids that needs to be updated
		EgoResult er = new EgoResult();
		int index = tbitemmanageservice.update(ids,3);
		if (index == 1){
			er.setStatus(200);
		}
		return er;
	}
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids){ //ids that needs to be xia jia
		EgoResult er = new EgoResult();
		int index = tbitemmanageservice.update(ids,2);
		if (index == 1){
			er.setStatus(200);
		}
		return er;
	}
	@RequestMapping("rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids){ //ids that needs to be shangjia
		EgoResult er = new EgoResult();
		int index = tbitemmanageservice.update(ids,1);
		if (index == 1){
			er.setStatus(200);
		}
		return er;
	}
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult save(TbItem tbItem,String desc,String itemParams){ //插入了一条item,itemDESC也要插入
		EgoResult er = new EgoResult();
		System.out.println("Controller:"+itemParams);
		int index = tbitemmanageservice.save(tbItem, desc,itemParams);
		if (1 != index){
			er.setStatus(0);
			er.setMessage("Insert error");
		} else {
			er.setStatus(200);
		}
		return er;
	}
}
