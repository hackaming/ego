package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemDescExample;

public class TBItemDescDubboServiceImpl implements TBItemDescDubboService{
	@Resource
	TbItemDescMapper tBItemDescMapper;
	@Override
	public int insertItemDesc(TbItemDesc tbItemDesc) {
		return tBItemDescMapper.insert(tbItemDesc);
	}
	@Override
	public TbItemDesc selByItemId(long itemId) {
		return tBItemDescMapper.selectByPrimaryKey(itemId);
	}

}
