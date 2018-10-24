package com.ego.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping("user/showLogin")
	public String showLogin(){
		return "login";
	}

	@RequestMapping("user/showRegister")
	public String showRegister(){
		return "register";
	}

}
