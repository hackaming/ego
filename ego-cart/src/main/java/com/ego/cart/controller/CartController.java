package com.ego.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
	@RequestMapping("test")
	public String test(){
		return "cart";
	}
	@RequestMapping("cart/add/{id}.html")
	public String showCart(@PathVariable long id,int num){
		System.out.println("Controller called");
		return "cart";
	}
}
