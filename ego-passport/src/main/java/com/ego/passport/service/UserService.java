package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface UserService {
	public TbUser selUserByLogin(TbUser tbUser);
	public EgoResult getUserInfoByToken(String token);
	public EgoResult  logout(String token,HttpServletRequest request,HttpServletResponse reponse);
}
