package com.ego.manage.service.impl;

import com.ego.manage.service.TBItemDescService;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TBItemDescDubboService;

import com.ego.pojo.TbItemDesc;

@Service
public class TBItemDescServiceImpl implements TBItemDescService {
	@Reference
	private TBItemDescDubboService tBItemDescDubboService;
	
	public int insertDesc(TbItemDesc itemdDesc) {
		return tBItemDescDubboService.insertItemDesc(itemdDesc);
	}

}
