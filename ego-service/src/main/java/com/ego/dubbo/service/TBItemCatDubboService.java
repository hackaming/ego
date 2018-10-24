package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;

public interface TBItemCatDubboService {
	List<TbItemCat> list(Long id);
	TbItemCat selById(Long id);
}
