package com.ego.item.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TBItemChild;
import com.ego.dubbo.service.TBItemDubboService;
import com.ego.item.service.TBItemService;
import com.ego.pojo.TbItem;

@Service
public class TBItemServiceImpl implements TBItemService{
	@Reference
	private TBItemDubboService tbItemDubbleServiceImpl;
	
	@Override
	public TbItem selItemById(String id) {
		return tbItemDubbleServiceImpl.selByItemId(Long.parseLong(id));
	}

}
