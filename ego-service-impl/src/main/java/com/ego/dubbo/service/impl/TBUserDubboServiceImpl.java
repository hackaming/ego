package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

public class TBUserDubboServiceImpl implements TBUserDubboService{
	
	@Resource
	private TbUserMapper tbUserMapper;
	
	@Override
	public TbUser selUserByLogin(TbUser tbUser) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
