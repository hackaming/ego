package com.ego.manage.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TBItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TBItemParamController {
	@Resource
	private TBItemParamService tBItemParamServiceImpl;
	
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid list(int page,int rows){
		return tBItemParamServiceImpl.list(page, rows);
	}
	@RequestMapping("item/param/query/itemcatid/{itemCatId}")
	@ResponseBody
	public EgoResult itemCatId(@PathVariable long itemCatId){  //如果服务层写的不多，就控制器写的多
		 return tBItemParamServiceImpl.selById(itemCatId);
	}
	@RequestMapping("item/param/save/{itemCatId}")
	@ResponseBody
	public EgoResult save(TbItemParam tbItemParam,@PathVariable long itemCatId){
		tbItemParam.setItemCatId(itemCatId);
		EgoResult er = new EgoResult();
		try{
			tBItemParamServiceImpl.insItemParam(tbItemParam);
			er.setStatus(200);
		} catch (Exception e){
			er.setMessage(e.getMessage());
		}
		return er;
	}
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult  er = new EgoResult();
		try{
			if (1==tBItemParamServiceImpl.delItemParam(ids)){
				er.setStatus(200);
			}
		} catch (Exception e){
			er.setMessage(e.getMessage());
		}
		return er;
	}
}
