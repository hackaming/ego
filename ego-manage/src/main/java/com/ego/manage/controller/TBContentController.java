package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TBContentService;
import com.ego.pojo.TbContent;

@Controller
public class TBContentController {
	@Resource
	private TBContentService tBContentServiceImpl;
	
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid list(@RequestParam(defaultValue = "0") long categoryId,@RequestParam int page,@RequestParam int rows){
		return tBContentServiceImpl.list(categoryId, page, rows);
	}
	
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult save(TbContent tbContent){
		EgoResult er = null;
		try {
			er = tBContentServiceImpl.save(tbContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return er;
	}
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		try {
			return tBContentServiceImpl.delContent(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult edit(TbContent tBContent){
		String url = tBContent.getUrl();
		if (url.length()>0&& !url.startsWith("http")){
			url = "http://"+url;
			tBContent.setUrl(url);
		}
		try {
			return tBContentServiceImpl.updateContent(tBContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
