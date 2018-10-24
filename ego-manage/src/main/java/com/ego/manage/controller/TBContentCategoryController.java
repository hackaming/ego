package com.ego.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TBContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TBContentCategoryController {
	@Resource
	TBContentCategoryService tBContentCategoryServiceImpl;
	
	@RequestMapping("content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory cat){
		System.out.println("id get from controller:"+cat.getId());
		int index = 0;
		try {
			index = tBContentCategoryServiceImpl.delContentCategoryWithRollback(cat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EgoResult er = new EgoResult();
		if (1 == index){
			er.setStatus(200);
		}
		return er;
	}
	
	@RequestMapping("content/category/delete1/") // 这个是老的，加上了一个1，不用了，用上面这个，支持事务处理，把孩子也删了的。
	@ResponseBody
	public EgoResult delete1(@RequestParam long id){
		TbContentCategory cat = new TbContentCategory();
		EgoResult er = new EgoResult();
		cat.setId(id);
		cat.setStatus(3);
		int index =0;
		try {
			index =tBContentCategoryServiceImpl.updateContentCategory(cat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (1 == index){
			er.setStatus(200);
		}
		return er;
	}
	
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUITree> list(@RequestParam(defaultValue = "0") long id){
		return tBContentCategoryServiceImpl.list(id);
	}

	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory tbContentCategory){
		int index = 0;
		try {
			index = tBContentCategoryServiceImpl.insContentCategoryWithRollback(tbContentCategory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EgoResult er = new EgoResult();
		if (1 == index){
			er.setStatus(200);
			Map<String,Long> map = new HashMap<>();
			map.put("id", tbContentCategory.getId());
			er.setData(map);
		}
		return er; 
	}
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory tbContentCategory){
		int index = 0;
		EgoResult er = new EgoResult();
		try {
			index = tBContentCategoryServiceImpl.updateContentCategory(tbContentCategory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (1 == index){
			er.setStatus(200);
		}
		return er;
	}

}
