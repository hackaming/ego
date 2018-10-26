package com.ego.passport.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.jedis.service.JedisDao;
import com.ego.passport.service.UserService;

import com.ego.pojo.TbUser;

@Controller
public class UserController {
	
	@Resource
	private UserService userServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader(value="Referer") String url,Model model){
		model.addAttribute("redirectUrl", url);
		return "login";
	}

	@RequestMapping("user/showRegister")
	public String showRegister(){
		return "register";
	}
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser tbUser,HttpServletRequest request,HttpServletResponse reponse){
		EgoResult er = new EgoResult();
		TbUser user = userServiceImpl.selUserByLogin(tbUser);
		if (user != null){
			er.setStatus(200);
			er.setMessage("Successfully logon to EGO!");
			// add the user to redis
			
			// key of the token
			UUID token = UUID.randomUUID();
			// add to the client, set the expire date to 24 hours
			CookieUtils.setCookie(request, reponse, "TT_TOKEN", token.toString(), 60*60*24);
			// put it into redis, delete the password before put it into redis
			user.setPassword("");
			jedisDaoImpl.set(token.toString(), JsonUtils.objectTOString(user));
			return er;
		}
		er.setStatus(0);
		er.setMessage("Can't logon to EGO!");
		return er;
	}
	
	
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object tokenCheck(@PathVariable String token,String callback){
		 EgoResult er = userServiceImpl.getUserInfoByToken(token);
		 if (!callback.equalsIgnoreCase("") && callback!=null){
			 MappingJacksonValue mjv = new MappingJacksonValue(er);
			 mjv.setJsonpFunction(callback);
			 return mjv;
		 }
		 return er;
	}
	@RequestMapping("user/logout/{token}")
	public Object logout(@PathVariable String token,String callback,HttpServletRequest request,HttpServletResponse reponse){
		EgoResult er = userServiceImpl.logout(token, request, reponse);
		if (!callback.equals("") && callback!= null){
			MappingJacksonValue mjv = new MappingJacksonValue (er);
			mjv.setJsonpFunction(callback);
		}
		return er;
	}

}
