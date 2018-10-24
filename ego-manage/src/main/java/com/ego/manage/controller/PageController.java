package com.ego.manage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TBItemDubboService;

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String welcome(){
		return "index";
	}
	
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page){
		return page;
	}	
}
