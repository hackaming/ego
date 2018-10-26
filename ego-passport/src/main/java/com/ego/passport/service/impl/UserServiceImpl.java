package com.ego.passport.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TBUserDubboService;
import com.ego.jedis.service.JedisDao;
import com.ego.passport.service.UserService;
import com.ego.pojo.TbUser;


@Service
public class UserServiceImpl implements UserService{
	@Reference
	private TBUserDubboService tBUserDubboServiceImpl;
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public TbUser selUserByLogin(TbUser tbUser) {
		return tBUserDubboServiceImpl.selUserByLogin(tbUser);
	}

	@Override
	public EgoResult getUserInfoByToken(String token) {
		EgoResult er = new EgoResult();
		if (!token.equals("") && null!=token){
			System.out.println("debug in userserviceImpl, token get:"+token);
			String json  =  (String) jedisDaoImpl.get(token);
			System.out.println("String get from redis is:" + json);
			if (json==null || json.trim().equals("")){
				return er; //data does not exist in redis
			}
			//String json = jedisDaoImpl.get(token).toString();
			TbUser user =JsonUtils.jsonToPojo( json, TbUser.class) ;
			if (null != user){
				er.setStatus(200);
				er.setMessage("Successfully get the user from Redis!");
				er.setData(user);
			}
		}
		return er;
	}

	@Override
	public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse reponse) {
		EgoResult er = new EgoResult();
		if (!token.equals("") && token!=null){
			jedisDaoImpl.delete(token);
			CookieUtils.deleteCookie(request, reponse, "TT_TOKEN");
			er.setStatus(200);
			er.setMessage("logout successfully");
		}
		return er;
	}

}
