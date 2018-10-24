package com.ego.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.portal.service.TbContentService;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	@RequestMapping("showBigAdvertisement")
	public String showBigAdvertisement(Model model){
		if (tbContentServiceImpl == null){
			System.out.println("Controller tbContentServiceImpl is null!");
		}
		model.addAttribute("ad1", tbContentServiceImpl.showBigAdvertisement());
		return "index";
	}
}
