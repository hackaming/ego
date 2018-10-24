package com.ego.dubbo.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TBUserDubboService {
	public EgoResult selUserByLogin(TbUser tbUser);
}
