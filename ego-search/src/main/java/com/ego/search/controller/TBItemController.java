package com.ego.search.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.pojo.TbItem;
import com.ego.search.service.TbItemService;



@Controller
public class TBItemController {
	
	@Resource
	private TbItemService tbItemServiceImpl;
	
	@RequestMapping(value="solr/init",produces="text/html;charset=utf-8")
	@ResponseBody
	public String init(){
		tbItemServiceImpl.init();
		return "finished!";
	}
	@RequestMapping("search.html")
	public String search(Model model,String q,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="12") int rows){
		try {
			q = new String(q.getBytes("iso-8859-1"),"utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("query",q);
			model.addAttribute("itemList",map.get("itemList"));
			model.addAttribute("totalPages",map.get("totalPages"));
			model.addAttribute("page",page);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
	}
	
	@RequestMapping("solr/add")
	@ResponseBody
	public int add(TbItem item){
		try {
			return tbItemServiceImpl.add(item);
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
