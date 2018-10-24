package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TBItemDescDubboService {
	int insertItemDesc(TbItemDesc tbItemDesc);
	TbItemDesc selByItemId(long itemId);
}
