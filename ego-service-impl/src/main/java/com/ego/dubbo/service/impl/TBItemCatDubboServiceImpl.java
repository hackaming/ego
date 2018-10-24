package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TBItemDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.pojo.TbItemExample;

public class TBItemCatDubboServiceImpl implements TBItemCatDubboService{
	@Resource
	TbItemCatMapper tbIemCatMapper;
	@Override
	public List<TbItemCat> list(Long id) {
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		tbItemCatExample.createCriteria().andParentIdEqualTo(id);
		return tbIemCatMapper.selectByExample(tbItemCatExample);
	}
	@Override
	public TbItemCat selById(Long id) {
		// TODO Auto-generated method stub
		return tbIemCatMapper.selectByPrimaryKey(id);
	}

}
