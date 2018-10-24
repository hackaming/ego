package com.ego.passport.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TBUserDubboService;
import com.ego.passport.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Reference
	private TBUserDubboService tBUserDubboServiceImpl;

}
