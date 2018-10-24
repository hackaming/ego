package com.ego.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.pojo.TbUser;

@Controller
public class UserController {
	
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader(value="Referer") String url,Model model){
		model.addAttribute("redirect", url);
		return "login";
	}

	@RequestMapping("user/showRegister")
	public String showRegister(){
		return "register";
	}
	@RequestMapping("/user/login")
	public String login(TbUser tbUser){
		return null;
	}

}
